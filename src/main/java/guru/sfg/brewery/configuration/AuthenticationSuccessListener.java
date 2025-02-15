package guru.sfg.brewery.configuration;

import guru.sfg.brewery.models.security.LoginSuccess;
import guru.sfg.brewery.repositories.sercurity.LoginSuccessRepository;
import guru.sfg.brewery.repositories.sercurity.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 7/18/20.
 */
@Slf4j
@Component
public class AuthenticationSuccessListener {

    private LoginSuccessRepository loginSuccessRepository;
    private UserRepository userRepository;

    @Autowired
    public AuthenticationSuccessListener(LoginSuccessRepository loginSuccessRepository, UserRepository userRepository) {
        this.loginSuccessRepository = loginSuccessRepository;
        this.userRepository = userRepository;
    }

    // it works after logged in success
    // try to jdk 17 and add to ecommerce app
    @EventListener // **
    public void listen(AuthenticationSuccessEvent event){

        log.debug("User Logged In Okay");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {

            LoginSuccess.LoginSuccessBuilder builder = LoginSuccess.builder();
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();

            log.debug("User logged in: {}" , token.getPrincipal() ); // Username: user; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: beer.read

            if(token.getPrincipal() instanceof org.springframework.security.core.userdetails.User){
                // this case only work! can't convert to models.security.User
                String username = ((User) token.getPrincipal()).getUsername(); // Username: admin
                log.debug("Username logged in: {}" , username );
                guru.sfg.brewery.models.security.User user = userRepository.findByUsername(username).get();
                builder.user(user);
            }

            if(token.getDetails() instanceof WebAuthenticationDetails){

                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                log.debug("Source IP: {}" , details.getRemoteAddress()); // *** 0:0:0:0:0:0:0:1 (IPV6) == 127.0.0.1 (IPV4)
                log.debug("details: {}" ,details); // RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: C87C67C13E95604A0435EC58AA5020CA
                builder.sourceIp(details.getRemoteAddress());
            }

            LoginSuccess loginSuccess = loginSuccessRepository.save(builder.build());

            log.debug("Login Success saved. Id: {}" , loginSuccess.getId());
        }

    }
}