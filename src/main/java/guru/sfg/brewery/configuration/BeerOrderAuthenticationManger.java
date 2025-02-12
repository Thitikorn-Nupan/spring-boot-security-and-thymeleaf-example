package guru.sfg.brewery.configuration;

import guru.sfg.brewery.models.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by jt on 7/6/20.
 */
@Slf4j
// @Component
public class BeerOrderAuthenticationManger {

    public boolean customerIdMatches(Authentication authentication, UUID customerId){
        User authenticatedUser = (User) authentication.getPrincipal();
        log.debug("Auth User Customer Id: " + authenticatedUser.getCustomer().getId() + " Customer Id:" + customerId);
        return authenticatedUser.getCustomer().getId().equals(customerId);
    }

}