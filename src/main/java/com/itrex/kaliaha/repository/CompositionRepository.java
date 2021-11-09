package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;

import java.util.List;

public interface CompositionRepository extends BaseRepository<Composition>{
    Ingredient getIngredientByCompositionId(Long compositionId);
    Dish getDishByCompositionId(Long compositionId);
    boolean addAll(List<Composition> compositions);
}