package com.example.productsshop.service.impl;

import com.example.productsshop.model.DTO.usersDtos.UserAndProductDto;
import com.example.productsshop.model.DTO.usersDtos.UserSeedDto;
import com.example.productsshop.model.DTO.usersDtos.UserViewRootDto;
import com.example.productsshop.model.DTO.usersDtos.UserWithProductsDto;
import com.example.productsshop.model.DTO.usersDtos.UsersSoldProductsWithAgeDto;
import com.example.productsshop.model.entity.User;
import com.example.productsshop.repository.UserRepository;
import com.example.productsshop.service.UserService;
import com.example.productsshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public long getEntityCount() {
        return userRepository.count();
    }

    @Override
    public void seedUsers(List<UserSeedDto> users) {
        users.stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);

    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current()
                .nextLong(1, userRepository.count() + 1);
        return userRepository.findById(randomId)
                .orElse(null);
    }

    @Override
    public UserViewRootDto findUsersWithMoreThanOneSoldProduct() {
        UserViewRootDto userViewRootDto = new UserViewRootDto();

        userViewRootDto.setProducts(userRepository.findAllUsersWithMoreThanOneSoldProduct()
                .stream()
                .map(user -> modelMapper.map(user, UserWithProductsDto.class))
                .collect(Collectors.toList()));


        return userViewRootDto;
    }




}
