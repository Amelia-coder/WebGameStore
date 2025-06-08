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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    @Transactional
    public Game createGame(GameDto gameDto) {
        Game game = new Game();
        updateGameFromDto(game, gameDto);
        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, GameDto gameDto) {
        Game game = getGameById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
        updateGameFromDto(game, gameDto);
        return gameRepository.save(game);
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
        return dto;
    }

    private void updateGameFromDto(Game game, GameDto dto) {
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        
        game.getGenres().clear();
        if (dto.getGenreIds() != null) {
            dto.getGenreIds().forEach(genreId -> {
                Genre genre = genreRepository.findById(genreId)
                        .orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId));
                game.getGenres().add(genre);
            });
        }

        if (dto.getDeveloperId() != null) {
            game.setDeveloper(developerRepository.findById(dto.getDeveloperId())
                    .orElseThrow(() -> new RuntimeException("Developer not found with id: " + dto.getDeveloperId())));
        }

        if (dto.getPublisherId() != null) {
            game.setPublisher(publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + dto.getPublisherId())));
        }

        game.setPrice(dto.getPrice());
        game.setImageUrl(dto.getImageUrl());
    }

    public long countGames() {
        return gameRepository.count();
    }
} 