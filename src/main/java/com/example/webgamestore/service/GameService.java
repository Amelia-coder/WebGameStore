package com.example.webgamestore.service;

import com.example.webgamestore.dto.GameDto;
import com.example.webgamestore.model.Game;
import com.example.webgamestore.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
    private final GameRepository gameRepository;
    private final DeveloperService developerService;
    private final PublisherService publisherService;

    public List<GameDto> getAllGames() {
        return gameRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
    public Game createGame(GameDto gameDto) {
        Game game = new Game();
        updateGameFromDto(game, gameDto);
        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, GameDto gameDto) {
        Game game = getGameById(id);
        updateGameFromDto(game, gameDto);
        return gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    public long countGames() {
        return gameRepository.count();
    }

    public GameDto convertToDto(Game game) {
        GameDto dto = new GameDto();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setDescription(game.getDescription());
        dto.setPrice(game.getPrice());
        dto.setReleaseDate(game.getReleaseDate());
        dto.setDeveloperId(game.getDeveloper().getId());
        dto.setPublisherId(game.getPublisher().getId());
        dto.setImageUrl(game.getImageUrl());
        return dto;
    }

    private void updateGameFromDto(Game game, GameDto dto) {
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setPrice(dto.getPrice());
        game.setReleaseDate(dto.getReleaseDate());
        game.setDeveloper(developerService.getDeveloperById(dto.getDeveloperId()));
        game.setPublisher(publisherService.getPublisherById(dto.getPublisherId()));
        game.setImageUrl(dto.getImageUrl());
    }
} 