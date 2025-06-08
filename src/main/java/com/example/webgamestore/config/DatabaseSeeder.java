package com.example.webgamestore.config;

import com.example.webgamestore.model.*;
import com.example.webgamestore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final DeveloperRepository developerRepository;
    private final PublisherRepository publisherRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Skip seeding if any data exists
        if (userRepository.count() > 0) {
            return;
        }

        // Create developers
        Developer valveDev = new Developer();
        valveDev.setName("Valve Corporation");
        valveDev.setDescription("American video game developer, publisher, and digital distribution company.");
        valveDev.setFoundedYear(1996);
        valveDev.setWebsiteUrl("https://www.valvesoftware.com");
        developerRepository.save(valveDev);

        Developer cdProjektDev = new Developer();
        cdProjektDev.setName("CD Projekt Red");
        cdProjektDev.setDescription("Polish video game developer, publisher and distributor based in Warsaw.");
        cdProjektDev.setFoundedYear(1994);
        cdProjektDev.setWebsiteUrl("https://www.cdprojektred.com");
        developerRepository.save(cdProjektDev);

        // Create publishers
        Publisher valvePub = new Publisher();
        valvePub.setName("Valve");
        valvePub.setDescription("Leading digital platform for PC gaming.");
        valvePub.setFoundedYear(1996);
        valvePub.setWebsiteUrl("https://www.valvesoftware.com");
        publisherRepository.save(valvePub);

        Publisher cdProjektPub = new Publisher();
        cdProjektPub.setName("CD Projekt");
        cdProjektPub.setDescription("Polish video game publisher and distributor.");
        cdProjektPub.setFoundedYear(1994);
        cdProjektPub.setWebsiteUrl("https://www.cdprojekt.com");
        publisherRepository.save(cdProjektPub);

        // Create games
        Game halfLife = new Game();
        halfLife.setTitle("Half-Life");
        halfLife.setDescription("Revolutionary first-person shooter that tells the story of Gordon Freeman.");
        halfLife.setPrice(new BigDecimal("9.99"));
        halfLife.setReleaseDate(LocalDate.of(1998, 11, 19));
        halfLife.setDeveloper(valveDev);
        halfLife.setPublisher(valvePub);
        halfLife.setImageUrl("/images/games/half-life.jpg");
        gameRepository.save(halfLife);

        Game cyberpunk = new Game();
        cyberpunk.setTitle("Cyberpunk 2077");
        cyberpunk.setDescription("Open-world action RPG set in Night City.");
        cyberpunk.setPrice(new BigDecimal("59.99"));
        cyberpunk.setReleaseDate(LocalDate.of(2020, 12, 10));
        cyberpunk.setDeveloper(cdProjektDev);
        cyberpunk.setPublisher(cdProjektPub);
        cyberpunk.setImageUrl("/images/games/cyberpunk-2077.jpg");
        gameRepository.save(cyberpunk);

        // Create users
        User john = new User();
        john.setUsername("john");
        john.setEmail("john@example.com");
        john.setPassword(passwordEncoder.encode("password123"));
        john.addRole("USER");
        john.setFirstName("John");
        john.setLastName("Doe");
        userRepository.save(john);

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.addRole("ADMIN");
        admin.addRole("USER"); // Admin should also have USER role
        admin.setFirstName("Admin");
        admin.setLastName("User");
        userRepository.save(admin);

        // Create orders (purchases)
        Order johnOrder = new Order();
        johnOrder.setUser(john);
        johnOrder.setStatus(OrderStatus.COMPLETED);
        johnOrder.setTotalAmount(halfLife.getPrice());

        OrderItem johnOrderItem = new OrderItem();
        johnOrderItem.setOrder(johnOrder);
        johnOrderItem.setGame(halfLife);
        johnOrderItem.setPricePerUnit(halfLife.getPrice());
        johnOrderItem.setQuantity(1);

        johnOrder.getOrderItems().add(johnOrderItem);
        orderRepository.save(johnOrder);

        Order adminOrder = new Order();
        adminOrder.setUser(admin);
        adminOrder.setStatus(OrderStatus.COMPLETED);
        adminOrder.setTotalAmount(cyberpunk.getPrice());

        OrderItem adminOrderItem = new OrderItem();
        adminOrderItem.setOrder(adminOrder);
        adminOrderItem.setGame(cyberpunk);
        adminOrderItem.setPricePerUnit(cyberpunk.getPrice());
        adminOrderItem.setQuantity(1);

        adminOrder.getOrderItems().add(adminOrderItem);
        orderRepository.save(adminOrder);
    }

    private boolean isDataExists() {
        return developerRepository.count() > 0 &&
               publisherRepository.count() > 0 &&
               gameRepository.count() > 0 &&
               userRepository.count() > 0 &&
               orderRepository.count() > 0;
    }
} 