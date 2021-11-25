package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.exception.RepositoryException;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    List<Order> findOrdersByUserId(Long userId) throws RepositoryException;
    List<Order> findAllOrdersThatIncludeDishByDishId(Long dishId) throws RepositoryException;
    boolean addDishToOrder(Long orderId, Long dishId) throws RepositoryException;
    boolean deleteDishFromOrder(Long orderId, Long dishId) throws RepositoryException;
}