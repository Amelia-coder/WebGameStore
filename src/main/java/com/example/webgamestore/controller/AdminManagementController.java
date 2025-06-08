package com.example.webgamestore.controller;

import com.example.webgamestore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin-management")
@RequiredArgsConstructor
@Slf4j
public class AdminManagementController {

    private final UserService userService;

    @PostMapping("/recreate-admin")
    public ResponseEntity<String> recreateAdmin() {
        try {
            log.info("Received request to recreate admin user");
            userService.recreateAdminUser();
            return ResponseEntity.ok("Admin user recreated successfully");
        } catch (Exception e) {
            log.error("Error recreating admin user", e);
            return ResponseEntity.internalServerError()
                .body("Error recreating admin user: " + e.getMessage());
        }
    }
} 