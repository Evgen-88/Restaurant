package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.IngredientSaveDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface IngredientService {
    IngredientSaveDTO findById(Long id);
    List<IngredientSaveDTO> findAll();
    void add(IngredientSaveDTO ingredientSaveDTO) throws ServiceException;
    void update(IngredientSaveDTO ingredientSaveDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
}
