package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    List<Order> findOrdersByUserId(Long userId);
    boolean orderDish(Order order, Long dishId);
    boolean deleteDishesByOrderId(Long orderId);
}