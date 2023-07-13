package com.example.softunigamestore.service;

import com.example.softunigamestore.model.dto.OwnedGamesDto;
import com.example.softunigamestore.model.dto.UserLoginDto;
import com.example.softunigamestore.model.dto.UserRegisterDto;
import com.example.softunigamestore.model.entity.User;

import java.util.List;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();

    List<OwnedGamesDto> ownedGames(User loggedInUser);
}
