package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.DishDTO;
import com.itrex.kaliaha.dto.DishListDTO;
import com.itrex.kaliaha.dto.DishSaveOrUpdateDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface DishService {
    DishDTO findById(Long id);
    List<DishListDTO> findAll();
    DishSaveOrUpdateDTO add(DishSaveOrUpdateDTO dishSaveOrUpdateDTO);
    DishSaveOrUpdateDTO update(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
}