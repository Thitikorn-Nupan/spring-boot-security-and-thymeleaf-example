package guru.sfg.brewery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:info/configs.properties") //
public class SfgBreweryUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfgBreweryUiApplication.class, args);
    }

}

/*
* SELECT * FROM USERS_ROLES ;

SELECT * FROM USERS ;

SELECT * FROM ROLES_AUTHORITIES ;

SELECT * FROM ROLES ;

SELECT * FROM AUTHORITIES ;
*
*
* SELECT * FROM BEER ;

SELECT * FROM BEER_INVENTORY ;

SELECT * FROM BEER_ORDER ;

SELECT * FROM BREWERY ;

SELECT * FROM CUSTOMER ;
*/