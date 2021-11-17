package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role> implements RoleRepository {
    private static final String SELECT_ALL = "from Role r";

    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        super(Role.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected void doDeletionOperations(Session session, Long id) {
        Role role = session.get(Role.class, id);
        deleteRoleLinks(role.getUsers(), role);
        session.delete(role);
    }

    private void deleteRoleLinks(List<User> users, Role deletedRole) {
        users.forEach(user -> user.getRoles().remove(deletedRole));
    }

    @Override
    public Set<Role> findRolesByUserId(Long userId) {
        try (Session session = getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            if (user != null) {
                return new HashSet<>(user.getRoles());
            }
            return new HashSet<>();
        }
    }
}