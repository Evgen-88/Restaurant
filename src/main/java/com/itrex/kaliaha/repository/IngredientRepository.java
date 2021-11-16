package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;

import java.util.List;

public interface IngredientRepository extends BaseRepository<Ingredient>{
    List<Composition> findAllCompositionsThatIncludeIngredientById(Long ingredientId);
    Ingredient getIngredientByCompositionId(Long compositionId);
}