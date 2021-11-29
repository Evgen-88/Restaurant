package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition, Long> {
    List<Composition> findAllByDishId(Long dishId);

    @Modifying
    @Query("delete from Composition c where c.dish.id=:dishId and c.ingredient.id=:ingredientId")
    void deleteCompositionByDishIdAndIngredientId(Long dishId, Long ingredientId);
}