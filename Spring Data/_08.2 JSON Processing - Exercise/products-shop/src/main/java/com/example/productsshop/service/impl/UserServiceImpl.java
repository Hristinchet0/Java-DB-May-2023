package com.example.productsshop.service.impl;

import com.example.productsshop.model.DTO.UserSeedDto;
import com.example.productsshop.model.DTO.UserSoldDto;
import com.example.productsshop.model.DTO.UsersAndProductsDto;
import com.example.productsshop.model.DTO.UsersSoldProductsWithAgeDto;
import com.example.productsshop.model.entity.User;
import com.example.productsshop.repository.UserRepository;
import com.example.productsshop.service.UserService;
import com.example.productsshop.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.productsshop.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_NAME = "users.json";

    private final UserRepository userRepository;

    private final Gson gson;

    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws IOException {

//        if(userRepository.count() > 0) {
//            return;
//        }
//
//        String fileContent = Files.readString(Path.of(RESOURCES_FILE_PATH + USERS_FILE_NAME));
//
//        UserSeedDto[] userSeedDtos = gson.fromJson(fileContent, UserSeedDto[].class);
//
//        Arrays.stream(userSeedDtos)
//                .filter(validationUtil::isValid)
//                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
//                .forEach(userRepository::save);

        if (userRepository.count() == 0) {
            Arrays.stream(gson.fromJson(
                    Files.readString(Path.of(RESOURCES_FILE_PATH + USERS_FILE_NAME)),
                    UserSeedDto[].class))
                    .filter(validationUtil::isValid)
                    .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                    .forEach(userRepository::save);
        }




    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts() {
        return userRepository.findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenByFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsersAndProductsDto getUsersAndProducts() {
        List<User> users = userRepository.findAllUsersWithSoldProductsOrderByCountAndLastName().orElse(null);

        List<UsersSoldProductsWithAgeDto> usersSoldProductsWithAgeDtoList = users
                .stream()
                .map(u -> {
                    UsersSoldProductsWithAgeDto user = modelMapper.map(u, UsersSoldProductsWithAgeDto.class);

                    user.getProducts().setCount(u.getSoldProducts().size());

                    return user;
                })
                .collect(Collectors.toList());

        UsersAndProductsDto usersAndProductsDto = new UsersAndProductsDto(usersSoldProductsWithAgeDtoList);

        return usersAndProductsDto;
    }
}
