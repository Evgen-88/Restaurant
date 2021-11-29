package com.itrex.kaliaha.repository.hibernate.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.RepositoryException;
import com.itrex.kaliaha.repository.hibernate.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Deprecated
@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ADDRESS_COLUMN = "address";

    private static final String SELECT_BY_ID =
            "from User u left join fetch u.orders left join fetch u.roles where u.id =:id";
    private static final String SELECT_ROLES_BY_USER_ID =
            "from Role r left join fetch r.users u where u.id =:id";
    private static final String SELECT_USERS_BY_ROLE_ID =
            "from User u left join fetch u.roles r where r.id =:id";

    private static final String SELECT_ALL = "from User u";
    private static final String UPDATE_QUERY = "update User set " +
            "lastName = :lastName, firstName = :firstName, login = :login, " +
            "password = :password, address = :address where id = :id";

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    public User findById(Long id) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return session.createQuery(SELECT_BY_ID, User.class)
                        .setParameter(ID_COLUMN, id)
                        .getSingleResult();
            } catch (Exception ex) {
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    @Override
    protected void doUpdateOperations(Session session, User user) {
        session.createQuery(UPDATE_QUERY)
                .setParameter(ID_COLUMN, user.getId())
                .setParameter(LAST_NAME_COLUMN, user.getLastName())
                .setParameter(FIRST_NAME_COLUMN, user.getFirstName())
                .setParameter(LOGIN_COLUMN, user.getLogin())
                .setParameter(PASSWORD_COLUMN, user.getPassword())
                .setParameter(ADDRESS_COLUMN, user.getAddress())
                .executeUpdate();
    }

    @Override
    public User add(User user) throws RepositoryException {
        throw new RepositoryException("You can't use this method, " +
                "you should add new user with add(User user, List<Role> roles) method");
    }

    @Override
    public User add(User user, Set<Role> roles) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                user.setRoles(roles);
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
        return user;
    }

    @Override
    protected void doDeletionOperations(Session session, Long id) {
        User user = session.get(User.class, id);
        deleteOrderLinks(session, user.getOrders());
        session.delete(user);
    }

    private void deleteOrderLinks(Session session, Set<Order> orders) {
        for (Order order : orders) {
            session.delete(order);
        }
    }

    @Override
    public boolean addRoleToUser(Long userId, Long roleId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                User user = session.get(User.class, userId);
                Role role = session.get(Role.class, roleId);
                user.getRoles().add(role);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    @Override
    public boolean deleteRoleFromUser(Long userId, Long roleId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                User user = session.get(User.class, userId);
                deleteRoleFromUser(user.getRoles(), roleId);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    private void deleteRoleFromUser(Set<Role> roles, Long roleId) {
        for (Role role : roles) {
            if (Objects.equals(role.getId(), roleId)) {
                roles.remove(role);
                break;
            }
        }
    }

    @Override
    public List<User> findAllUsersWhoHaveRoleById(Long roleId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return session.createQuery(SELECT_USERS_BY_ROLE_ID, User.class)
                        .setParameter(ID_COLUMN, roleId)
                        .list();
            } catch (Exception ex) {
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }

    @Override
    public Set<Role> findRolesByUserId(Long userId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            try {
                return new HashSet<>(
                        session.createQuery(SELECT_ROLES_BY_USER_ID, Role.class)
                                .setParameter(ID_COLUMN, userId)
                                .list());
            } catch (Exception ex) {
                throw new RepositoryException(ex.getMessage(), ex.getCause());
            }
        }
    }
}