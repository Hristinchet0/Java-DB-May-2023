package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.sevices.IngredientService;
import com.example.advquerying.sevices.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Component
public class Runner implements CommandLineRunner {

    private final ShampooService shampooService;

    private final IngredientService ingredientService;

    public Runner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }


    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choice a task:");
        System.out.printf("1 -> 1.\tSelect Shampoos by Size%n2 -> 2.\tSelect Shampoos by Size or Label%n");
        System.out.printf("3 -> 3.\tSelect Shampoos by Price%n4 -> 4.\tSelect Ingredients by Name%n");
        System.out.printf("5 -> 5.\tSelect Ingredients by Names%n6 -> 6.\tCount Shampoos by Price%n");
        System.out.printf("7 -> 7.\tSelect Shampoos by Ingredients%n8 -> 8.\tSelect Shampoos by Ingredients Count%n");
        System.out.printf("9 -> 9.\tDelete Ingredients by Name%n10 -> 10.\tUpdate Ingredients by Price%n");
        System.out.printf("11 -> 11.\tUpdate Ingredients by Names%n");

        int taskNumber = Integer.parseInt(scanner.nextLine());

        switch (taskNumber) {
            case 1:
                p01selectShampoosBySize(scanner);
                break;
            case 2:
                p02selectShampoosBySizeOrLabel(scanner);
                break;
            case 3:
                p03selectShampooByPrice(scanner);
                break;
            case 4:
                p04SelectIngredientsByName(scanner);
                break;
            case 5:
                p05SelectIngredientsByName(scanner);
                break;
            case 6:
                p06CountShampooByPrice(scanner);
                break;
            case 7:
                p07SelectShampoosByIngredient(scanner);
                break;
            case 8:
                p08SelectShampoosByIngredientsCount(scanner);
                break;
            case 9:
                p09DeleteIngredientByName(scanner);
                break;
            case 10:
                p10UpdateIngredientsByPrice(scanner);
                break;
            case 11:
                p11UpdateIngredientByNames(scanner);
                break;
        }

    }

    private void p11UpdateIngredientByNames(Scanner scanner) {
        System.out.println("Input Ingredient Name: ");
        String name = scanner.nextLine();

        this.ingredientService.updateIngredientByName(name);
    }

    private void p10UpdateIngredientsByPrice(Scanner scanner) {
        this.ingredientService.updateIngredientsPrice();
    }

    private void p09DeleteIngredientByName(Scanner scanner) {
        System.out.println("Input Ingredient Name: ");
        String ingredientName = scanner.nextLine();

        this.ingredientService.deleteIngredient(ingredientName);
    }

    private void p08SelectShampoosByIngredientsCount(Scanner scanner) {
        System.out.println("Input Ingredients count: ");

        int count = Integer.parseInt(scanner.nextLine());

        this.shampooService.findShampoosWithIngredientsLessThan(count)
                .forEach(System.out::println);
    }

    private void p07SelectShampoosByIngredient(Scanner scanner) {
        System.out.println("Input Ingredients: ");
        System.out.println("Write End to finish.");
        Set<String> ingredients = new HashSet<>();
        String row = scanner.nextLine();
        while (!row.equals("End")) {
            ingredients.add(row);
            row = scanner.nextLine();
        }

        this.shampooService.findShampooByIngredients(ingredients)
                .forEach(System.out::println);
    }

    private void p06CountShampooByPrice(Scanner scanner) {
        System.out.println("Input price:");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        System.out.println(this.shampooService.findCountOfShampooByGivenPrice(price));
    }

    private void p05SelectIngredientsByName(Scanner scanner) {
        System.out.println("Input Ingredients: ");
        System.out.println("Write End to finish.");
        Set<String> ingredients = new HashSet<>();
        String row = scanner.nextLine();
        while (!row.equals("End")) {
            ingredients.add(row);
            row = scanner.nextLine();
        }

        this.ingredientService.findIngredientNameByGivenListOfNames(ingredients)
                .forEach(System.out::println);
    }

    private void p04SelectIngredientsByName(Scanner scanner) {
        System.out.println("input first letter:");
        String firstLetter = scanner.nextLine();

        this.ingredientService.findIngredientNameByGivenLetter(firstLetter)
                .forEach(System.out::println);
    }

    private void p03selectShampooByPrice(Scanner scanner) {
        System.out.println("Input price:");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        this.shampooService.findShampooByGivenPrice(price)
                .forEach(System.out::println);
    }

    private void p02selectShampoosBySizeOrLabel(Scanner scanner) {
        System.out.println("Choice size: SMALL, MEDIUM, LARGE");
        String sizeName = scanner.nextLine();
        Size size = Size.valueOf(sizeName);

        System.out.println("Choice label ID:");
        Long labelId = Long.valueOf(scanner.nextLine());

        this.shampooService.findShampooByGivenSizeOrLabelOrderByPrice(size, labelId)
                .forEach(System.out::println);

    }

    private void p01selectShampoosBySize(Scanner scanner) {
        System.out.println("Choice size: SMALL, MEDIUM, LARGE");
        String sizeName = scanner.nextLine();
        Size size = Size.valueOf(sizeName);

        this.shampooService.findShampooByGivenSizeOrderById(size)
                .forEach(System.out::println);

    }
}
