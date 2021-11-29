package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Order;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Primary
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("from Order o join fetch o.user left join fetch o.dishes where o.id=:id")
    Optional<Order> findById(Long id);
}
