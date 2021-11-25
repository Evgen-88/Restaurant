package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.DishDTO;
import com.itrex.kaliaha.dto.DishListDTO;
import com.itrex.kaliaha.dto.DishSaveOrUpdateDTO;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface DishService {
    DishDTO findById(Long id) throws ServiceException;
    List<DishListDTO> findAll() throws ServiceException;
    DishSaveOrUpdateDTO add(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException;
    DishSaveOrUpdateDTO update(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException;
    boolean delete(Long id)  throws ServiceException;
}