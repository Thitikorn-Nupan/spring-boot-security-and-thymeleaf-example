package com.ttknp.understandsecurityforudemywithjdk17;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@Slf4j
/**
 *   ** example
 *   @Slf4j
 *   public class LogExample {
 *   }
 *   ** will generate:
 *   public class LogExample {
 *       private static final org. slf4j. Logger log = org. slf4j. LoggerFactory. getLogger(LogExample. class);
 *   }
 */
@SpringBootApplication

public class UnderstandSecurityForUdemyApplication {
    // private static Logger log = LoggerFactory.getLogger(UnderstandSecurityForUdemyApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(UnderstandSecurityForUdemyApplication.class, args);
        // log.debug("UnderstandSecurityForUdemyApplication class initialized"); // 15:47:09.874 [main] DEBUG c.t.u.UnderstandSecurityForUdemyApplication : UnderstandSecurityForUdemyApplication class initialized
    }

}
