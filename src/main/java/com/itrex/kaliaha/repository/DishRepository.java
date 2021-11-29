package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Dish;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Primary
public interface DishRepository extends JpaRepository<Dish, Long> {
    @Query("from Dish d left join fetch d.compositions c left join fetch c.ingredient where d.id =:id")
    Optional<Dish> findById(Long id);

    @Query("from Dish d left join fetch d.orders o where o.id=:id")
    List<Dish> findAllByOrderId(Long id);
}