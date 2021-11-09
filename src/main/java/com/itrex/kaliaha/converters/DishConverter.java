package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DishConverter {
    public static DishDTO toDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .dishGroup(dish.getDishGroup())
                .dishDescription(dish.getDishDescription())
                .imagePath(dish.getImagePath())
                .build();
    }

    public static List<DishIngredientDTO> toDishIngredientListDTO(List<Ingredient> ingredients, List<Composition> compositions) {
        List<DishIngredientDTO> dishIngredientDTOList = new ArrayList<>();
        for (int index = 0; index < compositions.size(); index++) {
            Ingredient ingredient = ingredients.get(index);
            Composition composition = compositions.get(index);
            DishIngredientDTO dishIngredientDTO = toDTO(ingredient, composition);
            dishIngredientDTO.setPrice(calculatePriceIngredient(ingredient, composition));
            dishIngredientDTOList.add(dishIngredientDTO);
        }
        return dishIngredientDTOList;
    }

    private static int calculatePriceIngredient(Ingredient ingredient, Composition composition) {
        int partMeasurementPrice = ingredient.getPrice() * composition.getQuantity();
        return partMeasurementPrice / ingredient.getMeasurement().getValue();
    }

    public static DishIngredientDTO toDTO(Ingredient ingredient, Composition composition) {
        return DishIngredientDTO.builder()
                .ingredientId(ingredient.getId())
                .ingredientName(ingredient.getIngredientName())
                .measurement(ingredient.getMeasurement())
                .compositionId(composition.getId())
                .quantity(composition.getQuantity())
                .build();
    }

    public static DishListDTO toDishListDTO(Dish dish) {
        return DishListDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .build();
    }

    public static DishSaveDTO toSaveDTO(Dish dish) {
        return DishSaveDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .dishGroup(dish.getDishGroup())
                .dishDescription(dish.getDishDescription())
                .imagePath(dish.getImagePath())
                .build();
    }

    public static List<DishListDTO> toDishListDTO(List<Dish> dishes) {
        return dishes.stream()
                .map(DishConverter::toDishListDTO)
                .collect(Collectors.toList());
    }

    public static Dish fromDTO(DishSaveDTO dishSaveDTO) {
        Dish dish = new Dish();
        dish.setId(dishSaveDTO.getId());
        dish.setDishName(dishSaveDTO.getDishName());
        dish.setPrice(dishSaveDTO.getPrice());
        dish.setDishDescription(dishSaveDTO.getDishDescription());
        dish.setDishGroup(dishSaveDTO.getDishGroup());
        dish.setImagePath(dishSaveDTO.getImagePath());
        return dish;
    }

    public static DishUpdateDTO toUpdateDTO(Dish dish) {
        return DishUpdateDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .dishGroup(dish.getDishGroup())
                .dishDescription(dish.getDishDescription())
                .imagePath(dish.getImagePath())
                .build();
    }

    public static Dish fromDTO(DishUpdateDTO dishUpdateDTO) {
        Dish dish = new Dish();
        dish.setDishName(dishUpdateDTO.getDishName());
        dish.setPrice(dishUpdateDTO.getPrice());
        dish.setDishGroup(dishUpdateDTO.getDishGroup());
        dish.setDishDescription(dishUpdateDTO.getDishDescription());
        dish.setImagePath(dishUpdateDTO.getImagePath());
        dish.setId(dishUpdateDTO.getId());
        return dish;
    }
}