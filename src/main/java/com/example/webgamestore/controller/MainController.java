package com.example.webgamestore.controller;

import com.example.webgamestore.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final GameService gameService;
    
    @GetMapping({"/", "/index", "/home"})
    public String index(Model model) {
        model.addAttribute("activePage", "home");
        model.addAttribute("games", gameService.getAllGamesWithDetails());
        return "index";
    }

    @GetMapping("/store")
    public String store(Model model) {
        model.addAttribute("activePage", "store");
        model.addAttribute("games", gameService.getAllGamesWithDetails());
        return "store";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("activePage", "about");
        return "about";
    }
} 