package com.example.webgamestore.controller;

import com.example.webgamestore.model.Game;
import com.example.webgamestore.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Game>> searchGames(@RequestParam String title) {
        List<Game> games = gameRepository.findByTitleContainingIgnoreCase(title);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<Game>> getGamesByDeveloper(@PathVariable Long developerId) {
        List<Game> games = gameRepository.findByDeveloperId(developerId);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<Game>> getGamesByGenre(@PathVariable Long genreId) {
        List<Game> games = gameRepository.findByGenreId(genreId);
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game savedGame = gameRepository.save(game);
        return ResponseEntity.ok(savedGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game game) {
        if (!gameRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        game.setId(id);
        Game updatedGame = gameRepository.save(game);
        return ResponseEntity.ok(updatedGame);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        if (!gameRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gameRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 