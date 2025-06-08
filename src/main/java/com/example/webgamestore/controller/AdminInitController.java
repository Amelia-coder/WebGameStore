package com.example.webgamestore.controller;

import com.example.webgamestore.model.User;
import com.example.webgamestore.repository.UserRepository;
import com.example.webgamestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminInitController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/init")
    public ResponseEntity<String> initializeAdmin() {
        // Check if admin already exists
        if (userRepository.findByUsername("admin").isPresent()) {
            return ResponseEntity.ok("Admin user already exists");
        }

        userService.createAdminUser("admin", "admin123");
        return ResponseEntity.ok("Admin user created successfully");
    }
} 