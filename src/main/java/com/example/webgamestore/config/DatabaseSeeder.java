package com.example.webgamestore.config;

import com.example.webgamestore.model.*;
import com.example.webgamestore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
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

    @Override
    @Transactional
    public void run(String... args) {
        if (isDataExists()) {
            return;
        }

        // Create developers
        Developer valve = new Developer();
        valve.setName("Valve Corporation");
        valve.setCountry("USA");
        valve.setDescription("Creator of Steam and legendary games");
        developerRepository.save(valve);

        Developer cdProjekt = new Developer();
        cdProjekt.setName("CD Projekt RED");
        cdProjekt.setCountry("Poland");
        cdProjekt.setDescription("Creator of The Witcher series");
        developerRepository.save(cdProjekt);

        // Create publishers
        Publisher valvePublisher = new Publisher();
        valvePublisher.setName("Valve");
        valvePublisher.setCountry("USA");
        valvePublisher.setDescription("Leading game distribution company");
        publisherRepository.save(valvePublisher);

        Publisher cdProjektPublisher = new Publisher();
        cdProjektPublisher.setName("CD Projekt");
        cdProjektPublisher.setCountry("Poland");
        cdProjektPublisher.setDescription("Publisher of The Witcher and Cyberpunk 2077");
        publisherRepository.save(cdProjektPublisher);

        // Create games
        Game halfLife = new Game();
        halfLife.setTitle("Half-Life");
        halfLife.setDescription("Revolutionary sci-fi FPS game");
        halfLife.setPrice(new BigDecimal("19.99"));
        halfLife.setReleaseDate(LocalDate.of(1998, 11, 19));
        halfLife.setDeveloper(valve);
        halfLife.setPublisher(valvePublisher);
        gameRepository.save(halfLife);

        Game cyberpunk = new Game();
        cyberpunk.setTitle("Cyberpunk 2077");
        cyberpunk.setDescription("Open-world action RPG set in Night City");
        cyberpunk.setPrice(new BigDecimal("59.99"));
        cyberpunk.setReleaseDate(LocalDate.of(2020, 12, 10));
        cyberpunk.setDeveloper(cdProjekt);
        cyberpunk.setPublisher(cdProjektPublisher);
        gameRepository.save(cyberpunk);

        // Create users
        User john = new User();
        john.setUsername("john");
        john.setEmail("john@example.com");
        john.setPassword("password123"); // In real app, should be encrypted
        john.setRole(UserRole.USER);
        john.setFirstName("John");
        john.setLastName("Doe");
        userRepository.save(john);

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword("admin123"); // In real app, should be encrypted
        admin.setRole(UserRole.ADMIN);
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
        johnOrderItem.setPrice(halfLife.getPrice());
        johnOrderItem.setQuantity(1);

        johnOrder.setOrderItems(Arrays.asList(johnOrderItem));
        orderRepository.save(johnOrder);

        Order adminOrder = new Order();
        adminOrder.setUser(admin);
        adminOrder.setStatus(OrderStatus.COMPLETED);
        adminOrder.setTotalAmount(cyberpunk.getPrice());

        OrderItem adminOrderItem = new OrderItem();
        adminOrderItem.setOrder(adminOrder);
        adminOrderItem.setGame(cyberpunk);
        adminOrderItem.setPrice(cyberpunk.getPrice());
        adminOrderItem.setQuantity(1);

        adminOrder.setOrderItems(Arrays.asList(adminOrderItem));
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