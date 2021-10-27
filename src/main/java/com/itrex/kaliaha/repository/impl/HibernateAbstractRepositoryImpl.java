package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.repository.BaseRepository;
import com.itrex.kaliaha.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public abstract class HibernateAbstractRepositoryImpl<E extends BaseEntity<Long>>
        implements BaseRepository<E> {
    public static final String ID_COLUMN = "id";

    private final Class<E> clazz;

    public HibernateAbstractRepositoryImpl(Class<E> clazz) {
        this.clazz = clazz;
    }

    protected abstract String defineSelectAllQuery();

    protected abstract String defineUpdateQuery();

    protected abstract String defineDeleteQuery();

    @Override
    public E findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(clazz, id);
        }
    }

    @Override
    public List<E> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(defineSelectAllQuery(), clazz).list();
        }
    }

    @Override
    public void add(E e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(e);
        }
    }

    @Override
    public void addAll(List<E> e) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            for (E element : e) {
                session.save(element);
            }
        }
    }

    @Override
    public boolean update(E e) {
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
            return false;
        }
    }

    protected abstract void constructQuery(Query query, E e);

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                E e = session.get(clazz, id);
                session.delete(e);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }
}