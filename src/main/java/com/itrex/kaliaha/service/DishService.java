package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface DishService {
    DishDTO findById(Long id) throws ServiceException;
    List<DishListDTO> findAll() throws ServiceException;
    DishSaveOrUpdateDTO add(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException;
    DishSaveOrUpdateDTO update(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException;
    boolean delete(Long id)  throws ServiceException;

    List<DishIngredientDTO> getIngredientsByDishId(Long dishId) throws ServiceException;
    boolean addIngredientToDish(Long dishId, DishIngredientDTO dishIngredientDTO) throws ServiceException;
    boolean deleteIngredientFromDish(Long compositionId, Long ingredientId) throws ServiceException;
}