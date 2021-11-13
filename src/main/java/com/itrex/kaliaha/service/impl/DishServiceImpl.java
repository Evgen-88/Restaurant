package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.DishConverter;
import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public DishDTO findById(Long id) {
        return DishConverter.toDTO(dishRepository.findById(id));
    }

    @Override
    public List<DishListDTO> findAll() {
        return dishRepository.findAll().stream().map(DishConverter::toDishListDTO).collect(Collectors.toList());
    }

    @Override
    public DishSaveOrUpdateDTO add(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) {
        Dish dish = DishConverter.fromDTO(dishSaveOrUpdateDTO);
        List<Composition> compositions = getCompositionList(dish, dishSaveOrUpdateDTO.getIngredients());
        dish = dishRepository.addWithCompositions(dish, compositions);
        dishSaveOrUpdateDTO.setId(dish.getId());
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

    @Override
    public DishSaveOrUpdateDTO update(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException {
        Dish dish = DishConverter.fromDTO(dishSaveOrUpdateDTO);
        return DishConverter.saveOrUpdateDTO(dishRepository.update(dish));
    }

    @Override
    public void delete(Long id) throws InvalidIdParameterServiceException {
        if (!dishRepository.delete(id)) {
            throw new InvalidIdParameterServiceException("Dish wasn't deleted", id);
        }
    }
}