package com.example.productsshop.service;


import com.example.productsshop.model.DTO.productsDtos.ProductSeedDto;
import com.example.productsshop.model.DTO.productsDtos.ProductViewRootDto;

import java.util.List;

public interface ProductService {

    long getEntityCount();

    void seedProducts(List<ProductSeedDto> products);

    ProductViewRootDto findProductInRangeWithoutBuyer();
}
