package com.openclassrooms.poseidon;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncodeTest {

    @Test
    public void testPasswordEncodingAndMatching() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "123456";
        String encodedPassword = encoder.encode(rawPassword);

        assertNotNull(encodedPassword, "Encoded password should not be null");
        assertFalse(encodedPassword.isEmpty(), "Encoded password should not be empty");

        assertTrue(encoder.matches(rawPassword, encodedPassword), "Password should match the encoded hash");

        System.out.println("Encoded password: " + encodedPassword);
    }
}
