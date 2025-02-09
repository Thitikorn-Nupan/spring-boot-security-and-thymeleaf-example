package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.models.security.Authority;
import guru.sfg.brewery.models.security.User;
import guru.sfg.brewery.repositories.sercurity.AuthorityRepository;
import guru.sfg.brewery.repositories.sercurity.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/20.
 */
@Slf4j
// @Component
public class DefaultSecurityDataOnCommandRunner implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // @Autowired
    public DefaultSecurityDataOnCommandRunner(AuthorityRepository authorityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void loadSecurityData() {

        // if you authen by role prefix should be ROLE_*
        // if you authen by authority can be any string
        Authority admin = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority user = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        Authority customer = authorityRepository.save(Authority.builder().role("CUSTOMER").build());

        userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("12345"))
                .authority(admin)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("12345"))
                .authority(user)
                .build());

        userRepository.save(User.builder()
                .username("customer")
                .password(passwordEncoder.encode("12345")) // result password like => {bcrypt15}$2a$15$cFAlllxt20GdpoYkjV0TjeghcajavRg6.vurnm1fY1zZrhEl.flkC
                .authority(customer)
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