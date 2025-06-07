package com.example.webgamestore.controller;

import com.example.webgamestore.dto.UserRegistrationDto;
import com.example.webgamestore.model.User;
import com.example.webgamestore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserRegistrationDto());
        }
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            // Register the user
            User user = userService.registerUser(registrationDto);
            
            // Automatically authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    registrationDto.getUsername(),
                    registrationDto.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Welcome to GameStore!");
            return "redirect:/";
        } catch (RuntimeException e) {
            result.rejectValue("username", "error.user", e.getMessage());
            return "register";
        }
    }
} 