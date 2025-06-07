package com.example.webgamestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    
    @GetMapping({"/", "/index", "/home"})
    public String index(Model model) {
        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/store")
    public String store(Model model) {
        model.addAttribute("activePage", "store");
        return "store";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("activePage", "about");
        return "about";
    }
} 