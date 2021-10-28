package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HibernateUserRepositoryImpl implements UserRepository {
    private static final String ID_COLUMN = "id";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ADDRESS_COLUMN = "address";

    private static final String SELECT_ALL = "from User u";
    private static final String UPDATE_QUERY = "update User set " +
            "lastName = :lastName, firstName = :firstName, login = :login, " +
            "password = :password, address = :address where id = :id";
    public HibernateUserRepositoryImpl() {}

    protected void constructQuery(Query query, User user) {
        query.setParameter(LAST_NAME_COLUMN, user.getLastName());
        query.setParameter(FIRST_NAME_COLUMN, user.getFirstName());
        query.setParameter(LOGIN_COLUMN, user.getLogin());
        query.setParameter(PASSWORD_COLUMN, user.getPassword());
        query.setParameter(ADDRESS_COLUMN, user.getAddress());
        query.setParameter(ID_COLUMN, user.getId());
    }

    @Override
    public User findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL, User.class).list();
        }
    }

    @Override
    public boolean update(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, user);
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

    @Override
    public boolean add(User user, List<Role> roles) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                saveUser(session, user, roles);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }

    private void saveUser(Session session, User user, List<Role> roles) {
        user.setRoles(roles);
        session.save(user);
    }

    @Override
    public boolean addAll(List<User> users, List<Role> roles) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try{
                for (User user : users) {
                    saveUser(session, user, roles);
                }
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                User user = session.get(User.class, id);
                deleteOrderLinks(session, user.getOrders());
                session.delete(user);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
            return false;
        }
    }

    private void deleteOrderLinks(Session session, List<Order> orders) {
        for (Order order : orders) {
            session.delete(order);
        }
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            if (user != null) {
                return new ArrayList<>(user.getRoles());
            }
            return new ArrayList<>();
        }
    }

    @Override
    public boolean deleteRoleFromUserById(Long userId, Long roleId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                User user = session.get(User.class, userId);
                deleteRoleFromUser(user.getRoles(), roleId);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }

    private void deleteRoleFromUser(List<Role> roles, Long roleId) {
        for (Role role : roles) {
            if (Objects.equals(role.getId(), roleId)) {
                roles.remove(role);
                break;
            }
        }
    }
}