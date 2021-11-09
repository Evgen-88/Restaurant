package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.AddMethodUserRepositoryImplException;
import com.itrex.kaliaha.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {
    private static final String SELECT_ALL = "from User u";

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public boolean add(User user) {
        throw new AddMethodUserRepositoryImplException("You can't use this method, " +
                "you should add new user with add(User user, List<Role> roles) method");
    }

    @Override
    public boolean add(User user, List<Role> roles) {
        try (Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                user.setRoles(roles);
                session.save(user);
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
    protected void doDeletionOperations(Session session, Long id) {
        User user = session.get(User.class, id);
        deleteOrderLinks(session, user.getOrders());
        session.delete(user);
    }

    private void deleteOrderLinks(Session session, List<Order> orders) {
        for (Order order : orders) {
            session.delete(order);
        }
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        try (Session session = getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            if (user != null) {
                return new ArrayList<>(user.getRoles());
            }
            return new ArrayList<>();
        }
    }

    @Override
    public boolean deleteRoleFromUserById(Long userId, Long roleId) {
        try (Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
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