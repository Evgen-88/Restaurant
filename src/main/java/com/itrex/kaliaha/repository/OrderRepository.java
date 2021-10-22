package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Order;

import java.util.List;

public interface OrderRepository {
    Order selectById(Long id);
    List<Order> selectAll();
    void add(Order order);
    void addAll(List<Order> orders);
    boolean update(Order order);
    boolean remove(Long id);

    List<Order> findOrdersByUserId(Long userId);
    boolean removeOrdersByUserId(Long userId);

    boolean orderDish(Order order, Long dishId);
    boolean removeDishesByOrderId(Long orderId);
}
