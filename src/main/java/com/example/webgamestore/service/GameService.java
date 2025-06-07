package com.example.webgamestore.service;

import com.example.webgamestore.model.Game;
import com.example.webgamestore.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
    private final GameRepository gameRepository;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }

    public List<Game> getGamesByDeveloper(Long developerId) {
        return gameRepository.findByDeveloperId(developerId);
    }

    public List<Game> getGamesByGenre(Long genreId) {
        return gameRepository.findByGenreId(genreId);
    }

    public List<Game> searchGamesByTitle(String title) {
        return gameRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, Game gameDetails) {
        Game game = getGameById(id);
        game.setTitle(gameDetails.getTitle());
        game.setDescription(gameDetails.getDescription());
        game.setPrice(gameDetails.getPrice());
        game.setReleaseDate(gameDetails.getReleaseDate());
        game.setDeveloper(gameDetails.getDeveloper());
        game.setPublisher(gameDetails.getPublisher());
        game.setGenres(gameDetails.getGenres());
        return gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
} 