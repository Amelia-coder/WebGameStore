package com.example.webgamestore.repository;

import com.example.webgamestore.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByDeveloperId(Long developerId);
    
    @Query("SELECT g FROM Game g JOIN g.genres genre WHERE genre.id = :genreId")
    List<Game> findByGenreId(@Param("genreId") Long genreId);
    
    List<Game> findByTitleContainingIgnoreCase(String title);
} 