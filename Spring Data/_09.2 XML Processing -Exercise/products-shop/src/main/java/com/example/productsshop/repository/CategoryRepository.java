package com.example.productsshop.repository;

import com.example.productsshop.model.DTO.categoriesDtos.CategoryProductsDto;
import com.example.productsshop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT new com.example.productsshop.model.DTO.categoriesDtos.CategoryProductsDto" +
            "(c.name, count(p.id), avg(p.price), sum(p.price)) " +
            "FROM Product p " +
            "JOIN p.categories c " +
            "GROUP BY c.id " +
            "ORDER BY count(p.id) DESC")
    Optional<List<CategoryProductsDto>> getCategoryView();
}
