package com.example.webgamestore.service;

import com.example.webgamestore.dto.UserRegistrationDto;
import com.example.webgamestore.model.User;
import com.example.webgamestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        // Check if username is already taken
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email is already taken
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.addRole("USER");

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // By default, assign USER role
        user.addRole("USER");
        return userRepository.save(user);
    }

    @Transactional
    public User createAdminUser(String username, String password) {
        User admin = new User();
        admin.setUsername(username);
        admin.setEmail("admin@gamestore.com");
        admin.setPassword(passwordEncoder.encode(password));
        admin.addRole("ADMIN");
        admin.addRole("USER"); // Admin should also have USER role
        return userRepository.save(admin);
    }

    @Transactional
    public void addAdminRole(String username) {
        User user = findByUsername(username);
        user.addRole("ADMIN");
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public long countUsers() {
        return userRepository.count();
    }

    @Transactional
    public void checkAndFixAdminRoles(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Check if user has ADMIN role
        if (!user.getRoles().contains("ADMIN")) {
            user.addRole("ADMIN");
            userRepository.save(user);
        }

        // Check if user has USER role
        if (!user.getRoles().contains("USER")) {
            user.addRole("USER");
            userRepository.save(user);
        }
    }

    @Transactional
    public void recreateAdminUser() {
        // Delete existing admin user if exists
        userRepository.findByUsername("admin").ifPresent(admin -> 
            userRepository.delete(admin)
        );

        // Create new admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gamestore.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.addRole("ADMIN");
        admin.addRole("USER");
        userRepository.save(admin);
    }
} 