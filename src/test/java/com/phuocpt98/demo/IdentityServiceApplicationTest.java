package com.phuocpt98.demo;

import java.security.MessageDigest;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdentityServiceApplicationTest {

    @Test
    void hashPassword()  throws Exception{

        String password = "123456789";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte[] digest = md.digest();

        String hashedPassword = String.format("%064x", new java.math.BigInteger(1, digest));
        log.info("round1: " + hashedPassword);


        md.update(password.getBytes());

         digest = md.digest();

        hashedPassword = String.format("%064x", new java.math.BigInteger(1, digest));
        log.info("round2: " + hashedPassword);

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        log.info("round2: " + encoder.encode(password));
        log.info("round2: " + encoder.encode(password));

    }
}
