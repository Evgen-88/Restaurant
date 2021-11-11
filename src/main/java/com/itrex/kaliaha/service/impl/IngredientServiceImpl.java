package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.IngredientConverter;
import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.IngredientRepository;
import com.itrex.kaliaha.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientDTO findById(Long id) {
        return IngredientConverter.toDTO(ingredientRepository.findById(id));
    }

    @Override
    public List<IngredientDTO> findAll() {
        return IngredientConverter.toIngredientListDTO(ingredientRepository.findAll());
    }

    @Override
    public IngredientDTO add(IngredientDTO ingredientDTO) throws ServiceException {
        Ingredient ingredient = IngredientConverter.fromDTO(ingredientDTO);
        ingredient = ingredientRepository.add(ingredient);
        return IngredientConverter.toDTO(ingredient);
    }

    @Override
    public IngredientDTO update(IngredientDTO ingredientDTO) throws ServiceException {
        Ingredient ingredient = IngredientConverter.fromDTO(ingredientDTO);
        return IngredientConverter.toDTO(ingredientRepository.update(ingredient));
    }

    @Override
    public void delete(Long id) throws InvalidIdParameterServiceException {
        if(!ingredientRepository.delete(id)) {
            throw new InvalidIdParameterServiceException("Ingredient wasn't deleted", id);
        }
    }
}