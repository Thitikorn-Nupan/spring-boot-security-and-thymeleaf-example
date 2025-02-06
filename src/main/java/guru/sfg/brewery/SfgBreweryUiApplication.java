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

