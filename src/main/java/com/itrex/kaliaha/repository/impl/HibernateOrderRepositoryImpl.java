package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class HibernateOrderRepositoryImpl
        extends HibernateAbstractRepositoryImpl<Order>
        implements OrderRepository {
    private static final String PRICE_COLUMN = "price";
    private static final String DATE_COLUMN = "date";
    private static final String ADDRESS_COLUMN = "address";
    private static final String ORDER_STATUS_COLUMN = "orderStatus";
    private static final String USER_ID_COLUMN = "userId";

    private static final String SELECT_ALL = "from Order o";
    private static final String UPDATE_QUERY = "update Order set " +
            "price = :price, date = :date, address = :address, " +
            "orderStatus = :orderStatus, userId = :userId where id = :id";
    private static final String DELETE_QUERY = "delete Order where id = :id";

    public HibernateOrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String defineUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String defineDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected void constructQuery(Query query, Order order) {
        query.setParameter(PRICE_COLUMN, order.getPrice());
        query.setParameter(DATE_COLUMN, order.getDate());
        query.setParameter(ADDRESS_COLUMN, order.getAddress());
        query.setParameter(ORDER_STATUS_COLUMN, order.getOrderStatus());
        query.setParameter(USER_ID_COLUMN, order.getUserId());
        query.setParameter(ID_COLUMN, order.getId());
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession())  {
            User user = session.get(User.class, userId);
            if(user != null) {
                List<Order> orders = user.getOrders();
                Hibernate.initialize(orders);
                return orders;
            }
            return new ArrayList<>();
        }
    }

    @Override
    public boolean orderDish(Long orderId, Long dishId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
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
    public boolean deleteDishesByOrderId(Long orderId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Order order = session.get(Order.class, orderId);
                order.getDishes().clear();
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