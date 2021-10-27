package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;

import javax.persistence.Query;

public class HibernateCompositionRepositoryImpl extends HibernateAbstractRepositoryImpl<Composition> {
    private static final String DISH_ID_COLUMN = "dishId";
    private static final String INGREDIENT_ID_COLUMN = "ingredientId";
    private static final String QUANTITY_COLUMN = "quantity";

    private static final String SELECT_ALL = "from Composition c";
    private static final String UPDATE_QUERY = "update Composition set " +
            "dishId = :dishId, ingredientId = :ingredientId, quantity = :quantity where id = :id";
    private static final String DELETE_QUERY = "delete Composition where id = :id";

    public HibernateCompositionRepositoryImpl() {
        super(Composition.class);
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
    protected void constructQuery(Query query, Composition composition) {
        query.setParameter(DISH_ID_COLUMN, composition.getDishId());
        query.setParameter(INGREDIENT_ID_COLUMN, composition.getIngredientId());
        query.setParameter(QUANTITY_COLUMN, composition.getQuantity());
        query.setParameter(ID_COLUMN, composition.getId());
    }
}