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
    private static final String PRICE_COLUMN = "price";
    private static final String DATE_COLUMN = "date";
    private static final String ADDRESS_COLUMN = "address";
    private static final String ORDER_STATUS_COLUMN = "orderStatus";

    private static final String SELECT_ALL = "from Order o";
    private static final String UPDATE_QUERY = "update Order set " +
            "price = :price, date = :date, address = :address, " +
            "orderStatus = :orderStatus where id = :id";

    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        super(Order.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected void doUpdateOperations(Session session, Order order) {
        session.createQuery(UPDATE_QUERY)
                .setParameter(ID_COLUMN, order.getId())
                .setParameter(PRICE_COLUMN, order.getPrice())
                .setParameter(DATE_COLUMN, order.getDate())
                .setParameter(ADDRESS_COLUMN, order.getAddress())
                .setParameter(ORDER_STATUS_COLUMN, order.getOrderStatus())
                .executeUpdate();
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