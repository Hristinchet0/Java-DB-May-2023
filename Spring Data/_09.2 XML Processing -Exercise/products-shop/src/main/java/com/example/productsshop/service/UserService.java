package com.example.productsshop.service;

import com.example.productsshop.model.DTO.usersDtos.UserSeedDto;
import com.example.productsshop.model.DTO.usersDtos.UserViewRootDto;
import com.example.productsshop.model.DTO.usersDtos.UsersSoldProductsWithAgeDto;
import com.example.productsshop.model.entity.User;

import java.util.List;

public interface UserService {

    long getEntityCount();

    void seedUsers(List<UserSeedDto> users);

    User getRandomUser();

    UserViewRootDto findUsersWithMoreThanOneSoldProduct();

}
