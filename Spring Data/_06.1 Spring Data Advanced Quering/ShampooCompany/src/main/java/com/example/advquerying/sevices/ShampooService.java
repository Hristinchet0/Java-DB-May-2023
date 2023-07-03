package com.example.advquerying.sevices;

import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {

    List<String> findShampooByGivenSizeOrderById(Size size);

    List<String> findShampooByGivenSizeOrLabelOrderByPrice(Size size, Long labelId);

    List<String> findShampooByGivenPrice(BigDecimal price);

    int findCountOfShampooByGivenPrice(BigDecimal price);

    List<String> findShampooByIngredients(Set<String> ingredients);

    List<String> findShampoosWithIngredientsLessThan(int count);
}
