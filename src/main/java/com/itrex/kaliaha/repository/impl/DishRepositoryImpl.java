package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.repository.DishRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class DishRepositoryImpl extends AbstractRepositoryImpl<Dish> implements DishRepository {
    private static final String SELECT_BY_ID =
            "from Dish d left join fetch d.compositions c left join fetch c.ingredient where d.id =:id";
    private static final String SELECT_DISH_BY_COMPOSITION_ID =
            "from Dish d left join fetch d.compositions c where c.id=:id";
    private static final String SELECT_DISHES_BY_ORDER_ID =
            "from Dish d left join fetch d.orders o where o.id=:id";
    private static final String SELECT_COMPOSITIONS_BY_DISH_ID =
            "from Composition c left join fetch c.dish d where d.id=:id";
    private static final String SELECT_ALL = "from Dish r";

    public DishRepositoryImpl(SessionFactory sessionFactory) {
        super(Dish.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public Dish findById(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return session.createQuery(SELECT_BY_ID, Dish.class)
                        .setParameter(ID_COLUMN, id)
                        .getSingleResult();
            } catch (NoResultException ex) {
                return null;
            }
        }
    }

    @Override
    protected void doDeletionOperations(Session session, Long id) {
        Dish dish = session.get(Dish.class, id);
        deleteDishLinksComposition(session, dish.getCompositions());
        deleteDishLinksOrder(dish.getOrders(), dish);
        session.delete(dish);
    }

    private void deleteDishLinksComposition(Session session, List<Composition> compositions) {
        for (Composition composition : compositions) {
            session.delete(composition);
        }
    }

    private void deleteDishLinksOrder(List<Order> orders, Dish deletedDish) {
        for (Order order : orders) {
            order.getDishes().remove(deletedDish);
        }
    }

    @Override
    public List<Dish> findAllDishesInOrderById(Long orderId) {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return session.createQuery(SELECT_DISHES_BY_ORDER_ID, Dish.class)
                        .setParameter(ID_COLUMN, orderId)
                        .list();
            } catch (NoResultException ex) {
                return null;
            }
        }
    }

    @Override
    public List<Composition> getCompositionsByDishId(Long dishId) {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return session.createQuery(SELECT_COMPOSITIONS_BY_DISH_ID, Composition.class)
                        .setParameter(ID_COLUMN, dishId)
                        .list();
            } catch (NoResultException ex) {
                return null;
            }
        }
    }

    @Override
    public Dish addWithCompositions(Dish dish, List<Composition> compositions) {
        try (Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                session.save(dish);
                saveCompositions(session, compositions);
                session.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return dish;
    }

    public void saveCompositions(Session session, List<Composition> compositions) {
        for (Composition composition : compositions) {
            session.save(composition);
        }
    }

    @Override
    public Dish getDishByCompositionId(Long compositionId) {
        try(Session session = getSessionFactory().openSession()) {
            return session.createQuery(SELECT_DISH_BY_COMPOSITION_ID, Dish.class)
                    .setParameter(ID_COLUMN, compositionId)
                    .getSingleResult();
        }
    }
}