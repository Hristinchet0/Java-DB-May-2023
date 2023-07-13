package com.example.softunigamestore.service.impl;

import com.example.softunigamestore.model.dto.AllGamesDto;
import com.example.softunigamestore.model.dto.DetailGameDto;
import com.example.softunigamestore.model.dto.GameAddDto;
import com.example.softunigamestore.model.entity.Game;
import com.example.softunigamestore.repository.GameRepository;
import com.example.softunigamestore.service.GameService;
import com.example.softunigamestore.service.UserService;
import com.example.softunigamestore.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {

        Set<ConstraintViolation<GameAddDto>> violations =
                validationUtil.getViolations(gameAddDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        Game game = modelMapper.map(gameAddDto, Game.class);

        game.setReleaseDate(LocalDate.parse(gameAddDto.getReleaseDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        gameRepository.save(game);

        System.out.println("Added " + gameAddDto.getTitle());

    }

    @Override
    public void editGame(Long gameId, BigDecimal price, Double size) {
        Game game = gameRepository.findById(gameId)
                .orElse(null);

        if (game == null) {
            System.out.println("Id is not correct");
            return;
        }

        game.setPrice(price);
        game.setSize(size);

        gameRepository.save(game);

        System.out.println("Edited " + game.getTitle());
    }

    @Override
    public void deleteGame(long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElse(null);

        if (game == null) {
            System.out.println("Id is not correct");
            return;
        }

        gameRepository.deleteById(gameId);

        System.out.println("Deleted " + game.getTitle());
    }

    @Override
    public List<AllGamesDto> allGames() {
        return gameRepository.findAll()
                .stream()
                .map(game -> modelMapper.map(game, AllGamesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DetailGameDto detailGame(String title) {

        Game game = gameRepository.findGameByTitle(title);

        DetailGameDto detailGameDto = null;

        if (game != null) {
            detailGameDto = modelMapper.map(game, DetailGameDto.class);
        }

        return detailGameDto;
    }


}
