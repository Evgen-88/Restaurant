package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.RoleRepository;
import com.itrex.kaliaha.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class HibernateRoleRepositoryImpl extends HibernateAbstractRepositoryImpl<Role> implements RoleRepository {
    private static final String ROLE_NAME_COLUMN = "roleName";

    private static final String SELECT_ALL = "from Role r";
    private static final String UPDATE_QUERY = "update Role set roleName = :roleName where id = :id";
    private static final String DELETE_QUERY = "delete Role where id = :id";

    public HibernateRoleRepositoryImpl() {
        super(Role.class);
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
    protected void constructQuery(Query query, Role role) {
        query.setParameter(ROLE_NAME_COLUMN, role.getRoleName());
        query.setParameter(ID_COLUMN, role.getId());
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            try {
                Role role = session.get(Role.class, id);
                deleteRoleLinks(role.getUsers(), role);
                session.delete(role);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        return false;
    }

    private void deleteRoleLinks(List<User> users, Role deletionRole) {
        for (User user : users) {
            List<Role> roles = user.getRoles();
            roles.remove(deletionRole);
        }
    }

    @Override
    public List<User> findAllUsersWhoHaveRoleById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Role role = session.get(Role.class, id);
            if(role != null) {
                return new ArrayList<>(role.getUsers());
            }
        }
        return new ArrayList<>();
    }
}