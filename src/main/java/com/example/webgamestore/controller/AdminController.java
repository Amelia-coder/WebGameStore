package com.example.webgamestore.controller;

import com.example.webgamestore.dto.GameDto;
import com.example.webgamestore.model.Game;
import com.example.webgamestore.service.DeveloperService;
import com.example.webgamestore.service.GameService;
import com.example.webgamestore.service.GenreService;
import com.example.webgamestore.service.PublisherService;
import com.example.webgamestore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final GameService gameService;
    private final UserService userService;
    private final DeveloperService developerService;
    private final PublisherService publisherService;
    private final GenreService genreService;

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        model.addAttribute("totalGames", gameService.countGames());
        model.addAttribute("totalUsers", userService.countUsers());
        // You can add more statistics here
        return "admin/dashboard";
    }

    @GetMapping("/games")
    public String listGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "admin/games";
    }

    @GetMapping("/games/add")
    public String showAddGameForm(Model model) {
        if (!model.containsAttribute("game")) {
            model.addAttribute("game", new GameDto());
        }
        addGameFormAttributes(model);
        return "admin/game-form";
    }

    @PostMapping("/games/add")
    public String addGame(@Valid @ModelAttribute("game") GameDto gameDto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            addGameFormAttributes(model);
            return "admin/game-form";
        }

        try {
            gameService.createGame(gameDto);
            redirectAttributes.addFlashAttribute("successMessage", "Game added successfully!");
            return "redirect:/admin/games";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding game: " + e.getMessage());
            redirectAttributes.addFlashAttribute("game", gameDto);
            return "redirect:/admin/games/add";
        }
    }

    @GetMapping("/games/edit/{id}")
    public String showEditGameForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Game game = gameService.getGameById(id)
                    .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
            GameDto gameDto = gameService.convertToDto(game);
            model.addAttribute("game", gameDto);
            addGameFormAttributes(model);
            return "admin/game-form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/games";
        }
    }

    @PostMapping("/games/edit/{id}")
    public String updateGame(@PathVariable Long id,
                           @Valid @ModelAttribute("game") GameDto gameDto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (result.hasErrors()) {
            addGameFormAttributes(model);
            return "admin/game-form";
        }

        try {
            gameService.updateGame(id, gameDto);
            redirectAttributes.addFlashAttribute("successMessage", "Game updated successfully!");
            return "redirect:/admin/games";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating game: " + e.getMessage());
            redirectAttributes.addFlashAttribute("game", gameDto);
            return "redirect:/admin/games/edit/" + id;
        }
    }

    @PostMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            gameService.deleteGame(id);
            redirectAttributes.addFlashAttribute("successMessage", "Game deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting game: " + e.getMessage());
        }
        return "redirect:/admin/games";
    }

    private void addGameFormAttributes(Model model) {
        model.addAttribute("developers", developerService.getAllDevelopers());
        model.addAttribute("publishers", publisherService.getAllPublishers());
        model.addAttribute("genres", genreService.getAllGenres());
    }
} 