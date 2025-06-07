package com.example.webgamestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUserDto {
    private String username;
    private String avatarUrl;
    private boolean isAdmin;
} 