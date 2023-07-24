package com.example.productsshop.service.impl;

import com.example.productsshop.model.DTO.productsDtos.ProductSeedDto;
import com.example.productsshop.model.DTO.productsDtos.ProductViewRootDto;
import com.example.productsshop.model.DTO.productsDtos.ProductWithSellerDto;
import com.example.productsshop.model.entity.Product;
import com.example.productsshop.repository.ProductRepository;
import com.example.productsshop.service.CategoryService;
import com.example.productsshop.service.ProductService;
import com.example.productsshop.service.UserService;
import com.example.productsshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final UserService userService;

    private final CategoryService categoryService;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public long getEntityCount() {
        return productRepository.count();
    }

    @Override
    public void seedProducts(List<ProductSeedDto> products) {

        products.stream()
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.getRandomUser());

                    if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {
                        product.setBuyer(userService.getRandomUser());
                    }

                    product.setCategories(categoryService.getRandomCategories());

                    return product;

                })
                .forEach(productRepository::save);

    }

    @Override
    public ProductViewRootDto findProductInRangeWithoutBuyer() {
        ProductViewRootDto rootDto = new ProductViewRootDto();

        rootDto.setProducts(
                productRepository.findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L))
                        .stream()
                        .map(product -> {
                            ProductWithSellerDto productWithSellerDto = modelMapper.map(product, ProductWithSellerDto.class);
                            productWithSellerDto.setSeller(String.format("%s %s",
                                    product.getSeller().getFirstName()  == null ? "" :product.getSeller().getFirstName(),
                                    product.getSeller().getLastName()));

                            return productWithSellerDto;
                        })
                                .collect(Collectors.toList()));

        return rootDto;
    }
}
