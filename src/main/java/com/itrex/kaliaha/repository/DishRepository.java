package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;

import java.util.List;

public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findAllDishesInOrderById(Long orderId);
    List<Composition> getDishCompositionById(Long id);
    List<Order> findAllOrdersThatIncludeDishById(Long id);
}