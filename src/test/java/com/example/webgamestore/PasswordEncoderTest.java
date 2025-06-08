package com.example.webgamestore;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {

    @Test
    public void testPasswordEncoding() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Test the stored hash from data.sql
        String storedHash = "$2a$10$dL4az.HhQJ0UGT.xN3.wR.oGr8Jg1oG3VCxDEKxYG6vSk.OEpxKIS";
        String password = "admin123";
        
        // Verify the password matches the stored hash
        boolean matches = encoder.matches(password, storedHash);
        assertTrue(matches, "Password should match the stored hash");
        
        // Generate a new hash and verify it works
        String newHash = encoder.encode("admin123");
        assertTrue(encoder.matches(password, newHash), "Password should match the new hash");
    }
} 