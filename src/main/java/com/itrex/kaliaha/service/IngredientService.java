package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface IngredientService {
    IngredientDTO findById(Long id);
    List<IngredientDTO> findAll();
    IngredientDTO add(IngredientDTO ingredientDTO) throws ServiceException;
    IngredientDTO update(IngredientDTO ingredientDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
}