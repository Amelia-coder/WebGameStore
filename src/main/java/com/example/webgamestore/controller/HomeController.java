package com.example.webgamestore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/info")
public class HomeController {

    @GetMapping
    public ResponseEntity<Map<String, String>> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to WebGameStore API");
        response.put("version", "1.0");
        response.put("status", "running");
        return ResponseEntity.ok(response);
    }
} 