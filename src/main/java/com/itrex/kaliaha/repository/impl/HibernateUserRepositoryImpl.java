package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class HibernateUserRepositoryImpl
        extends HibernateAbstractRepositoryImpl<User>
        implements UserRepository {
    private static final Long DEFAULT_ROLE_ID = 2L;

    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ADDRESS_COLUMN = "address";

    private static final String SELECT_ALL = "from User u";
    private static final String UPDATE_QUERY = "update User set " +
            "lastName = :lastName, firstName = :firstName, login = :login, " +
            "password = :password, address = :address where id = :id";

    private static final String DELETE_QUERY = "delete User where id = :id";

    public HibernateUserRepositoryImpl() {
        super(User.class);
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
    protected void constructQuery(Query query, User user) {
        query.setParameter(LAST_NAME_COLUMN, user.getLastName());
        query.setParameter(FIRST_NAME_COLUMN, user.getFirstName());
        query.setParameter(LOGIN_COLUMN, user.getLogin());
        query.setParameter(PASSWORD_COLUMN, user.getPassword());
        query.setParameter(ADDRESS_COLUMN, user.getAddress());
        query.setParameter(ID_COLUMN, user.getId());
    }

    @Override
    public void add(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            saveUser(session, user);
        }
    }

    private void saveUser(Session session, User user) {
        session.getTransaction().begin();
        setDefaultRole(session, user);
        session.save(user);
        session.getTransaction().commit();
    }

    private void setDefaultRole(Session session, User user) {
        Role role = session.get(Role.class, DEFAULT_ROLE_ID);
        user.setRoles(new ArrayList<>() {{add(role);}});
    }

    @Override
    public void addAll(List<User> users) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            for (User user : users) {
                saveUser(session, user);
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    private void deleteOrderLinks(Session session, List<Order> orders){
        for(Order order : orders) {
            session.delete(order);
        }
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            if(user != null) {
                List<Role> roles =  user.getRoles();
                Hibernate.initialize(roles);
                return roles;
            }
            return new ArrayList<>();
        }
    }

    @Override
    public boolean deleteRoleFromUserById(Long userId, Long roleId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try{
                User user = session.get(User.class, userId);
                Role role = session.get(Role.class, roleId);
                List<Role> roles = user.getRoles();
                if(roles.contains(role)) {
                    roles.remove(role);
                    session.getTransaction().commit();
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }
}