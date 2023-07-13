package com.example.softunigamestore.service.impl;

import com.example.softunigamestore.model.dto.OwnedGamesDto;
import com.example.softunigamestore.model.dto.UserLoginDto;
import com.example.softunigamestore.model.dto.UserRegisterDto;
import com.example.softunigamestore.model.entity.User;
import com.example.softunigamestore.repository.UserRepository;
import com.example.softunigamestore.service.UserService;
import com.example.softunigamestore.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private User loggedInUser;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {

        if(!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Wrong confirm password");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations =
                validationUtil.getViolations(userRegisterDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User user = modelMapper.map(userRegisterDto, User.class);

        userRepository.save(user);

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violations =
                validationUtil.getViolations(userLoginDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User user = userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword())
                .orElse(null);

        if (user == null) {
            System.out.println("Incorrect username / password");
            return;
        }

        loggedInUser = user;

        System.out.println("Successfully logged in " + loggedInUser.getFullName());

    }

    @Override
    public void logout() {
        if (loggedInUser == null) {
            System.out.println("Cannot logout. No user was logged in.");
            return;
        }

        System.out.println("User " + loggedInUser.getFullName() + " successfully logged out.");
        loggedInUser = null;

    }

    @Override
    public List<OwnedGamesDto> ownedGames(User loggedInUser) {
        return userRepository.findAllByUser(loggedInUser.getId())
                .stream()
                .map(game -> modelMapper.map(game, OwnedGamesDto.class))
                .collect(Collectors.toList());
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
