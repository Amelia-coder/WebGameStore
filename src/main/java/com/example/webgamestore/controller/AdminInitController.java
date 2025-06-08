package com.example.webgamestore.controller;

import com.example.webgamestore.model.User;
import com.example.webgamestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminInitController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/init")
    public ResponseEntity<String> initializeAdmin() {
        // Check if admin already exists
        if (userRepository.findByUsername("admin").isPresent()) {
            return ResponseEntity.ok("Admin user already exists");
        }

        // Create new admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gamestore.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRegistrationDate(LocalDateTime.now());
        admin.setRoles(new HashSet<>());
        admin.addRole("ADMIN");
        admin.addRole("USER");

        userRepository.save(admin);

        return ResponseEntity.ok("Admin user created successfully");
    }
} 