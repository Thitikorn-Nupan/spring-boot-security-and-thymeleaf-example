package guru.sfg.brewery.configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class SfgPasswordEncoderFactories {

    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt15";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder(15));
        encoders.put("bcrypt", new BCryptPasswordEncoder()); // .password("{bcrypt}$2a$16$OwTrMH3Sl2Mz80lLf
        encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()); // .password("{noop}12345
        encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder()); // work for .password("{sha256}1296cefceb47413d3fb91ac758
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    // don't instantiate class
    /*
    private SfgPasswordEncoderFactories() {
    }
    */
}