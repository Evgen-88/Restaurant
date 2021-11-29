package com.itrex.kaliaha.repository.hibernate.impl;

import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.exception.RepositoryException;
import com.itrex.kaliaha.repository.hibernate.BaseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Deprecated
public abstract class AbstractRepositoryImpl<E extends BaseEntity<Long>> implements BaseRepository<E> {
    protected static final String ID_COLUMN = "id";
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
    public E findById(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(clazz, id);
        } catch (Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public List<E> findAll() throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(defineSelectAllQuery(), clazz).list();
        } catch (Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public E add(E e) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            session.save(e);
            return e;
        } catch (Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public E update(E e) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                doUpdateOperations(session, e);
                session.getTransaction().commit();
                return e;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    protected void doUpdateOperations(Session session, E e) {
        session.update(e);
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                doDeletionOperations(session, id);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    protected void doDeletionOperations(Session session, Long id) {
        E e = session.get(clazz, id);
        session.delete(e);
    }
}