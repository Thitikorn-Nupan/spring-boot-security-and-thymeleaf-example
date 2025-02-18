//package com.ttknp.understandsecurityforudemywithjdk17.configuration.event_listening;
//
//
//import jakarta.servlet.http.HttpServlet;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//
//@Slf4j
//// @Component
//public class AuthenticationSuccessListener {
//
//    // ** @EventListener Annotation that marks a method as a listener for application events. ** it works after logged in success
//    // @EventListener
//    public void listen(AuthenticationSuccessEvent event){
//
//        log.debug("User Logged In");
//
//        if (event.getSource() instanceof UsernamePasswordAuthenticationToken token) {
//
//
//            log.debug("token.getPrincipal() : {}" , token.getPrincipal() ); // [Username=admin, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[delete, read, update, write]]
//
//            if(token.getPrincipal() instanceof org.springframework.security.core.userdetails.User){
//                // this case only work! can't convert to models.security.User
//                String username = ((User) token.getPrincipal()).getUsername(); // Username: admin
//                log.debug("Username logged in: {}" , username ); // amin
//
//            }
//
//            if(token.getDetails() instanceof WebAuthenticationDetails details){
//
//                log.debug("Source IP: {}" , details.getRemoteAddress()); // *** 0:0:0:0:0:0:0:1 (IPV6) == 127.0.0.1 (IPV4)
//                log.debug("details: {}" ,details); // [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=8FEDF6373D5A79A8736CD7E5D3FEBDAE]
//            }
//
//
//        }
//
//    }
//}