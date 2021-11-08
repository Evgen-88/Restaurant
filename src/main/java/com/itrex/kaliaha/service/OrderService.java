package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderWithDishesDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface OrderService {
    OrderWithDishesDTO findById(Long orderId);
    List<OrderDTO> findAll();
    void add(OrderDTO orderDTO) throws ServiceException;
    void update(OrderDTO orderDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
}