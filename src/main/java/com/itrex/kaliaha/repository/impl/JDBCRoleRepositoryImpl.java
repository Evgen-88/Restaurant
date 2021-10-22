package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.repository.RoleRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JDBCRoleRepositoryImpl implements RoleRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "role_name";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM role";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM role WHERE id = ";
    private static final String INSERT_QUERY = "INSERT INTO role(role_name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE role SET role_name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM role WHERE id = ?";
    private static final String DELETE_LINK_QUERY = "DELETE FROM user_role_link WHERE role_id = ?";

    private final DataSource dataSource;

    public JDBCRoleRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Role selectById(Long id) {
        Role role = new Role();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                settingRole(resultSet, role);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return role;
    }

    private void settingRole(ResultSet resultSet, Role role) throws SQLException {
        role.setId(resultSet.getLong(ID_COLUMN));
        role.setRoleName(resultSet.getString(NAME_COLUMN));
    }

    @Override
    public List<Role> selectAll() {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Role role = new Role();
                settingRole(resultSet, role);
                roles.add(role);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roles;
    }

    @Override
    public void add(Role role) {
        try (Connection connection = dataSource.getConnection()) {
            insert(connection, role);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insert(Connection con, Role role) throws SQLException {
        try (PreparedStatement preparedStatement =
                     con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, role.getRoleName());
            if (preparedStatement.executeUpdate() == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        role.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    @Override
    public void addAll(List<Role> roles) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Role role : roles) {
                    insert(con, role);
                }
                con.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                con.rollback();
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean update(Role role) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.setLong(2, role.getId());
            return  preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}