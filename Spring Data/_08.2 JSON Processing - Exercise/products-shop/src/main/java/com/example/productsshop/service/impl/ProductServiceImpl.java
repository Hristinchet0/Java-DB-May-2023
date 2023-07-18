package com.example.productsshop.service.impl;

import com.example.productsshop.model.DTO.CategoriesByProductsSummaryDto;
import com.example.productsshop.model.DTO.ProductNamePriceAndSellerDto;
import com.example.productsshop.model.DTO.ProductSeedDto;
import com.example.productsshop.model.entity.Product;
import com.example.productsshop.repository.ProductRepository;
import com.example.productsshop.service.CategoryService;
import com.example.productsshop.service.ProductService;
import com.example.productsshop.service.UserService;
import com.example.productsshop.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productsshop.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCTS_FILE_NAME = "products.json";

    private final ProductRepository productRepository;

    private final UserService userService;

    private final CategoryService categoryService;

    private final Gson gson;

    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryService categoryService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedProducts() throws IOException {

        if (productRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(RESOURCES_FILE_PATH + PRODUCTS_FILE_NAME));

        ProductSeedDto[] productSeedDtos = gson.fromJson(fileContent, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.findRandomUser());

                    if (product.getPrice().compareTo(BigDecimal.valueOf(900L)) > 0) {
                        product.setBuyer(userService.findRandomUser());
                    }

                    product.setCategories(categoryService.findRandomCategories());

                    return product;
                })
                .forEach(productRepository::save);

    }

    @Override
    public List<ProductNamePriceAndSellerDto> findAllProductsInRangeOrderByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(minPrice, maxPrice)
                .stream()
                .map(product -> {
                    ProductNamePriceAndSellerDto productNameAndPriceDto = modelMapper
                            .map(product, ProductNamePriceAndSellerDto.class);

                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productNameAndPriceDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriesByProductsSummaryDto> getCategoryStatistics() {
        return  this.productRepository.getCategoryStatus();
    }
}
