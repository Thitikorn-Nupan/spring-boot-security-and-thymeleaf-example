package com.ttknp.understandsecurityforudemywithjdk17.configuration.security_auth;


import com.ttknp.understandsecurityforudemywithjdk17.models.security.User;
import com.ttknp.understandsecurityforudemywithjdk17.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


 @Slf4j // this anno work as logback.xml
//@Service
@Component("userDetailsService")
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

        User user = userRepository.findByEmail(username).orElseThrow(() -> {
            return new UsernameNotFoundException("User name: " + username + " not found");
        });

        // this config below make you set rows of row like ROLE_ADMIN or ADMIN Both are worked *** run work but invalid
        // but // if you authenticate by role prefix should be ROLE_*
        Set<GrantedAuthority> authorities = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        log.debug("JpaUserDetailsServiceConfig.authorities.size() {}",authorities.size()); // 1
        log.debug("JpaUserDetailsServiceConfig.authorities {}",authorities); //


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

   /**
    Not worked
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Getting User info via JPA");

        return userRepository.findByUsername(username).orElseThrow(() -> {

            return new UsernameNotFoundException("User name: " + username + " not found");
        });
    }*/
}