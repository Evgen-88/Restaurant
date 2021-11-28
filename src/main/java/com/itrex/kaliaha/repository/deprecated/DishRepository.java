package com.itrex.kaliaha.repository.deprecated;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.exception.RepositoryException;

import java.util.List;

@Deprecated
public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findAllDishesInOrderById(Long orderId) throws RepositoryException;
    List<Composition> getCompositionsByDishId(Long dishId) throws RepositoryException;
    Dish addWithCompositions(Dish dish, List<Composition> compositions) throws RepositoryException;
    Dish getDishByCompositionId(Long compositionId) throws RepositoryException;
}