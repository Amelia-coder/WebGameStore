package com.example.webgamestore.config;

import com.example.webgamestore.model.*;
import com.example.webgamestore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Skip seeding if any data exists
        if (userRepository.count() > 0) {
            return;
        }

        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gamestore.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.addRole("ADMIN");
        admin.addRole("USER");
        admin.setFirstName("Admin");
        admin.setLastName("User");
        userRepository.save(admin);
    }
} 