package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

public class DishConverter {
    public static DishDTO fromDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .dishGroup(dish.getDishGroup())
                .dishDescription(dish.getDishDescription())
                .imagePath(dish.getImagePath())
                .ingredientList(IngredientConverter.toDishIngredientListDTO(dish.getCompositions()))
                .build();
    }

    public static DishListDTO toDishListDTO(Dish dish) {
        return DishListDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .imagePath(dish.getImagePath())
                .build();
    }

    public static List<DishListDTO> toDishListDTO(List<Dish> dishes) {
        return dishes.stream()
                .map(DishConverter::toDishListDTO)
                .collect(Collectors.toList());
    }

    public static Dish fromDTO(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) {
        return Dish.builder()
                .id(dishSaveOrUpdateDTO.getId())
                .dishName(dishSaveOrUpdateDTO.getDishName())
                .price(dishSaveOrUpdateDTO.getPrice())
                .dishGroup(dishSaveOrUpdateDTO.getDishGroup())
                .dishDescription(dishSaveOrUpdateDTO.getDishDescription())
                .imagePath(dishSaveOrUpdateDTO.getImagePath())
                .build();
    }

    public static Composition fromDTO(Long dishId, DishIngredientDTO dishIngredientDTO) {
        return Composition.builder()
                .dish(Dish.builder().id(dishId).build())
                .ingredient(Ingredient.builder().id(dishIngredientDTO.getIngredientId()).build())
                .quantity(dishIngredientDTO.getQuantity())
                .build();
    }


}