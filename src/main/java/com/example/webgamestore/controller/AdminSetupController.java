package com.example.webgamestore.controller;

import com.example.webgamestore.model.User;
import com.example.webgamestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/setup")
@RequiredArgsConstructor
public class AdminSetupController {

    private final UserService userService;

    @PostMapping("/check")
    public ResponseEntity<String> checkAndSetupAdmin(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (!user.isAdmin()) {
            userService.makeAdmin(username);
            return ResponseEntity.ok("User " + username + " has been made an admin");
        }
        return ResponseEntity.ok("User " + username + " is already an admin");
    }
} 