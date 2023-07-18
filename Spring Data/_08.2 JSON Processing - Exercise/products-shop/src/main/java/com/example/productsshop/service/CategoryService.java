package com.example.productsshop.service;

import com.example.productsshop.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> findRandomCategories();
}
