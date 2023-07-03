package com.example.advquerying.sevices;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findIngredientNameByGivenLetter(String letter) {
        return this.ingredientRepository.findByNameIsStartingWith(letter);
    }

    @Override
    public List<Ingredient> findIngredientNameByGivenListOfNames(Set<String> givenNames) {
        return this.ingredientRepository.findByNameIsInOrderByPrice(givenNames);
    }

    @Override
    public void deleteIngredient(String name) {

        if (!ingredientRepository.existsIngredientByName(name)) {
            System.out.println("No such ingredient");
            return;
        }

        ingredientRepository.deleteByName(name);
        System.out.println("Successfully deleted ingredient " + name);
    }

    @Override
    public void updateIngredientsPrice() {

        ingredientRepository.updateAllPrice();
    }

    @Override
    public void updateIngredientByName(String name) {

        if (!ingredientRepository.existsIngredientByName(name)) {
            System.out.println("No such ingredient");
            return;
        }

        ingredientRepository.updatePriceByName(name);
        System.out.println("Successfully updated the price of ingredient" + name);
    }


}
