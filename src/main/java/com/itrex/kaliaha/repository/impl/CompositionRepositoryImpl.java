package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;

import javax.persistence.Query;

public class CompositionRepositoryImpl extends AbstractRepositoryImpl<Composition> {
    private static final String QUANTITY_COLUMN = "quantity";

    private static final String SELECT_ALL = "from Composition c";
    private static final String UPDATE_QUERY = "update Composition set quantity = :quantity where id = :id";

    public CompositionRepositoryImpl() {
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
    protected void constructQuery(Query query, Composition composition) {
        query.setParameter(QUANTITY_COLUMN, composition.getQuantity());
        query.setParameter(ID_COLUMN, composition.getId());
    }
}