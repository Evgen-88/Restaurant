package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.OrderRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends AbstractRepositoryImpl<Order> implements OrderRepository {
    private static final String SELECT_ALL = "from Order o";

    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        super(Order.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        try(Session session = getSessionFactory().openSession())  {
            User user = session.get(User.class, userId);
            if(user != null) {
                return new ArrayList<>(user.getOrders());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean orderDish(Long orderId, Long dishId) {
        try(Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Order order = session.get(Order.class, orderId);
                Dish dish = session.get(Dish.class, dishId);
                order.getDishes().add(dish);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }

    @Override
    public boolean deleteFromOrderDishById(Long orderId, Long dishId) {
        try(Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Order order = session.get(Order.class, orderId);
                Dish dish = session.get(Dish.class, dishId);
                order.getDishes().remove(dish);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }
}