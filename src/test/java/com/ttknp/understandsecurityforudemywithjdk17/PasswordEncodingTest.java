package com.ttknp.understandsecurityforudemywithjdk17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by jt on 6/16/20.
 */
@Disabled
@Slf4j
public class PasswordEncodingTest {

    static final String PASSWORD = "12345";

    @Test
    void testBcrypt15() {
        PasswordEncoder bcrypt = new BCryptPasswordEncoder(15);
        log.debug(bcrypt.encode(PASSWORD));
        log.debug(bcrypt.encode(PASSWORD));
        log.debug(bcrypt.encode("12345"));
        // ** each encode no duplicate
    }

    @Test
    void testBcrypt() {
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();
        log.debug(bcrypt.encode(PASSWORD));
        log.debug(bcrypt.encode(PASSWORD));
        log.debug(bcrypt.encode("12345"));
        // ** each encode no duplicate

    }

    @Test
    void testSha256() {
        PasswordEncoder sha256 = new StandardPasswordEncoder();
        log.debug(sha256.encode(PASSWORD));
        log.debug(sha256.encode(PASSWORD));
        // ** each encode no duplicate

    }

    @Test
    void testLdap() {
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        log.debug(ldap.encode(PASSWORD));
        log.debug(ldap.encode(PASSWORD));
        log.debug(ldap.encode("12345"));
        // ** each encode no duplicate
        String encodedPwd = ldap.encode(PASSWORD);
        // ** decode
        assertTrue(ldap.matches(PASSWORD, encodedPwd ));

    }

    @Test
    void testNoOp() {
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();
        log.debug(noOp.encode(PASSWORD));
    }

    @Test
    void hashingExample() {
        log.debug(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        log.debug(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        // ** each encode are duplicate
    }
}