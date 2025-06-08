package com.example.webgamestore.service;

import com.example.webgamestore.model.User;
import com.example.webgamestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Attempting to load user by username: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
        
        log.debug("User found: {}", user.getUsername());
        log.debug("User role: {}", user.getRole());
        log.debug("User authorities: {}", user.getAuthorities());
        log.debug("Is password empty: {}", user.getPassword() == null || user.getPassword().isEmpty());
        log.debug("Password hash: {}", user.getPassword());
        
        return user;
    }
} 