package com.itrex.kaliaha.repository.deprecated;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.exception.RepositoryException;

import java.util.List;

@Deprecated
public interface IngredientRepository extends BaseRepository<Ingredient>{
    List<Composition> findAllCompositionsThatIncludeIngredientById(Long ingredientId) throws RepositoryException;
    Ingredient getIngredientByCompositionId(Long compositionId) throws RepositoryException;
}