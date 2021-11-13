package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    List<Order> findOrdersByUserId(Long userId);
    boolean bookDish(Long orderId, Long dishId);
    boolean deleteFromOrderDishById(Long orderId, Long dishId);
}