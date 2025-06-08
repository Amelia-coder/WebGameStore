package com.example.webgamestore.config;

import com.example.webgamestore.model.*;
import com.example.webgamestore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final DeveloperRepository developerRepository;
    private final PublisherRepository publisherRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Skip seeding if any data exists
        if (userRepository.count() > 0) {
            return;
        }

        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gamestore.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.addRole("ADMIN");
        admin.addRole("USER");
        admin.setFirstName("Admin");
        admin.setLastName("User");
        userRepository.save(admin);

        // Create genres
        Genre actionGenre = new Genre();
        actionGenre.setName("ACTION");
        actionGenre.setDescription("Action games focus on physical challenges that require hand-eye coordination and motor skills.");
        genreRepository.save(actionGenre);

        Genre rpgGenre = new Genre();
        rpgGenre.setName("RPG");
        rpgGenre.setDescription("Role-playing games where players assume the roles of characters in a fictional setting.");
        genreRepository.save(rpgGenre);

        // Create developers
        Developer valveDev = new Developer();
        valveDev.setName("Valve Corporation");
        valveDev.setDescription("American video game developer, publisher, and digital distribution company.");
        valveDev.setFoundedYear(1996);
        valveDev.setWebsiteUrl("https://www.valvesoftware.com");
        developerRepository.save(valveDev);

        Developer cdProjektDev = new Developer();
        cdProjektDev.setName("CD Projekt Red");
        cdProjektDev.setDescription("Polish video game developer, publisher and distributor.");
        cdProjektDev.setFoundedYear(1994);
        cdProjektDev.setWebsiteUrl("https://www.cdprojektred.com");
        developerRepository.save(cdProjektDev);

        // Create publishers
        Publisher valvePub = new Publisher();
        valvePub.setName("Valve");
        valvePub.setDescription("Video game publisher and digital distribution company.");
        valvePub.setFoundedYear(1996);
        valvePub.setWebsiteUrl("https://www.valvesoftware.com");
        publisherRepository.save(valvePub);

        Publisher cdProjektPub = new Publisher();
        cdProjektPub.setName("CD Projekt");
        cdProjektPub.setDescription("Video game publisher and distributor.");
        cdProjektPub.setFoundedYear(1994);
        cdProjektPub.setWebsiteUrl("https://www.cdprojekt.com");
        publisherRepository.save(cdProjektPub);

        // Create example games
        Game halfLife = new Game();
        halfLife.setTitle("Half-Life");
        halfLife.setDescription("Revolutionary first-person shooter that tells the story of Gordon Freeman.");
        halfLife.getGenres().add(actionGenre);
        halfLife.setDeveloper(valveDev);
        halfLife.setPublisher(valvePub);
        halfLife.setPrice(new BigDecimal("9.99"));
        halfLife.setImageUrl("/images/games/half-life.jpg");
        gameRepository.save(halfLife);

        Game cyberpunk = new Game();
        cyberpunk.setTitle("Cyberpunk 2077");
        cyberpunk.setDescription("Open-world action RPG set in Night City.");
        cyberpunk.getGenres().add(actionGenre);
        cyberpunk.getGenres().add(rpgGenre);
        cyberpunk.setDeveloper(cdProjektDev);
        cyberpunk.setPublisher(cdProjektPub);
        cyberpunk.setPrice(new BigDecimal("59.99"));
        cyberpunk.setImageUrl("/images/games/cyberpunk-2077.jpg");
        gameRepository.save(cyberpunk);
    }
} 