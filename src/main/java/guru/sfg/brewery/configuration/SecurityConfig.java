package guru.sfg.brewery.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// *** this is old  ****
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // add my custom restHeaderAuthFilter // for testing
        http.addFilterBefore(
                        restHeaderAuthFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
        // Note. you still can work with Mvc mathers block below

        http
                .authorizeRequests((auth) -> {
                    // Mvc mathers block
                    // ***** .hasIpAddress(<ip>) accept ip a / ip netmask ** now localhost can't access // if you not set permitall it will authen
                    auth.antMatchers("/","/beers","/beers/**","/brewery","/brewery/**","/customers","/customers/**").hasIpAddress("127.0.0.1");
                    auth.antMatchers("/brewery/","/brewery/**").hasRole("ADMIN");
                    // where /webjars/** at ? it's dependency folder at external library
                    auth.antMatchers("/webjars/**", "/login", "/resources/images/**","/h2-ui","/h2-ui/**").permitAll();
                    auth.antMatchers(HttpMethod.GET, "/api/v1/beer").hasAnyRole("ADMIN", "USER"); // without .permitAll() / .authenticated() default is authenticated()
                    //
                    auth.antMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN");
                    auth.antMatchers(HttpMethod.PUT, "/api/v1/beer/**").hasRole("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/api/v1/beer.upc/{ucp}").authenticated(); // just authenticate not validate role ** all roles can access
                })
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().disable() // close default form login
                // .and()
                .httpBasic(); // open dialog form

        http.headers().frameOptions().sameOrigin(); // for loading h2 ui templates
    }

    // @Bean // don't forget you have to make sure this one of beans on context // bean for config user detail on mem
    //@Override
    /*protected UserDetailsService userDetailsService() {
        // rules and authorities are getting you same result but difference way to use on Mvc mathers block ** configure(HttpSecurity http)
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .roles("USER")
                .build();
        // if we dont do with withDefaultPasswordEncoder() we can do with  .builder() but on password you have to put {noop}
        /*UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}12345")
                .roles("ADMIN","USER")
                .build();*//*
        // ** another way
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$16$OwTrMH3Sl2Mz80lLfhx5z.RestirTb5Xzo4Vmkfjd3miBmppIpfSC") // 12345
                .roles("ADMIN", "USER")
                .build();
        UserDetails admin2 = User.builder()
                .username("admin2")
                .password("{sha256}1296cefceb47413d3fb91ac7586a4625c33937b4d3109f5a4dd96c79c46193a029db713b96006ded") // password
                .roles("ADMIN", "USER")
                .build();
        UserDetails admin3 = User.builder()
                .username("admin3")
                .password("{noop}12345")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin, admin2, admin3);
    }*/

   /**
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // can specify
        //  strength – the log rounds to use, between 4 and 31
        // default is 10
    }
    */

    @Bean // many password encode types
    protected PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
