package com.example.webgamestore.config;

import com.example.webgamestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitialDataLoader {

    private final UserService userService;

    @Bean
    public CommandLineRunner initialData() {
        return args -> {
            // Create admin user if it doesn't exist
            if (!userService.existsByUsername("admin")) {
                userService.createAdminUser("admin", "admin123");
                System.out.println("Admin user created successfully!");
                System.out.println("Username: admin");
                System.out.println("Password: admin123");
            }
        };
    }
} 