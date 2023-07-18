package com.example.productsshop.service;

import com.example.productsshop.model.DTO.UserSoldDto;
import com.example.productsshop.model.DTO.UsersAndProductsDto;
import com.example.productsshop.model.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();


    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts();


    UsersAndProductsDto getUsersAndProducts();
}
