package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.repository.CompositionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompositionRepositoryImpl extends AbstractRepositoryImpl<Composition> implements CompositionRepository {
    private static final String SELECT_ALL = "from Composition c";

    public CompositionRepositoryImpl(SessionFactory sessionFactory) {
        super(Composition.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
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