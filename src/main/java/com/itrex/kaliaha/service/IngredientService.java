package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface IngredientService {
    IngredientDTO findById(Long id) throws ServiceException;
    List<IngredientDTO> findAll() throws ServiceException;
    IngredientDTO add(IngredientDTO ingredientDTO) throws ServiceException;
    IngredientDTO update(IngredientDTO ingredientDTO) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
}