package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.DishConverter;
import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
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
    public DishDTO findById(Long id) throws ServiceException {
        try {
            return DishConverter.toDTO(dishRepository.findById(id));
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public List<DishListDTO> findAll() throws ServiceException {
        try {
            return dishRepository.findAll().stream().map(DishConverter::toDishListDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public DishSaveOrUpdateDTO add(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException {
        try {
            Dish dish = DishConverter.fromDTO(dishSaveOrUpdateDTO);
            List<Composition> compositions = getCompositionList(dish, dishSaveOrUpdateDTO.getIngredients());
            dish = dishRepository.addWithCompositions(dish, compositions);
            dishSaveOrUpdateDTO.setId(dish.getId());
            return dishSaveOrUpdateDTO;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
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
        try {
            Dish dish = DishConverter.fromDTO(dishSaveOrUpdateDTO);
            return DishConverter.saveOrUpdateDTO(dishRepository.update(dish));
        }  catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return dishRepository.delete(id);
        }  catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }
}