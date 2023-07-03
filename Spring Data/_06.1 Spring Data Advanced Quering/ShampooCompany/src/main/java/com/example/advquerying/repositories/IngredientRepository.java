package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameIsStartingWith(String letter);

    List<Ingredient> findByNameIsInOrderByPrice(Set<String> inputIngredients);

    boolean existsIngredientByName(String name);

    void deleteByName(String name);


    @Query("UPDATE Ingredient i SET i.price = i.price * 1.10")
    @Modifying
    void updateAllPrice();

    @Query("UPDATE Ingredient i SET i.price = i.price * 1.10 WHERE i.name =:name")
    @Modifying
    void updatePriceByName(String name);
}
