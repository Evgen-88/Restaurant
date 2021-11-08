package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.repository.CompositionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CompositionRepositoryImpl extends AbstractRepositoryImpl<Composition> implements CompositionRepository {
    private static final String QUANTITY_COLUMN = "quantity";

    private static final String SELECT_ALL = "from Composition c";
    private static final String UPDATE_QUERY = "update Composition set " +
            "quantity = :quantity where id = :id";

    public CompositionRepositoryImpl(SessionFactory sessionFactory) {
        super(Composition.class, sessionFactory);
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
    protected void constructQuery(Query query, Composition composition) {
        query.setParameter(QUANTITY_COLUMN, composition.getQuantity());
        query.setParameter(ID_COLUMN, composition.getId());
    }

    @Override
    public Dish getDishByCompositionId(Long compositionId) {
        try(Session session = getSessionFactory().openSession()) {
            Composition composition = session.get(Composition.class, compositionId);
            if(composition != null) {
                return composition.getDish();
            }
        }
        return new Dish();
    }

    @Override
    public Ingredient getIngredientByCompositionId(Long compositionId) {
        try(Session session = getSessionFactory().openSession()) {
            Composition composition = session.get(Composition.class, compositionId);
            if(composition != null) {
                return composition.getIngredient();
            }
        }
        return new Ingredient();
    }

    @Override
    public boolean addAll(List<Composition> compositions) {
        try(Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                compositions.forEach(session::save);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }
}