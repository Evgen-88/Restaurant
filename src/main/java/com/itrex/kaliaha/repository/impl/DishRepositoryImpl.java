package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.repository.DishRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepositoryImpl extends AbstractRepositoryImpl<Dish> implements DishRepository {
    private static final String DISH_NAME_COLUMN = "dishName";
    private static final String PRICE_COLUMN = "price";
    private static final String DISH_GROUP_COLUMN = "dishGroup";
    private static final String DISH_DESCRIPTION_COLUMN = "dishDescription";
    private static final String IMAGE_PATH_COLUMN = "imagePath";

    private static final String SELECT_ALL = "from Dish r";
    private static final String UPDATE_QUERY = "update Dish set " +
            "dishName = :dishName, price = :price, " +
            "dishGroup = :dishGroup, dishDescription = :dishDescription, " +
            "imagePath = :imagePath where id = :id";


    public DishRepositoryImpl(SessionFactory sessionFactory) {
        super(Dish.class, sessionFactory);
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
    protected void constructQuery(Query query, Dish dish) {
        query.setParameter(DISH_NAME_COLUMN, dish.getDishName());
        query.setParameter(PRICE_COLUMN, dish.getPrice());
        query.setParameter(DISH_GROUP_COLUMN, dish.getDishGroup());
        query.setParameter(DISH_DESCRIPTION_COLUMN, dish.getDishDescription());
        query.setParameter(IMAGE_PATH_COLUMN, dish.getImagePath());
        query.setParameter(ID_COLUMN, dish.getId());
    }

    @Override
    protected void doDeletionOperations(Session session, Long id) {
        Dish dish = session.get(Dish.class, id);
        deleteDishLinksComposition(session, dish.getCompositions());
        deleteDishLinksOrder(dish.getOrders(), dish);
        session.delete(dish);
    }

    private void deleteDishLinksComposition(Session session, List<Composition> compositions){
        for(Composition composition : compositions) {
            session.delete(composition);
        }
    }

    private void deleteDishLinksOrder(List<Order> orders, Dish deletedDish){
        for(Order order : orders) {
            order.getDishes().remove(deletedDish);
        }
    }

    @Override
    public List<Dish> findAllDishesInOrderById(Long orderId) {
        try(Session session = getSessionFactory().openSession()) {
            Order order = session.get(Order.class, orderId);
            if(order != null) {
                return new ArrayList<>(order.getDishes());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Order> findAllOrdersThatIncludeDishByDishId(Long dishId) {
        try(Session session = getSessionFactory().openSession()) {
            Dish dish = session.get(Dish.class, dishId);
            if(dish != null) {
                return new ArrayList<>(dish.getOrders());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Composition> getCompositionsByDishId(Long dishId) {
        try(Session session = getSessionFactory().openSession()) {
            Dish dish = session.get(Dish.class, dishId);
            if(dish != null) {
                return new ArrayList<>(dish.getCompositions());
            }
        }
        return new ArrayList<>();
    }
}