package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Dish;

import java.util.List;

public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findAllDishesInOrderById(Long orderId);
}