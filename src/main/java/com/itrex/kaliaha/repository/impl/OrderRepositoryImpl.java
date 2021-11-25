package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.exception.RepositoryException;
import com.itrex.kaliaha.repository.OrderRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends AbstractRepositoryImpl<Order> implements OrderRepository {
    private static final String PRICE_COLUMN = "price";
    private static final String DATE_COLUMN = "date";
    private static final String ADDRESS_COLUMN = "address";
    private static final String ORDER_STATUS_COLUMN = "orderStatus";

    private static final String SELECT_BY_ID =
            "from Order o join fetch o.user left join fetch o.dishes where o.id=:id";
    private static final String SELECT_ORDERS_BY_DISH_ID =
            "from Order o left join fetch o.dishes d where d.id=:id";
    private static final String SELECT_ORDERS_BY_USER_ID =
            "from Order o left join fetch o.user u where u.id=:id";

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
    public Order findById(Long id) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return session.createQuery(SELECT_BY_ID, Order.class)
                        .setParameter(ID_COLUMN, id)
                        .getSingleResult();
            } catch (Exception ex) {
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
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
    public List<Order> findOrdersByUserId(Long userId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return session.createQuery(SELECT_ORDERS_BY_USER_ID, Order.class)
                        .setParameter(ID_COLUMN, userId)
                        .list();
            } catch (Exception ex) {
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    @Override
    public List<Order> findAllOrdersThatIncludeDishByDishId(Long dishId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return session.createQuery(SELECT_ORDERS_BY_DISH_ID, Order.class)
                        .setParameter(ID_COLUMN, dishId)
                        .list();
            } catch (Exception ex) {
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    @Override
    public boolean addDishToOrder(Long orderId, Long dishId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Order order = session.get(Order.class, orderId);
                Dish dish = session.get(Dish.class, dishId);
                order.getDishes().add(dish);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    @Override
    public boolean deleteDishFromOrder(Long orderId, Long dishId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Order order = session.get(Order.class, orderId);
                Dish dish = session.get(Dish.class, dishId);
                order.getDishes().remove(dish);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }
}