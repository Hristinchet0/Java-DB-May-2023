package com.example.productsshop.service;

import com.example.productsshop.model.DTO.categoriesDtos.CategoriesViewProductsDto;
import com.example.productsshop.model.DTO.categoriesDtos.CategoryProductsDto;
import com.example.productsshop.model.DTO.categoriesDtos.CategorySeedDto;
import com.example.productsshop.model.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories(List<CategorySeedDto> categories);

    long getEntityCount();

    Set<Category> getRandomCategories();

    List<CategoryProductsDto> categoryProductsCount();

    CategoriesViewProductsDto categoriesByProductsCount();
}
