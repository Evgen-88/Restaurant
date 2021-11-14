package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    List<Order> findOrdersByUserId(Long userId);
    boolean addDishToOrder(Long orderId, Long dishId);
    boolean deleteDishFromOrder(Long orderId, Long dishId);
}