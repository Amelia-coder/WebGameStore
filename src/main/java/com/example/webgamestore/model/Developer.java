package com.example.webgamestore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "developers")
@Getter
@Setter
@NoArgsConstructor
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @Column(name = "website_url")
    private String websiteUrl;
} 