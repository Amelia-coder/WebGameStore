package com.example.webgamestore.controller;

import com.example.webgamestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin-setup")
@RequiredArgsConstructor
public class AdminSetupController {

    private final UserService userService;

    @PostMapping("/fix-roles")
    public String fixAdminRoles() {
        userService.checkAndFixAdminRoles("admin");
        return "Admin roles checked and fixed if necessary";
    }

    @PostMapping("/recreate-admin")
    public String recreateAdmin() {
        userService.recreateAdminUser();
        return "Admin user recreated successfully";
    }
} 