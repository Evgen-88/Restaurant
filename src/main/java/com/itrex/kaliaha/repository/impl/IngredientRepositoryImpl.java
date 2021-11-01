package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.repository.IngredientRepository;
import com.itrex.kaliaha.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepositoryImpl extends AbstractRepositoryImpl<Ingredient> implements IngredientRepository {
    private static final String INGREDIENT_NAME_COLUMN = "ingredientName";
    private static final String PRICE_COLUMN = "price";
    private static final String REMAINDER_COLUMN = "remainder";
    private static final String MEASUREMENT_COLUMN = "measurement";

    private static final String SELECT_ALL = "from Ingredient i";
    private static final String UPDATE_QUERY = "update Ingredient set ingredientName = :ingredientName, price = :price, remainder = :remainder, measurement = :measurement where id = :id";

    public IngredientRepositoryImpl() {
        super(Ingredient.class);
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
    protected void constructQuery(Query query, Ingredient ingredient) {
        query.setParameter(INGREDIENT_NAME_COLUMN, ingredient.getIngredientName());
        query.setParameter(PRICE_COLUMN, ingredient.getPrice());
        query.setParameter(REMAINDER_COLUMN, ingredient.getRemainder());
        query.setParameter(MEASUREMENT_COLUMN, ingredient.getMeasurement());
        query.setParameter(ID_COLUMN, ingredient.getId());
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
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Ingredient ingredient = session.get(Ingredient.class, ingredientId);
            if(ingredient != null) {
                return new ArrayList<>(ingredient.getCompositions());
            }
        }
        return new ArrayList<>();
    }
}