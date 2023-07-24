package com.example.productsshop;

import com.example.productsshop.model.DTO.categoriesDtos.CategoriesViewProductsDto;
import com.example.productsshop.model.DTO.categoriesDtos.CategorySeedRootDto;
import com.example.productsshop.model.DTO.productsDtos.ProductSeedRootDto;
import com.example.productsshop.model.DTO.productsDtos.ProductViewRootDto;
import com.example.productsshop.model.DTO.usersDtos.UserSeedRootDto;
import com.example.productsshop.model.DTO.usersDtos.UserViewRootDto;
import com.example.productsshop.service.CategoryService;
import com.example.productsshop.service.ProductService;
import com.example.productsshop.service.UserService;
import com.example.productsshop.util.XMLParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String RESOURCES_FILES_PATH = "src/main/resources/files/";

    private static final String OUTPUT_FILES_PATH = "out/";

    private static final String CATEGORIES_FILE_NAME = "categories.xml";

    private static final String USERS_FILE_NAME = "users.xml";

    private static final String PRODUCTS_FILE_NAME = "products.xml";

    public static final String PRODUCTS_IN_RANGE_FILE_NAME = "productsInRange.xml";

    public static final String SUCCESSFULLY_SOLD_PRODUCTS_FILE_NAME = "successfullySoldProducts.xml";

    public static final String CATEGORIES_BY_PRODUCTS_FILE_NAME = "categoriesByProducts.xml";


    private final CategoryService categoryService;

    private final UserService userService;

    private final ProductService productService;

    private final BufferedReader bufferedReader;

    private final XMLParser xmlParser;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, XMLParser xmlParser) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.xmlParser = xmlParser;

        bufferedReader = new BufferedReader((new InputStreamReader(System.in)));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Select Exercise:");

        int exNumber = Integer.parseInt(bufferedReader.readLine());

        switch (exNumber) {
            case 1:
                productsInRange();
                break;
            case 2:
                successfullySoldProducts();
                break;
            case 3:
                categoriesByProductsCount();
                break;
            case 4:
                usersAndProducts();
        }

    }

    private void usersAndProducts() {
//        userService.usersWithSoldProducts();
    }

    private void categoriesByProductsCount() throws JAXBException {
        CategoriesViewProductsDto categoriesViewProductsDto = categoryService.categoriesByProductsCount();

        xmlParser.writeToFile(RESOURCES_FILES_PATH + OUTPUT_FILES_PATH + CATEGORIES_BY_PRODUCTS_FILE_NAME, categoriesViewProductsDto);

        System.out.println("File " + CATEGORIES_BY_PRODUCTS_FILE_NAME + " is successfully saved in " + RESOURCES_FILES_PATH + OUTPUT_FILES_PATH);


    }

    private void successfullySoldProducts() throws JAXBException {
        UserViewRootDto userViewRootDto = userService.findUsersWithMoreThanOneSoldProduct();

        xmlParser.writeToFile(RESOURCES_FILES_PATH + OUTPUT_FILES_PATH + SUCCESSFULLY_SOLD_PRODUCTS_FILE_NAME, userViewRootDto);

        System.out.println("File " + SUCCESSFULLY_SOLD_PRODUCTS_FILE_NAME + " is successfully saved in " + RESOURCES_FILES_PATH + OUTPUT_FILES_PATH);
    }

    private void productsInRange() throws JAXBException {
        ProductViewRootDto rootDto = productService
                .findProductInRangeWithoutBuyer();

        xmlParser.writeToFile(RESOURCES_FILES_PATH + OUTPUT_FILES_PATH + PRODUCTS_IN_RANGE_FILE_NAME, rootDto);

        System.out.println("File " + PRODUCTS_IN_RANGE_FILE_NAME + " is successfully saved in " + RESOURCES_FILES_PATH + OUTPUT_FILES_PATH);

    }

    private void seedData() throws IOException, JAXBException {

        if (categoryService.getEntityCount() == 0) {
            CategorySeedRootDto categorySeedRootDto = xmlParser.FromFile(RESOURCES_FILES_PATH + CATEGORIES_FILE_NAME,
                    CategorySeedRootDto.class);

            categoryService.seedCategories(categorySeedRootDto.getCategories());
        }

        if (userService.getEntityCount() == 0) {
            UserSeedRootDto userSeedRootDto = xmlParser.FromFile(RESOURCES_FILES_PATH + USERS_FILE_NAME,
                    UserSeedRootDto.class);

            userService.seedUsers(userSeedRootDto.getUsers());
        }

        if (productService.getEntityCount() == 0) {
            ProductSeedRootDto ProductSeedRootDto = xmlParser.FromFile(RESOURCES_FILES_PATH + PRODUCTS_FILE_NAME,
                    ProductSeedRootDto.class);

            productService.seedProducts(ProductSeedRootDto.getProducts());

        }

    }
}
