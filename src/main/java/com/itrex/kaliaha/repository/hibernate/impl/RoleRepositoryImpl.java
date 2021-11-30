package com.itrex.kaliaha.repository.hibernate.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.hibernate.BaseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Deprecated
public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role> implements BaseRepository<Role> {
    private static final String SELECT_ALL = "from Role r";

    public RoleRepositoryImpl(EntityManagerFactory factory) {
        super(Role.class, factory.unwrap(SessionFactory.class));
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
}