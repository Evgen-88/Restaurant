package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.OrderDishDTO;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface OrderService {
    OrderDTO findById(Long orderId);
    List<OrderListDTO>findAll();
    void add(OrderDTO orderDTO) throws ServiceException;
    void update(OrderDTO orderDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
    boolean order(OrderDishDTO orderDishDTO) throws ServiceException;
    boolean deleteFromOrderDishById(OrderDishDTO orderDishDTO) throws ServiceException;
}