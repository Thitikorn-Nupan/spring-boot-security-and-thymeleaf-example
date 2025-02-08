package guru.sfg.brewery.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// *** this is old  ****
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .authorizeRequests((auth) -> {
                   // Mvc mathers block
                   // ** You see i permit all "/" as "/beers","/beers/find" so all of authorizeRequests() under this block won work
                   // where /webjars/** at ? it's dependency folder at external library
                   auth.antMatchers("/webjars/**","/login","/resources/images/**").permitAll();
                   auth.antMatchers("/").permitAll();
                   auth.antMatchers(HttpMethod.GET,"/api/v1/beer").hasAnyRole("ADMIN","USER"); // without .permitAll() / .authenticated() default is authenticated()
                   auth.antMatchers(HttpMethod.GET,"/api/v1/beer.upc/{ucp}").authenticated(); // just authenticate not validate role ** all roles can access
               })
               .authorizeRequests()
               .anyRequest()
               .authenticated()
               .and()
               .formLogin().disable() // close default form login
               // .and()
               .httpBasic(); // open dialog form
    }

    @Bean //** don't forget you have to make sure this one of beans on context
    // for config user deatil on mem
    @Override
    protected UserDetailsService userDetailsService() {
        // rules and authorities are getting you same result but difference way to use on Mvc mathers block ** configure(HttpSecurity http)
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .roles("USER")
                .build();
        // if we dont do with withDefaultPasswordEncoder() we can do with  .builder() but on password you have to put {noop}
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}12345")
                .roles("ADMIN","USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
