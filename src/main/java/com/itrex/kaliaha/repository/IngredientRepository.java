package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Ingredient;

import java.util.List;

public interface IngredientRepository {
    Ingredient selectById(Long id);
    List<Ingredient> selectAll();
    void add(Ingredient ingredient);
    void addAll(List<Ingredient> ingredients);
    boolean update(Ingredient ingredient);
    boolean remove(Long id);
}