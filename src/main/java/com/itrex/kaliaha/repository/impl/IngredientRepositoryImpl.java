package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.repository.IngredientRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientRepositoryImpl extends AbstractRepositoryImpl<Ingredient> implements IngredientRepository {
    private static final String SELECT_ALL = "from Ingredient i";

    public IngredientRepositoryImpl(SessionFactory sessionFactory) {
        super(Ingredient.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected void doDeletionOperations(Session session, Long id) {
        Ingredient ingredient = session.get(Ingredient.class, id);
        deleteIngredientLinks(session, ingredient.getCompositions());
        session.delete(ingredient);
    }

    private void deleteIngredientLinks(Session session, List<Composition> compositions){
        for(Composition composition : compositions) {
            session.delete(composition);
        }
    }

    @Override
    public List<Composition> findAllCompositionsThatIncludeIngredientById(Long ingredientId) {
        try(Session session = getSessionFactory().openSession()) {
            Ingredient ingredient = session.get(Ingredient.class, ingredientId);
            if(ingredient != null) {
                return new ArrayList<>(ingredient.getCompositions());
            }
        }
        return new ArrayList<>();
    }
}