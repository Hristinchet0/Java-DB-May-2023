package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findBySizeOrderByIdAsc(Size size);

    List<Shampoo> findBySizeOrLabel_IdOrderByPriceAsc(Size size, Long label_id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i where i.name IN :ingredientNames ORDER BY s.id DESC")
    List<Shampoo> findShampoosWithIngredients(@Param("ingredientNames") Set<String> ingredients);


    @Query("SELECT s FROM Shampoo s WHERE SIZE(s.ingredients)<:count")
    List<Shampoo> findShampoosWithIngredientsLessThank(int count);
}
