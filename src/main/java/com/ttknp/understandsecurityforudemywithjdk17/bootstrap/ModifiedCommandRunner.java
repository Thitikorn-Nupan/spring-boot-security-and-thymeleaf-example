package com.ttknp.understandsecurityforudemywithjdk17.bootstrap;

import com.ttknp.understandsecurityforudemywithjdk17.models.security.Authority;
import com.ttknp.understandsecurityforudemywithjdk17.models.security.Role;
import com.ttknp.understandsecurityforudemywithjdk17.models.security.User;
import com.ttknp.understandsecurityforudemywithjdk17.repositories.security.AuthorityRepository;
import com.ttknp.understandsecurityforudemywithjdk17.repositories.security.RoleRepository;
import com.ttknp.understandsecurityforudemywithjdk17.repositories.security.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
// @Component
public class ModifiedCommandRunner implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // @Autowired
    public ModifiedCommandRunner(AuthorityRepository authorityRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // loadSecurityData();
    }

    public void loadSecurityData() {
        // *** save to table authorities (id,name)
        // *** 1 ,beer.create
        // *** 2 ,beer.read
        // *** 3 ,beer.update
        // *** ...
        // beer auths

        Authority create = Authority.builder().permission("permission.create").build();
        Authority read = Authority.builder().permission("permission.read").build();
        Authority update = Authority.builder().permission("permission.update").build();
        Authority delete = Authority.builder().permission("permission.delete").build();

        authorityRepository.saveAll(Arrays.asList(create, read, update, delete));

        // *** save to table roles (id,name)
        // *** 1 , ADMIN
        // *** 2 , CUSTOMER
        // *** 3 , NOBODY
        // done add roles table
        /**
        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());
        Role customerRole = roleRepository.save(Role.builder().name("NOBODY").build());
        */
        Role adminRole = Role.builder().name("ADMIN").build();
        Role userRole = Role.builder().name("USER").build();
        Role customerRole = Role.builder().name("NOBODY").build();

        // ** prepare roles_authorities attributes
        HashSet<Authority> adminAuthorities = new HashSet<Authority>(
                Set.of(
                        create, update, read, delete
                )
        );


        HashSet<Authority> userAuthorities = new HashSet<Authority>(
                Set.of(
                        create ,read
                )
        );

        HashSet<Authority> nobodyAuthorities = new HashSet<Authority>(
                Set.of(
                        read
                )
        );

        adminRole.setAuthorities(adminAuthorities);
        userRole.setAuthorities(userAuthorities);
        customerRole.setAuthorities(nobodyAuthorities);

        // *** save to table roles,roles_authorities in *** once method
        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));


        // *** save to tables user and users_roles ***
        /*userRepository.save(User.builder()
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
                .username("nobody")
                .password(passwordEncoder.encode("12345"))
                .role(customerRole)
                .build());*/
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("12345"))
                .role(adminRole)
                .build();

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("12345"))
                .role(userRole)
                .build();

        User nobody = User.builder()
                .username("nobody")
                .password(passwordEncoder.encode("12345"))
                .role(customerRole)
                .build();

        // *** save to table users,users_roles in *** once method
        userRepository.saveAll(Arrays.asList(admin, user, nobody));

        log.debug("Users Loaded : {}", userRepository.count());
    }
}
