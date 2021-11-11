package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Dish;

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

    public static DishSaveOrUpdateDTO saveOrUpdateDTO(Dish dish) {
        return DishSaveOrUpdateDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .dishGroup(dish.getDishGroup())
                .dishDescription(dish.getDishDescription())
                .imagePath(dish.getImagePath())
                .build();
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
}