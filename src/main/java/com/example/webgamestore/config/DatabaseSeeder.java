package com.example.webgamestore.config;

import com.example.webgamestore.model.*;
import com.example.webgamestore.repository.*;
import com.example.webgamestore.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Skip seeding if any data exists
        if (userRepository.count() > 0) {
            return;
        }

        // Create admin user
        userService.createAdminUser("admin", "admin123");
    }
} 