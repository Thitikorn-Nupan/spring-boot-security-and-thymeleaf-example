package com.ttknp.understandsecurityforudemywithjdk17.configuration;

import com.ttknp.understandsecurityforudemywithjdk17.configuration.security_option.RestApiHeaderAfterAuthFilter;
import com.ttknp.understandsecurityforudemywithjdk17.configuration.security_option.RestApiHeaderAuthFilter;
import com.ttknp.understandsecurityforudemywithjdk17.configuration.security_option.RestApiHeaderBeforeAuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
// ** for open using method security
// securedEnabled=true allow us @Secured({"**"})
// prePostEnabled=true allow use @PreAuthorize("hasAuthority('***')")
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    // again it's for testing (** this way for jdk 17 to inject AuthenticationManager)
    private AuthenticationConfiguration authConfiguration;
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(AuthenticationConfiguration authConfiguration ,UserDetailsService userDetailsService) { // , UserDetailsService userDetailsService
        this.authConfiguration = authConfiguration;
        this.userDetailsService = userDetailsService;
    }

    public RestApiHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestApiHeaderAuthFilter filter = new RestApiHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity) throws Exception { //  , MvcRequestMatcher.Builder mvc

        /**
        // For testing
        // this testing just validate string on header
        httpSecurity
                .addFilterBefore(new RestApiHeaderBeforeAuthFilter(), BasicAuthenticationFilter.class)
                .csrf().disable();
        */

        // For testing
        // this testing validate string on header and authenticate user,password
        // this is good way to filter !
        httpSecurity.addFilterBefore(
                        restHeaderAuthFilter(authConfiguration.getAuthenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

        httpSecurity
                .authorizeHttpRequests((authz) -> {
                    // Mvc matchers block
                    authz.requestMatchers(HttpMethod.GET, "/bootstrap/**","/css/**","/images/**").permitAll(); // for bootstrap css , my css
                    authz.requestMatchers(HttpMethod.GET, "/h2-ui","/h2-ui/**").permitAll();
                    authz.requestMatchers(HttpMethod.POST, "/h2-ui/**").permitAll();
                    // *** authenticate and no validate rules
                    authz.requestMatchers(HttpMethod.GET, "/template/car-list").hasAuthority("permission.read");
                    // *** who has read only can access
                    authz.requestMatchers(HttpMethod.GET, "/api/car/all","/api/car/").hasAuthority("permission.read");
                })
                // .formLogin().disable() // disable form login *** optional
                .httpBasic(withDefaults()) // enable http basic *** dialog login
                // *** for enable loading h2 ui templates
                // *** same .headers().frameOptions().sameOrigin();
                .headers((headers) -> {
                    headers.frameOptions((frameOptions) -> {
                        frameOptions.sameOrigin();
                    });
                })
                // *** you won't access any req with param if you don't enable/disable csrf protection
                // *** common error Invalid CSRF token found for http://localhost:8082 ***
                // *** this case i enable
                .csrf((csrf) -> {
                    csrf.ignoringRequestMatchers("/h2-ui", "/h2-ui/**","/template/webjars/**",
                            "/api/car/all","/api/car/",
                            "/template/car-list"
                    );
                })
                // add your jpa user detail service
                .userDetailsService(userDetailsService);

        /**
         // For testing
         httpSecurity
                .addFilterAfter(new RestApiHeaderAfterAuthFilter(), BasicAuthenticationFilter.class)
                .csrf().disable();
         */

        return httpSecurity.build();
    }
    /*
    @Bean
    public UserDetailsService userDetailsService() {
        // ** authorities ** no need prefix
        // ** rules have to prefix like RULE_
        User.UserBuilder userBuilder = User.builder(); // withDefaultPasswordEncoder() Deprecated. use instead builder() it's worked
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(userBuilder
                .username("user")
                .password(passwordEncoder().encode("12345"))
                .authorities("permission.read")
                .build());
        manager.createUser(userBuilder
                .username("admin@hotmail.com")
                .password(passwordEncoder().encode("12345"))
                .authorities("permission.read", "permission.write", "permission.delete", "permission.update")
                .build());
        return manager;
    }
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // many type of password encoder
    }
    */

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // many type of password encoder
    }

    // for setup config event listening
    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    // ** @EventListener Annotation that marks a method as a listener for application events. ** it works after logged in success
    // ** it inject AuthenticationSuccessEvent class auto
    @EventListener
    public void listenAuthenticationSuccessEvent(AuthenticationSuccessEvent event){
        log.debug("User Logged In");
        if (event.getSource() instanceof UsernamePasswordAuthenticationToken token) {

            log.debug("token.getPrincipal() : {}" , token.getPrincipal() ); // [Username=admin, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[delete, read, update, write]]

            if(token.getPrincipal() instanceof org.springframework.security.core.userdetails.User){
                // this case only work! can't convert to models.security.User
                String username = ((User) token.getPrincipal()).getUsername(); // Username: admin
                log.debug("Username logged in: {}" , username ); // amin
            }

            if(token.getDetails() instanceof WebAuthenticationDetails details){

                log.debug("Source IP : {} ,details : {}" , details.getRemoteAddress(),details); // Source IP : 0:0:0:0:0:0:0:1 ,details : WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null

            }
        }
    }

    // it works after authenticate and get something wrong as username or password
    // ** it inject AuthenticationFailureBadCredentialsEvent class auto
    @EventListener
    public void listenAuthenticationFailureBadCredentialsEvent(AuthenticationFailureBadCredentialsEvent event){
        log.debug("User Login failure");

        if(event.getSource() instanceof UsernamePasswordAuthenticationToken token){

            if(token.getPrincipal() instanceof String){
                log.debug("token.getPrincipal() : " + token.getPrincipal());
            }

            if(token.getDetails() instanceof WebAuthenticationDetails details){
                log.debug("Source IP: {}" , details.getRemoteAddress());
            }
        }
    }

}
