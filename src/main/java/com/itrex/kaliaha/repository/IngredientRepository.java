package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Primary
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> getIngredientByCompositionId(Long compositionId);

    @Query("from Composition c left join fetch c.ingredient i where i.id=:ingredientId")
    List<Composition> findAllCompositionsThatIncludeIngredientById(Long ingredientId);
}