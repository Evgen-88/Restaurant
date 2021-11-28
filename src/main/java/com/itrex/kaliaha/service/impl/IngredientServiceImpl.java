package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.IngredientConverter;
import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.deprecated.IngredientRepository;
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
    public IngredientDTO findById(Long id) throws ServiceException {
        try {
            return IngredientConverter.toDTO(ingredientRepository.findById(id));
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public List<IngredientDTO> findAll() throws ServiceException {
        try {
            return IngredientConverter.toIngredientListDTO(ingredientRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public IngredientDTO add(IngredientDTO ingredientDTO) throws ServiceException {
        try {
            Ingredient ingredient = IngredientConverter.fromDTO(ingredientDTO);
            ingredient = ingredientRepository.add(ingredient);
            return IngredientConverter.toDTO(ingredient);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public IngredientDTO update(IngredientDTO ingredientDTO) throws ServiceException {
        try {
            Ingredient ingredient = IngredientConverter.fromDTO(ingredientDTO);
            return IngredientConverter.toDTO(ingredientRepository.update(ingredient));
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return ingredientRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }
}