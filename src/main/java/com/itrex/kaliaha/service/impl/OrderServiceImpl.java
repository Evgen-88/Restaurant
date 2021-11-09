package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.OrderConverter;
import com.itrex.kaliaha.dto.OrderDishDTO;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, DishRepository dishRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
    }

    public OrderDTO findById(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.setUser(userRepository.findById(order.getUser().getId()));

        List<Dish> dishes = dishRepository.findAllDishesInOrderById(orderId);
        return OrderConverter.toDTO(order, dishes);
    }

    public List<OrderListDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return OrderConverter.toOrderListDTO(orders);
    }

    public void add(OrderDTO orderDTO) throws ServiceException {
        Order order = OrderConverter.fromDTO(orderDTO);

        if(orderRepository.add(order)) {
            orderDTO.setOrderId(order.getId());
        } else {
            throw new ServiceException("Ingredient object is not added to database", orderDTO);
        }
    }

    public void update(OrderDTO orderDTO) throws ServiceException {
        Order order = OrderConverter.fromDTO(orderDTO);
        if(!orderRepository.update(order)) {
            throw new ServiceException("Order object is not updated in database", orderDTO);
        }
    }

    public void delete(Long id) throws InvalidIdParameterServiceException {
        if(id == null) {
            throw new InvalidIdParameterServiceException("id parameter shouldn't be null");
        }
        if(!orderRepository.delete(id)) {
            throw new InvalidIdParameterServiceException("Order wasn't deleted", id);
        }
    }

    @Override
    public boolean order(OrderDishDTO orderDishDTO) throws ServiceException {
        if(!orderRepository.orderDish(orderDishDTO.getOrderId(), orderDishDTO.getDishId())) {
            throw new ServiceException("Dish is not ordered", orderDishDTO);
        }
        return true;
    }

    @Override
    public boolean deleteFromOrderDishById(OrderDishDTO orderDishDTO) throws ServiceException {
        if(!orderRepository.deleteFromOrderDishById(orderDishDTO.getOrderId(), orderDishDTO.getDishId())) {
            throw new ServiceException("Dish is not ordered", orderDishDTO);
        }
        return true;
    }
}