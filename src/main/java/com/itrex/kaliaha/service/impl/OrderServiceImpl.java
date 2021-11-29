package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.DishConverter;
import com.itrex.kaliaha.converters.OrderConverter;
import com.itrex.kaliaha.dto.DishListDTO;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderSaveOrUpdateDTO;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderDTO findById(Long orderId) throws ServiceException {
        return orderRepository.findById(orderId).map(OrderConverter::toDTO)
                .orElseThrow(() -> new ServiceException("Order is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderListDTO> findAll() throws ServiceException {
        return OrderConverter.toOrderListDTO(orderRepository.findAll());
    }

    @Override
    @Transactional
    public OrderSaveOrUpdateDTO add(OrderSaveOrUpdateDTO saveOrUpdateDTO) throws ServiceException {
        Order order = OrderConverter.toSaveOrUpdateDTO(saveOrUpdateDTO);
        return OrderConverter.toSaveOrUpdateDTO(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderSaveOrUpdateDTO update(OrderSaveOrUpdateDTO saveOrUpdateDTO) throws ServiceException {
        Optional<Order> orderToUpdate = orderRepository.findById(saveOrUpdateDTO.getOrderId());
        if(orderToUpdate.isPresent()) {
            Order order = orderToUpdate.get();
            if(saveOrUpdateDTO.getPrice() != 0) {
                order.setPrice(saveOrUpdateDTO.getPrice());
            }
            if(saveOrUpdateDTO.getDate() != null) {
                order.setDate(saveOrUpdateDTO.getDate().toLocalDate());
            }
            if(saveOrUpdateDTO.getAddress() != null) {
                order.setAddress(saveOrUpdateDTO.getAddress());
            }
            if(saveOrUpdateDTO.getOrderStatus() != null) {
                order.setOrderStatus(saveOrUpdateDTO.getOrderStatus());
            }
            orderRepository.flush();
            return saveOrUpdateDTO;
        } else {
            throw new ServiceException("Order is not updated");
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws ServiceException {
        Optional<Order> orderToDelete = orderRepository.findById(id);
        if(orderToDelete.isPresent()) {
            Order order = orderToDelete.get();
            orderRepository.delete(order);
            return true;
        } else {
            throw new ServiceException("Order is not deleted");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DishListDTO> getDishesByOrderId(Long orderId) {
        return DishConverter.toDishListDTO(dishRepository.findAllByOrderId(orderId));
    }

    @Override
    @Transactional
    public boolean addDishToOrder(Long orderId, Long dishId) throws ServiceException {
        Optional<Order> orderToAddDish = orderRepository.findById(orderId);
        if (orderToAddDish.isPresent()) {
            Order order = orderToAddDish.get();
            order.getDishes().add(Dish.builder().id(dishId).build());
            orderRepository.flush();
            return true;
        } else {
            throw new ServiceException("Dish is not added to order");
        }
    }

    @Override
    @Transactional
    public boolean deleteDishFromOrder(Long orderId, Long dishId) throws ServiceException {
        Optional<Order> orderToRemoveDish = orderRepository.findById(orderId);
        if (orderToRemoveDish.isPresent()) {
            Order order = orderToRemoveDish.get();
            List<Dish> dishesAfterRemove = removeOneDish(order.getDishes(), dishId);
            order.setDishes(dishesAfterRemove);
            orderRepository.flush();
            return true;
        } else {
            throw new ServiceException("Dish is not deleted from order");
        }
    }

    private List<Dish> removeOneDish(List<Dish> dishes, Long dishId) {
        Iterator<Dish> iterator = dishes.iterator();
        while (iterator.hasNext()) {
            if(Objects.equals(iterator.next().getId(), dishId)) {
                iterator.remove();
                break;
            }
        }
        return new ArrayList<>(dishes);
    }
}