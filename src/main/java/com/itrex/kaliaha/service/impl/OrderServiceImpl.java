package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.OrderConverter;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderSaveOrUpdateDTO;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.exception.DishIsNotOrderedException;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.OrderRepository;
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
    public OrderDTO findById(Long orderId) {
        return OrderConverter.toDTO(orderRepository.findById(orderId));
    }

    @Override
    public List<OrderListDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return OrderConverter.toOrderListDTO(orders);
    }

    @Override
    public OrderSaveOrUpdateDTO add(OrderSaveOrUpdateDTO saveOrUpdateDTO) throws ServiceException {
        Order order = OrderConverter.toSaveOrUpdateDTO(saveOrUpdateDTO);
        order = orderRepository.add(order);
        saveOrUpdateDTO.setOrderId(order.getId());
        return saveOrUpdateDTO;
    }

    @Override
    public OrderSaveOrUpdateDTO update(OrderSaveOrUpdateDTO saveOrUpdateDTO) throws ServiceException {
        Order order = OrderConverter.toSaveOrUpdateDTO(saveOrUpdateDTO);
        return OrderConverter.toSaveOrUpdateDTO(orderRepository.update(order));
    }

    @Override
    public void delete(Long id) throws InvalidIdParameterServiceException {
        if(!orderRepository.delete(id)) {
            throw new InvalidIdParameterServiceException("Order wasn't deleted", id);
        }
    }

    @Override
    public boolean orderDish(Long orderId, Long dishId) throws DishIsNotOrderedException {
        if(!orderRepository.orderDish(orderId, dishId)) {
            throw new DishIsNotOrderedException("Dish is not ordered", dishId);
        }
        return true;
    }

    @Override
    public boolean deleteFromOrderDishById(Long orderId, Long dishId) throws DishIsNotOrderedException {
        if(!orderRepository.deleteFromOrderDishById(orderId, dishId)) {
            throw new DishIsNotOrderedException("Dish is not deleted from order", dishId);
        }
        return true;
    }
}