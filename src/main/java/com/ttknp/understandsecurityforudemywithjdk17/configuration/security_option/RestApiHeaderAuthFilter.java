package com.ttknp.understandsecurityforudemywithjdk17.configuration.security_option;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;
@Slf4j
public class RestApiHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    public RestApiHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain
            chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }

        try {

            Authentication authResult = attemptAuthentication(request, response);
            // if authResult is null mean user try to login as simple way
            // so that why set else { chain.doFilter(request, response) }
            log.debug("Authentication result: {}", authResult);

            if (authResult != null) {
                // developer test
                successfulAuthentication(request, response, chain, authResult);
            } else {
                // user
                chain.doFilter(request, response);
            }

        } catch (AuthenticationException e) {
            log.error("Authentication Failed", e);
            unsuccessfulAuthentication(request, response, e);
        }

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String userName = getUsername(request);
        String password = getPassword(request);

        if (userName == null) {
            userName = "";
        }

        if (password == null) {
            password = "";
        }

        log.debug("Authenticating User:Password: {}:{}", userName, password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);

        if (!StringUtils.isEmpty(userName)) {
            return this.getAuthenticationManager().authenticate(token);
        } else {
            return null;
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.setHeader("Message-Filter", "Authentication success");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        SecurityContextHolder.clearContext(); //

        if (log.isDebugEnabled()) {
            log.debug("Authentication request failed: {}", failed.toString(), failed);
            log.debug("Updated SecurityContextHolder to contain null Authentication");
        }


        response.setContentType("application/json"); // default is text
        response.getWriter().write("{\"message\":\"invalid user or password\"}");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }

    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Password");
    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader("Api-Username");
    }
}
