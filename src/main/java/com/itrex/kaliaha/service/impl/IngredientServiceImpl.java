package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.IngredientSaveDTO;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.IngredientRepository;
import com.itrex.kaliaha.service.IngredientService;
import com.itrex.kaliaha.util.DTOParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public IngredientSaveDTO findById(Long id) {
        return DTOParser.toDTO(ingredientRepository.findById(id));
    }

    public List<IngredientSaveDTO> findAll() {
        return DTOParser.toIngredientListDTO(ingredientRepository.findAll());
    }

    public void add(IngredientSaveDTO ingredientSaveDTO) throws ServiceException {
        Ingredient ingredient = DTOParser.fromDTO(ingredientSaveDTO);

        if(ingredientRepository.add(ingredient)) {
            ingredientSaveDTO.setId(ingredient.getId());
        } else {
            throw new ServiceException("Ingredient object is not added to database", ingredientSaveDTO);
        }
    }

    public void update(IngredientSaveDTO ingredientSaveDTO) throws ServiceException {
        Ingredient ingredient = DTOParser.fromDTO(ingredientSaveDTO);

        if(!ingredientRepository.update(ingredient)) {
            throw new ServiceException("Ingredient object is not updated in database", ingredientSaveDTO);
        }
    }

    public void delete(Long id) throws InvalidIdParameterServiceException {
        if(id == null) {
            throw new InvalidIdParameterServiceException("id parameter shouldn't be null");
        }
        if(!ingredientRepository.delete(id)) {
            throw new InvalidIdParameterServiceException("Ingredient wasn't deleted", id);
        }
    }
}