package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.DishListDTO;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderSaveOrUpdateDTO;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface OrderService {
    OrderDTO findById(Long orderId) throws ServiceException;
    List<OrderListDTO>findAll() throws ServiceException;
    OrderSaveOrUpdateDTO add(OrderSaveOrUpdateDTO saveOrUpdateDTO) throws ServiceException;
    OrderSaveOrUpdateDTO update(OrderSaveOrUpdateDTO saveOrUpdateDTO) throws ServiceException;
    boolean delete(Long id) throws ServiceException;

    List<DishListDTO> getDishesByOrderId(Long orderId) throws ServiceException;
    boolean addDishToOrder(Long orderId, Long dishId) throws ServiceException;
    boolean deleteDishFromOrder(Long orderId, Long dishId) throws ServiceException;
}