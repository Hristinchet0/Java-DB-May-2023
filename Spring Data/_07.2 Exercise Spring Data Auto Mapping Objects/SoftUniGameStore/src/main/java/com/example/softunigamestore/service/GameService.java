package com.example.softunigamestore.service;

import com.example.softunigamestore.model.dto.AllGamesDto;
import com.example.softunigamestore.model.dto.DetailGameDto;
import com.example.softunigamestore.model.dto.GameAddDto;

import java.math.BigDecimal;
import java.util.List;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void editGame(Long gameId, BigDecimal price, Double size);

    void deleteGame(long gameId);

    List<AllGamesDto> allGames();

    DetailGameDto detailGame(String title);
}
