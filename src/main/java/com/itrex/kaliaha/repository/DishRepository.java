package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;

import java.util.List;

public interface DishRepository extends Repository<Dish> {
    List<Dish> findAllDishesInOrderById(Long orderId);
}
