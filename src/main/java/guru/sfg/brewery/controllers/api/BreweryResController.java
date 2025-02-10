package guru.sfg.brewery.controllers.api;

import guru.sfg.brewery.models.Brewery;
import guru.sfg.brewery.services.BreweryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j // is lombok logging
// @RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BreweryResController {

    private final BreweryService breweryService;

    @Autowired
    public BreweryResController(BreweryService breweryService) {
        this.breweryService = breweryService;
    }

    // provide method security
    @Secured({"ROLE_ADMIN"}) // now we don't need config on configure(...) method // **** we call security method expression
    @GetMapping("/breweries")
    public @ResponseBody
    List<Brewery> getBreweriesJson(){
        return breweryService.getAllBreweries();
    }
}
