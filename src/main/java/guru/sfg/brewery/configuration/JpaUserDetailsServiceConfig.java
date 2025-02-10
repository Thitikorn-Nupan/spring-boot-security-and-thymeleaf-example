package guru.sfg.brewery.configuration;

import guru.sfg.brewery.models.security.Authority;
import guru.sfg.brewery.models.security.User;
import guru.sfg.brewery.repositories.sercurity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jt on 6/22/20.
 */
@Slf4j // this anno work as logback.xml
@Service
public class JpaUserDetailsServiceConfig implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JpaUserDetailsServiceConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("loadUserByUsername {}",username);

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("User name: " + username + " not found");
        });

        // this config below make you set rows of row like ROLE_ADMIN or ADMIN Both are worked *** run work but invalid
        // but // if you authenticate by role prefix should be ROLE_*
        Set<GrantedAuthority> authorities = user
                .getAuthorities()
                .stream()
                .map(Authority::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        log.debug("loadUserByUsername {}",authorities.size()); // 1
        log.debug("loadUserByUsername {}",authorities); // [ROLE_ADMIN] [beer.read]

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                authorities
        );
    }

}