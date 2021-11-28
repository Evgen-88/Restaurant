package com.itrex.kaliaha.repository.deprecated.impl;

import com.itrex.kaliaha.entity.Composition;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository(value = "compositionRepository")
public class CompositionRepositoryImpl extends AbstractRepositoryImpl<Composition> {
    private static final String QUANTITY_COLUMN = "quantity";

    private static final String SELECT_ALL = "from Composition c";
    private static final String UPDATE_QUERY = "update Composition set quantity = :quantity where id = :id";

    public CompositionRepositoryImpl(SessionFactory sessionFactory) {
        super(Composition.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected void doUpdateOperations(Session session, Composition composition) {
        session.createQuery(UPDATE_QUERY)
                .setParameter(ID_COLUMN, composition.getId())
                .setParameter(QUANTITY_COLUMN, composition.getQuantity())
                .executeUpdate();
    }
}