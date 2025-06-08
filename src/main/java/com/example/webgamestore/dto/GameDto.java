package com.example.webgamestore.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class GameDto {
    private Long id;
    private String title;
    private String description;
    private Set<Long> genreIds = new HashSet<>();
    private BigDecimal price;
    private String imageUrl;
    private LocalDate releaseDate;
    private Long developerId;
    private Long publisherId;
} 