package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Order;

import java.util.List;

public interface OrderRepository extends Repository<Order>{
    List<Order> findOrdersByUserId(Long userId);

    boolean deleteOrdersByUserId(Long userId);

    boolean orderDish(Order order, Long dishId);
    boolean deleteDishesByOrderId(Long orderId);
}
