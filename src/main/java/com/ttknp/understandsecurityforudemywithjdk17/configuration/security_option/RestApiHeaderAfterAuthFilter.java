package com.ttknp.understandsecurityforudemywithjdk17.configuration.security_option;
// jdk 11
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
// jdk 17

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

// ** this filter for testing
//@Slf4j
//public class RestApiHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {
//
//    public RestApiHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
//        super(requiresAuthenticationRequestMatcher);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        String userName = getUsername(request);
//        String password = getPassword(request);
//
//        if (userName == null) {
//            userName = "";
//        }
//
//        if (password == null) {
//            password = "";
//        }
//
//        log.debug("Authenticating User:Password {}:{}", userName, password);
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
//
//        if (!isEmpty(userName)) {
//            log.debug("Authenticating User:{}", userName);
//            return this.getAuthenticationManager().authenticate(token);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//
//
//        if (logger.isDebugEnabled()) {
//            logger.debug("Request is to process authentication");
//        }
//
//        try {
//
//            Authentication authResult = attemptAuthentication(httpServletRequest, httpServletResponse);
//
//            if (authResult != null) {
//                // chain.doFilter(request, response); // ** if you wanna return result as same as api you provide
//                successfulAuthentication(httpServletRequest, httpServletResponse, chain, authResult);
//            } else {
//                chain.doFilter(request, response);
//            }
//        } catch (AuthenticationException e) {
//            log.debug("Authentication Failed {}", e.getMessage());
//            unsuccessfulAuthentication(httpServletRequest, httpServletResponse, e);
//        }
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//
//        SecurityContextHolder.clearContext(); // Explicitly clears the context value from the current thread.
//
//        if (log.isDebugEnabled()) {
//            log.debug("Authentication request failed: {}" , failed.toString(), failed);
//        }
//
//        response.setContentType("application/json"); // default is text
//        response.getWriter().write("{\"message\":\"invalid user or password\"}");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//
//        if (logger.isDebugEnabled()) {
//            logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
//        }
//
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        response.setContentType("application/json");
//        response.getWriter().write("{\"message\":\"valid user and password\"}");
//        response.setStatus(HttpServletResponse.SC_ACCEPTED);
//    }
//
//
//    private String getPassword(HttpServletRequest request) {
//        return request.getHeader("Api-Username");
//    }
//
//    private String getUsername(HttpServletRequest request) {
//        return request.getHeader("Api-Password");
//    }
//
//
////
////    @Override
////    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
////        String userName = getUsername(request);
////        String password = getPassword(request);
////
////        if (userName == null) {
////            userName = "";
////        }
////
////        if (password == null) {
////            password = "";
////        }
////
////        log.debug("Authenticating User:Password: {}:{}" , userName , password);
////
////        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
////
////        if (!StringUtils.isEmpty(userName)) {
////            return this.getAuthenticationManager().authenticate(token);
////        } else {
////            return null;
////        }
////
////    }
////
//
//

////
////    private String getPassword(HttpServletRequest request) {
////        return request.getHeader("Api-Secret");
////    }
////
////    private String getUsername(HttpServletRequest request) {
////        return request.getHeader("Api-Key");
////    }
//}

@Slf4j
public class RestApiHeaderAfterAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {

           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           if (authentication != null) {
               log.debug("Authentication found");
               successfulAuthentication(httpServletResponse);
           }else{
               log.debug("Authentication not found");
               unsuccessfulAuthentication( httpServletResponse);
           }

        } catch (AuthenticationException e) {
            log.debug("Authentication Failed {}", e.getMessage());
            unsuccessfulAuthentication( httpServletResponse);
        }
    }

    protected void unsuccessfulAuthentication(HttpServletResponse response) throws IOException, ServletException {
        SecurityContextHolder.clearContext(); // Explicitly clears the context value from the current thread.
        response.setContentType("application/json"); // default is text
        response.getWriter().write("{\"message\":\"Username or password are incorrect\"}");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    protected void successfulAuthentication( HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"Username or password are correct\"}");
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Password");
    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader("Api-Username");
    }


}