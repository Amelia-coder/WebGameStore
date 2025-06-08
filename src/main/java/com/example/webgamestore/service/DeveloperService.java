package com.example.webgamestore.service;

import com.example.webgamestore.model.Developer;
import com.example.webgamestore.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Developer getDeveloperById(Long id) {
        return developerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Developer not found with id: " + id));
    }

    @Transactional
    public Developer createDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }

    @Transactional
    public Developer updateDeveloper(Long id, Developer developerDetails) {
        Developer developer = getDeveloperById(id);
        developer.setName(developerDetails.getName());
        developer.setDescription(developerDetails.getDescription());
        developer.setFoundedYear(developerDetails.getFoundedYear());
        developer.setWebsiteUrl(developerDetails.getWebsiteUrl());
        return developerRepository.save(developer);
    }

    @Transactional
    public void deleteDeveloper(Long id) {
        developerRepository.deleteById(id);
    }
} 