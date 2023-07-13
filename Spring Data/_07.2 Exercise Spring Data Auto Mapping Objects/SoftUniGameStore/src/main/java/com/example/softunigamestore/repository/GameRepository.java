package com.example.softunigamestore.repository;

import com.example.softunigamestore.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameByTitle(String title);
}
