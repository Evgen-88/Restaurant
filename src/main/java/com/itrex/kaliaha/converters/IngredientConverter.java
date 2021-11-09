package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.IngredientSaveDTO;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IngredientConverter {
    public static Ingredient fromDTO(IngredientSaveDTO ingredientSaveDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(ingredientSaveDTO.getIngredientName());
        ingredient.setRemainder(ingredient.getRemainder());
        ingredient.setPrice(ingredient.getPrice());
        ingredient.setMeasurement(ingredientSaveDTO.getMeasurement());
        return ingredient;
    }


    public static IngredientSaveDTO toDTO(Ingredient ingredient) {
        return IngredientSaveDTO.builder()
                .id(ingredient.getId())
                .ingredientName(ingredient.getIngredientName())
                .measurement(ingredient.getMeasurement())
                .price(ingredient.getPrice())
                .remainder(ingredient.getRemainder())
                .build();
    }

    public static List<IngredientSaveDTO> toIngredientListDTO(List<Ingredient> ingredients) {
        return ingredients.stream().map(IngredientConverter::toDTO).collect(Collectors.toList());
    }

    public static Map<Long, Integer> toMapIngredient(List<Composition> compositions) {
        Map<Long, Integer> ingredientMap = new HashMap<>();
        for (Composition composition : compositions) {
            ingredientMap.put(composition.getIngredient().getId(), composition.getQuantity());
        }
        return ingredientMap;
    }
}
