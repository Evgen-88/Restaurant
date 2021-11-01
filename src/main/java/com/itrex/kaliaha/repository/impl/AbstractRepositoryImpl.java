package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.repository.BaseRepository;
import com.itrex.kaliaha.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public abstract class AbstractRepositoryImpl<E extends BaseEntity<Long>> implements BaseRepository<E> {
    public static final String ID_COLUMN = "id";

    private final Class<E> clazz;

    public AbstractRepositoryImpl(Class<E> clazz) {
        this.clazz = clazz;
    }

    protected abstract String defineSelectAllQuery();

    protected abstract String defineUpdateQuery();

    @Override
    public final E findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(clazz, id);
        }
    }

    @Override
    public final List<E> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(defineSelectAllQuery(), clazz).list();
        }
    }

    @Override
    public boolean add(E e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(e);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public final boolean update(E e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(defineUpdateQuery());
                constructQuery(query, e);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }

    protected abstract void constructQuery(Query query, E e);

    @Override
    public final boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                doDeletionOperations(session, id);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }

    protected void doDeletionOperations(Session session, Long id) {
        E e = session.get(clazz, id);
        session.delete(e);
    }
}