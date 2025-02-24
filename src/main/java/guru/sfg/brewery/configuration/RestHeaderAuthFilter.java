package guru.sfg.brewery.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// this filter for testing
@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }

        try {

            Authentication authResult = attemptAuthentication(request, response);

            if (authResult != null) {
                successfulAuthentication(request, response, chain, authResult);
                // chain.doFilter(request, response); // ** if you wanna return result as same as api you provide
            } else {
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

        log.debug("Authenticating User:Password: {}:{}" , userName , password);

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
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"message\":\"valid user and password\"}");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        SecurityContextHolder.clearContext(); //

        if (log.isDebugEnabled()) {
            log.debug("Authentication request failed: {}" , failed.toString(), failed);
            log.debug("Updated SecurityContextHolder to contain null Authentication");
        }


        response.setContentType("application/json"); // default is text
        response.getWriter().write("{\"message\":\"invalid user or password\"}");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }

    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Secret");
    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader("Api-Key");
    }
}