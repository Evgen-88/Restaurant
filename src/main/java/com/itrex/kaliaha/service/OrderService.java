package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderUserDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface OrderService {
    OrderUserDTO findById(Long orderId);
    List<OrderListDTO>findAll();
    void add(OrderUserDTO orderDTO) throws ServiceException;
    void update(OrderUserDTO orderDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
}