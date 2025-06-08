package com.example.webgamestore.service;

import com.example.webgamestore.dto.GameDto;
import com.example.webgamestore.model.Game;
import com.example.webgamestore.model.Genre;
import com.example.webgamestore.repository.GameRepository;
import com.example.webgamestore.repository.GenreRepository;
import com.example.webgamestore.repository.DeveloperRepository;
import com.example.webgamestore.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final DeveloperRepository developerRepository;
    private final PublisherRepository publisherRepository;

    public List<GameDto> getAllGames() {
        return gameRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Game> getAllGamesWithDetails() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    @Transactional
    public Game createGame(GameDto gameDto) {
        validateGameDto(gameDto);
        Game game = new Game();
        updateGameFromDto(game, gameDto);
        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, GameDto gameDto) {
        validateGameDto(gameDto);
        Game game = getGameById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
        updateGameFromDto(game, gameDto);
        return gameRepository.save(game);
    }

    private void validateGameDto(GameDto gameDto) {
        if (gameDto.getTitle() == null || gameDto.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Title is required");
        }
        if (gameDto.getPrice() == null || gameDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }
        if (gameDto.getDeveloperId() == null) {
            throw new RuntimeException("Developer is required");
        }
        if (gameDto.getPublisherId() == null) {
            throw new RuntimeException("Publisher is required");
        }
        if (gameDto.getGenreIds() == null || gameDto.getGenreIds().isEmpty()) {
            throw new RuntimeException("At least one genre must be selected");
        }
    }

    @Transactional
    public void deleteGame(Long id) {
        Game game = getGameById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
        gameRepository.delete(game);
    }

    public GameDto convertToDto(Game game) {
        GameDto dto = new GameDto();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setDescription(game.getDescription());
        dto.setGenreIds(game.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet()));
        dto.setDeveloperId(game.getDeveloper() != null ? game.getDeveloper().getId() : null);
        dto.setPublisherId(game.getPublisher() != null ? game.getPublisher().getId() : null);
        dto.setPrice(game.getPrice());
        dto.setImageUrl(game.getImageUrl());
        dto.setReleaseDate(game.getReleaseDate());
        return dto;
    }

    private void updateGameFromDto(Game game, GameDto dto) {
        game.setTitle(dto.getTitle().trim());
        game.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : null);
        game.setPrice(dto.getPrice());
        game.setImageUrl(dto.getImageUrl() != null ? dto.getImageUrl().trim() : null);
        game.setReleaseDate(dto.getReleaseDate());
        
        // Update developer
        game.setDeveloper(developerRepository.findById(dto.getDeveloperId())
                .orElseThrow(() -> new RuntimeException("Developer not found with id: " + dto.getDeveloperId())));

        // Update publisher
        game.setPublisher(publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + dto.getPublisherId())));

        // Update genres
        Set<Genre> genres = new HashSet<>();
        for (Long genreId : dto.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId));
            genres.add(genre);
        }
        game.setGenres(genres);
    }

    public long countGames() {
        return gameRepository.count();
    }
} 