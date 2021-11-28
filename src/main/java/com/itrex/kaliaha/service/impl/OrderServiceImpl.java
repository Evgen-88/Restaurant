package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.OrderConverter;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderSaveOrUpdateDTO;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.deprecated.OrderRepository;
import com.itrex.kaliaha.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO findById(Long orderId) throws ServiceException {
        try {
            return OrderConverter.toDTO(orderRepository.findById(orderId));
        }  catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public List<OrderListDTO> findAll() throws ServiceException {
        try {
            List<Order> orders = orderRepository.findAll();
            return OrderConverter.toOrderListDTO(orders);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public OrderSaveOrUpdateDTO add(OrderSaveOrUpdateDTO saveOrUpdateDTO) throws ServiceException {
        try {
            Order order = OrderConverter.toSaveOrUpdateDTO(saveOrUpdateDTO);
            order = orderRepository.add(order);
            saveOrUpdateDTO.setOrderId(order.getId());
            return saveOrUpdateDTO;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public OrderSaveOrUpdateDTO update(OrderSaveOrUpdateDTO saveOrUpdateDTO) throws ServiceException {
        try {
            Order order = OrderConverter.toSaveOrUpdateDTO(saveOrUpdateDTO);
            return OrderConverter.toSaveOrUpdateDTO(orderRepository.update(order));
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return orderRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public boolean addDishToOrder(Long orderId, Long dishId) throws ServiceException {
        try {
            return orderRepository.addDishToOrder(orderId, dishId);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public boolean deleteDishFromOrder(Long orderId, Long dishId) throws ServiceException {
        try {
            return orderRepository.deleteDishFromOrder(orderId, dishId);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }
}