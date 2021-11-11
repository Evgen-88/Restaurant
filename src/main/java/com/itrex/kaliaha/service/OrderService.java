package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.exception.DishIsNotOrderedException;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface OrderService {
    OrderDTO findById(Long orderId);
    List<OrderListDTO>findAll();
    OrderListDTO add(OrderListDTO orderDTO) throws ServiceException;
    OrderListDTO update(OrderListDTO orderDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
    boolean orderDish(Long orderId, Long dishId) throws ServiceException, DishIsNotOrderedException;
    boolean deleteFromOrderDishById(Long orderId, Long dishId) throws DishIsNotOrderedException;
}