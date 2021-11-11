package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.DishIngredientDTO;
import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

public class IngredientConverter {
    public static IngredientDTO toDTO(Ingredient ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .ingredientName(ingredient.getIngredientName())
                .measurement(ingredient.getMeasurement())
                .price(ingredient.getPrice())
                .remainder(ingredient.getRemainder())
                .build();
    }

    public static List<IngredientDTO> toIngredientListDTO(List<Ingredient> ingredients) {
        return ingredients.stream().map(IngredientConverter::toDTO).collect(Collectors.toList());
    }

    public static Ingredient fromDTO(IngredientDTO ingredientDTO) {
        return Ingredient.builder()
                .ingredientName(ingredientDTO.getIngredientName())
                .remainder(ingredientDTO.getRemainder())
                .price(ingredientDTO.getPrice())
                .measurement(ingredientDTO.getMeasurement())
                .build();
    }

    public static DishIngredientDTO toDishIngredientDTO(Ingredient ingredient, Composition composition) {
        return DishIngredientDTO.builder()
                .ingredientId(ingredient.getId())
                .ingredientName(ingredient.getIngredientName())
                .measurement(ingredient.getMeasurement())
                .compositionId(composition.getId())
                .quantity(composition.getQuantity())
                .build();
    }

    public static List<DishIngredientDTO> toDishIngredientListDTO(List<Composition> compositions) {
        return compositions.stream()
                .map(composition -> {
                    DishIngredientDTO dishIngredientDTO = IngredientConverter.toDishIngredientDTO(composition.getIngredient(), composition);
                    dishIngredientDTO.setPrice(calculatePriceIngredient(composition.getIngredient(), composition));
                    return dishIngredientDTO;
                })
                .collect(Collectors.toList());
    }

    private static int calculatePriceIngredient(Ingredient ingredient, Composition composition) {
        int partMeasurementPrice = ingredient.getPrice() * composition.getQuantity();
        return partMeasurementPrice / ingredient.getMeasurement().getValue();
    }
}