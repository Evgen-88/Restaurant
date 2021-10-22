package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Dish;

import java.util.List;

public interface DishRepository {
    Dish selectById(Long id);
    List<Dish> selectAll();
    void add(Dish dish);
    void addAll(List<Dish> dishes);
    boolean update(Dish dish);
    boolean remove(Long id);

    List<Dish> findAllDishesInOrderById(Long orderId);
}
