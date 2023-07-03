package com.example.advquerying.sevices;

import com.example.advquerying.entities.Ingredient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface IngredientService {

    List<Ingredient> findIngredientNameByGivenLetter(String letter);

    List<Ingredient> findIngredientNameByGivenListOfNames(Set<String> givenNames);

    @Transactional
    void deleteIngredient(String ingredientName);

    @Transactional
    void updateIngredientsPrice();

    @Transactional
    void updateIngredientByName(String name);
}
