package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.IngredientConverter;
import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.CompositionRepository;
import com.itrex.kaliaha.repository.IngredientRepository;
import com.itrex.kaliaha.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {
    private final CompositionRepository compositionRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public IngredientDTO findById(Long id) throws ServiceException {
        return ingredientRepository.findById(id).map(IngredientConverter::toDTO)
                .orElseThrow(() -> new ServiceException("Ingredient is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> findAll() throws ServiceException {
        return IngredientConverter.toIngredientListDTO(ingredientRepository.findAll());
    }

    @Transactional
    @Override
    public IngredientDTO add(IngredientDTO ingredientDTO) throws ServiceException {
        Ingredient ingredient = IngredientConverter.fromDTO(ingredientDTO);
        return IngredientConverter.toDTO(ingredientRepository.save(ingredient));
    }

    @Transactional
    @Override
    public IngredientDTO update(IngredientDTO ingredientDTO) throws ServiceException {
        Optional<Ingredient> ingredientToUpdate = ingredientRepository.findById(ingredientDTO.getId());
        if(ingredientToUpdate.isPresent()) {
            Ingredient ingredient = ingredientToUpdate.get();
            if(ingredientDTO.getPrice() != 0) {
                ingredient.setPrice(ingredientDTO.getPrice());
            }
            if(ingredientDTO.getIngredientName() != null) {
                ingredient.setIngredientName(ingredientDTO.getIngredientName());
            }
            if(ingredientDTO.getMeasurement() != null) {
                ingredient.setMeasurement(ingredientDTO.getMeasurement());
            }
            if(ingredientDTO.getRemainder() != 0) {
                ingredient.setRemainder(ingredientDTO.getRemainder());
            }
            ingredientRepository.flush();
            return ingredientDTO;
        } else {
            throw new ServiceException("Ingredient is not updated");
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Optional<Ingredient> ingredientToDelete = ingredientRepository.findById(id);
        if(ingredientToDelete.isPresent()) {
            Ingredient ingredient = ingredientToDelete.get();
            compositionRepository.deleteAll(ingredient.getCompositions());
            ingredientRepository.delete(ingredient);
            return true;
        } else {
            throw new ServiceException("Ingredient is not deleted");
        }
    }
}