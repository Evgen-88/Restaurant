package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.DishConverter;
import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.CompositionRepository;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.service.DishService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final CompositionRepository compositionRepository;

    public DishServiceImpl(DishRepository dishRepository, CompositionRepository compositionRepository) {
        this.dishRepository = dishRepository;
        this.compositionRepository = compositionRepository;
    }

    public DishDTO findById(Long id) {
        DishDTO dishDTO = DishConverter.toDTO(dishRepository.findById(id));
        dishDTO.setIngredientList(getIngredientList(id));
        return dishDTO;
    }

    private List<DishIngredientDTO> getIngredientList(Long dishId) {
        List<Composition> compositions = dishRepository.getCompositionsByDishId(dishId);
        List<Ingredient> ingredients = compositions.stream()
                .map(composition -> compositionRepository.getIngredientByCompositionId(composition.getId()))
                .collect(Collectors.toList());
        return DishConverter.toDishIngredientListDTO(ingredients, compositions);
    }

    public List<DishListDTO> findAll() {
        return dishRepository.findAll().stream().map(DishConverter::toDishListDTO).collect(Collectors.toList());
    }

    public void add(DishSaveDTO dishSaveDTO) {
        Dish dish = DishConverter.fromDTO(dishSaveDTO);
        dishRepository.add(dish);
        dishSaveDTO.setId(dish.getId());
        compositionRepository.addAll(collectCompositions(dish, dishSaveDTO.getIngredients()));
    }

    private List<Composition> collectCompositions(Dish dish, Map<Long, Integer> ingredients) {
        List<Composition> compositions = new ArrayList<>();
        for (Long ingredientId : ingredients.keySet()) {
            Ingredient ingredient = new Ingredient(ingredientId);
            int quantity = ingredients.get(ingredientId);
            compositions.add(new Composition(dish, ingredient, quantity));
        }
        return compositions;
    }

    public void update(DishUpdateDTO dishUpdateDTO) throws ServiceException {
        Dish dish = DishConverter.fromDTO(dishUpdateDTO);

        if(!dishRepository.update(dish)) {
            throw new ServiceException("Dish object is not updated to database", dishUpdateDTO);
        }
    }

    public void delete(Long id) throws InvalidIdParameterServiceException {
        if(id == null) {
            throw new InvalidIdParameterServiceException("id parameter shouldn't be null");
        }
        if(!dishRepository.delete(id)) {
            throw new InvalidIdParameterServiceException("Dish wasn't deleted", id);
        }
    }
}