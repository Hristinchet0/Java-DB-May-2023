package com.example.advquerying.sevices;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService{


    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }


    @Override
    public List<String> findShampooByGivenSizeOrderById(Size size) {
        List<Shampoo> result = this.shampooRepository.findBySizeOrderByIdAsc(size);
        return resultToString(result);
    }

    @Override
    public List<String> findShampooByGivenSizeOrLabelOrderByPrice(Size size, Long labelId) {
        List<Shampoo> result = shampooRepository.findBySizeOrLabel_IdOrderByPriceAsc(size, labelId);
        return resultToString(result);
    }

    @Override
    public List<String> findShampooByGivenPrice(BigDecimal price) {
        List<Shampoo> result = shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
        return resultToString(result);
    }

    @Override
    public int findCountOfShampooByGivenPrice(BigDecimal price) {
        return this.shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public List<String> findShampooByIngredients(Set<String> ingredients) {
        return this.shampooRepository.findShampoosWithIngredients(ingredients)
                .stream()
                .map(Shampoo::getBrand)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findShampoosWithIngredientsLessThan(int count) {
        return this.shampooRepository.findShampoosWithIngredientsLessThank(count)
                .stream()
                .map(Shampoo::getBrand)
                .distinct()
                .collect(Collectors.toList());
    }


    private List<String> resultToString(List<Shampoo> result) {
        return result
                .stream()
                .map(shampoo -> String.format("%s %s %.2flv.",
                        shampoo.getBrand(),
                        shampoo.getSize(),
                        shampoo.getPrice()))
                .collect(Collectors.toList());
    }
}
