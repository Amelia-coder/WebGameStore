package com.example.webgamestore.controller;

import com.example.webgamestore.dto.CurrentUserDto;
import com.example.webgamestore.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserAuthController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.ok(null);
        }

        CurrentUserDto currentUser = new CurrentUserDto(
            userDetails.getUsername(),
            "/images/avatar.png",
            userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
        );

        return ResponseEntity.ok(currentUser);
    }
} 