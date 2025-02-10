package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.models.security.Authority;
import guru.sfg.brewery.models.security.Role;
import guru.sfg.brewery.models.security.User;
import guru.sfg.brewery.repositories.sercurity.AuthorityRepository;
import guru.sfg.brewery.repositories.sercurity.RoleRepository;
import guru.sfg.brewery.repositories.sercurity.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by jt on 6/21/20.
 */
@Slf4j
// @Component
public class DefaultSecurityDataOnCommandRunner implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // @Autowired
    public DefaultSecurityDataOnCommandRunner(AuthorityRepository authorityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    private void loadSecurityData() {

        // if you authen by role prefix should be ROLE_*
        //beer auths
        Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        adminRole.setAuthorities(Set.of(createBeer, updateBeer, readBeer, deleteBeer)); // admin has beer.create,update,read,delete but these are ADMIN

        customerRole.setAuthorities(Set.of(readBeer)); // customer has beer.read but these are CUSTOMER

        userRole.setAuthorities(Set.of(readBeer)); // user has beer.read but these are USER

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        // now you use authorities for authenticate users!
        userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("12345"))
                .role(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("12345"))
                .role(userRole)
                .build());

        userRepository.save(User.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .role(customerRole)
                .build());

        log.debug("Users Loaded: " + userRepository.count());
    }

    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}