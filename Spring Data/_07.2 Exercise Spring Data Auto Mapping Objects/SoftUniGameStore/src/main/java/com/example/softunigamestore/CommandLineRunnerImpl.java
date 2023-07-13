package com.example.softunigamestore;

import com.example.softunigamestore.model.dto.AllGamesDto;
import com.example.softunigamestore.model.dto.DetailGameDto;
import com.example.softunigamestore.model.dto.GameAddDto;
import com.example.softunigamestore.model.dto.OwnedGamesDto;
import com.example.softunigamestore.model.dto.UserLoginDto;
import com.example.softunigamestore.model.dto.UserRegisterDto;
import com.example.softunigamestore.service.GameService;
import com.example.softunigamestore.service.UserService;
import com.example.softunigamestore.service.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final Scanner scanner;

    private final UserService userService;

    private final UserServiceImpl userServiceImpl;

    private final GameService gameService;

    public CommandLineRunnerImpl(UserService userService, UserServiceImpl userServiceImpl, GameService gameService) {
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
        this.gameService = gameService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("Enter your commands:");
            String[] tokens = scanner.nextLine().split("\\|");
            String command = tokens[0];
            String email;
            String password;


            switch (command) {
                case "RegisterUser":
                    email = tokens[1];
                    password = tokens[2];
                    String confirmPassword = tokens[3];
                    String fullName = tokens[4];

                    userService.registerUser(
                            new UserRegisterDto(email, password, confirmPassword, fullName));

                    break;
                case "LoginUser":
                    email = tokens[1];
                    password = tokens[2];

                    userService.loginUser(new UserLoginDto(email, password));

                    break;
                case "Logout":
                    userService.logout();
                    break;
                case "AddGame":
                    gameService.addGame(new GameAddDto(tokens[1], new BigDecimal(tokens[2]), Double.parseDouble(tokens[3]), tokens[4], tokens[5], tokens[6], tokens[7]));
                    break;
                case "EditGame":
                    BigDecimal price = new BigDecimal(tokens[2].split("=")[1]);
                    Double size = Double.parseDouble(tokens[3].split("=")[1]);
                    gameService.editGame(Long.parseLong(tokens[1]), price, size);
                    break;
                case "DeleteGame":
                    gameService.deleteGame(Long.parseLong(tokens[1]));
                    break;
                case "AllGames":
                    List<AllGamesDto> allGames = gameService.allGames();

                    if (allGames.isEmpty()) {
                        System.out.println("No games");
                        return;
                    }

                    allGames.forEach(System.out::println);
                    break;
                case "DetailGame":
                    DetailGameDto detailGameDto = gameService.detailGame(tokens[1]);

                    if (detailGameDto == null) {
                        System.out.println("No such game");
                        return;
                    }

                    System.out.println(detailGameDto);
                    break;
                case "OwnedGames":

                    if (!isLoggedInUser()) {
                        return;
                    }

                    List<OwnedGamesDto> ownedGames = userService.ownedGames(userServiceImpl.getLoggedInUser());

                    if (ownedGames.isEmpty()) {
                        System.out.println("No ownedGames");
                        return;
                    }

                    ownedGames.forEach(System.out::println);

                    break;
            }

        }
    }

    private boolean isLoggedInUser() {
        if (userServiceImpl.getLoggedInUser() == null) {
            System.out.println("Please login!");
            return false;
        }

        return true;
    }
}
