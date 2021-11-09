package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.repository.BaseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class AbstractRepositoryImpl<E extends BaseEntity<Long>> implements BaseRepository<E> {
    private final Class<E> clazz;
    private final SessionFactory sessionFactory;

    public AbstractRepositoryImpl(Class<E> clazz, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazz;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected abstract String defineSelectAllQuery();

    @Override
    public E findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(clazz, id);
        }
    }

    @Override
    public List<E> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(defineSelectAllQuery(), clazz).list();
        }
    }

    @Override
    public boolean add(E e) {
        try (Session session = sessionFactory.openSession()) {
            session.save(e);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(E e) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                doUpdateOperations(session, e);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }

    protected void doUpdateOperations(Session session, E e) {
        session.update(e);
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
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