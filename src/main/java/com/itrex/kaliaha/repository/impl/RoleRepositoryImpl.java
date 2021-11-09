package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public List<User> findAllUsersWhoHaveRoleById(Long roleId) {
        try (Session session = getSessionFactory().openSession()) {
            Role role = session.get(Role.class, roleId);
            if (role != null) {
                return new ArrayList<>(role.getUsers());
            }
        }
        return new ArrayList<>();
    }
}