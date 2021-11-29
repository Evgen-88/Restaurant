package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.DishConverter;
import com.itrex.kaliaha.converters.IngredientConverter;
import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.CompositionRepository;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final CompositionRepository compositionRepository;

    @Override
    public DishDTO findById(Long id) throws ServiceException {
        return dishRepository.findById(id).map(DishConverter::fromDTO)
                .orElseThrow(() -> new ServiceException("Dish is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DishListDTO> findAll() throws ServiceException {
        return DishConverter.toDishListDTO(dishRepository.findAll());
    }

    @Transactional
    @Override
    public DishSaveOrUpdateDTO add(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException {
        Dish savedDish = dishRepository.save(DishConverter.fromDTO(dishSaveOrUpdateDTO));
        List<Composition> compositions = getCompositionList(savedDish, dishSaveOrUpdateDTO.getIngredients());
        compositionRepository.saveAll(compositions);
        dishSaveOrUpdateDTO.setId(savedDish.getId());
        return dishSaveOrUpdateDTO;
    }

    private List<Composition> getCompositionList(Dish dish, Map<Long, Integer> ingredients) {
        return ingredients.keySet().stream()
                .map(ingredientId -> Composition.builder()
                        .dish(dish)
                        .ingredient(Ingredient.builder().id(ingredientId).build())
                        .quantity(ingredients.get(ingredientId))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DishSaveOrUpdateDTO update(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException {
        Optional<Dish> dishToUpdate = dishRepository.findById(dishSaveOrUpdateDTO.getId());
        if(dishToUpdate.isPresent()) {
            Dish dish = dishToUpdate.get();
            if(dishSaveOrUpdateDTO.getDishName() != null) {
                dish.setDishName(dishSaveOrUpdateDTO.getDishName());
            }
            if(dishSaveOrUpdateDTO.getPrice() != 0) {
                dish.setPrice(dishSaveOrUpdateDTO.getPrice());
            }
            if(dishSaveOrUpdateDTO.getDishGroup() != null) {
                dish.setDishGroup(dishSaveOrUpdateDTO.getDishGroup());
            }
            if(dishSaveOrUpdateDTO.getDishDescription() != null) {
                dish.setDishDescription(dishSaveOrUpdateDTO.getDishDescription());
            }
            if(dishSaveOrUpdateDTO.getImagePath() != null) {
                dish.setImagePath(dishSaveOrUpdateDTO.getImagePath());
            }
            dishRepository.flush();
            return dishSaveOrUpdateDTO;
        } else {
            throw new ServiceException("Dish is not updated");
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Optional<Dish> dishToDelete = dishRepository.findById(id);
        if(dishToDelete.isPresent()) {
            Dish dish = dishToDelete.get();
            compositionRepository.deleteAll(dish.getCompositions());
            dishRepository.delete(dish);
            return true;
        } else {
            throw new ServiceException("Dish is not deleted");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DishIngredientDTO> getIngredientsByDishId(Long dishId) {
        return IngredientConverter.toDishIngredientListDTO(compositionRepository.findAllByDishId(dishId));
    }

    @Transactional
    @Override
    public boolean addIngredientToDish(Long dishId, DishIngredientDTO dishIngredientDTO) {
        compositionRepository.save(DishConverter.fromDTO(dishId, dishIngredientDTO));
        return true;
    }

    @Transactional
    @Override
    public boolean deleteIngredientFromDish(Long dishId, Long ingredientId) {
        compositionRepository.deleteCompositionByDishIdAndIngredientId(dishId, ingredientId);
        return true;
    }
}