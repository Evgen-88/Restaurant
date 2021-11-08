package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderUserDTO;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.OrderService;
import com.itrex.kaliaha.util.DTOParser;
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

    public OrderUserDTO findById(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.setUser(userRepository.findById(order.getUser().getId()));

        List<Dish> dishes = dishRepository.findAllDishesInOrderById(orderId);
        return DTOParser.toDTO(order, dishes);
    }

    public List<OrderListDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return DTOParser.toOrderListDTO(orders);
    }

    public void add(OrderUserDTO orderDTO) throws ServiceException {
        Order order = DTOParser.fromDTO(orderDTO);

        if(orderRepository.add(order)) {
            orderDTO.setOrderId(order.getId());
        } else {
            throw new ServiceException("Ingredient object is not added to database", orderDTO);
        }
    }

    public void update(OrderUserDTO orderDTO) throws ServiceException {
        Order order = DTOParser.fromDTO(orderDTO);
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
}