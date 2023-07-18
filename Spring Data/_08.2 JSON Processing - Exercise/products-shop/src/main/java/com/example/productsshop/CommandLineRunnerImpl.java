package com.example.productsshop;

import com.example.productsshop.model.DTO.CategoriesByProductsSummaryDto;
import com.example.productsshop.model.DTO.ProductNamePriceAndSellerDto;
import com.example.productsshop.model.DTO.UserSoldDto;
import com.example.productsshop.model.DTO.UsersAndProductsDto;
import com.example.productsshop.service.CategoryService;
import com.example.productsshop.service.ProductService;
import com.example.productsshop.service.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String OUTPUT_FILES_PATH = "src/main/resources/files/out/";

    private static final String PRODUCT_IN_RANGE_FILE_NAME = "products-in-range.json";

    public static final String SUCCESSFULLY_SOLD_PRODUCTS_FILE_NAME = "user-sold-products.json";

    public static final String CATEGORIES_BY_PRODUCTS_COUNT_FILE_NAME = "categories-by-products.json";

    public static final String USERS_AND_PRODUCTS_FILE_NAME = "users-and-products.json";

    private final CategoryService categoryService;

    private final UserService userService;

    private final ProductService productService;

    private final Scanner scanner;

    private final Gson gson;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;

        scanner = new Scanner(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Enter exercise:");
        int exerciseNumber = Integer.parseInt(scanner.nextLine());

        switch (exerciseNumber) {
            case 1:
                productsInRange();
                break;
            case 2:
                soldProducts();
                break;
            case 3:
                categoriesBySoldProductsCount();
                break;
            case 4:
                usersAndProducts();
                break;
        }


    }

    private void usersAndProducts() throws IOException {
        UsersAndProductsDto usersAndProducts = userService.getUsersAndProducts();

        String content = gson.toJson(usersAndProducts);

        writeToFile(OUTPUT_FILES_PATH + USERS_AND_PRODUCTS_FILE_NAME, content);
    }

    private void categoriesBySoldProductsCount() throws IOException {
        List<CategoriesByProductsSummaryDto> categoryStatistics = productService.getCategoryStatistics();

        String content = gson.toJson(categoryStatistics);

        writeToFile(OUTPUT_FILES_PATH + CATEGORIES_BY_PRODUCTS_COUNT_FILE_NAME, content);



    }

    private void soldProducts() throws IOException {
        List<UserSoldDto> userSoldDtos = userService.findAllUsersWithMoreThanOneSoldProducts();

        String content = gson.toJson(userSoldDtos);

        writeToFile(OUTPUT_FILES_PATH + SUCCESSFULLY_SOLD_PRODUCTS_FILE_NAME, content);


    }

    private void productsInRange() throws IOException {
        List<ProductNamePriceAndSellerDto> productDtos = productService
                .findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));

        String content = gson.toJson(productDtos);

        writeToFile(OUTPUT_FILES_PATH + PRODUCT_IN_RANGE_FILE_NAME, content);
    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files.write(Path.of(filePath),
                Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();

    }
}
