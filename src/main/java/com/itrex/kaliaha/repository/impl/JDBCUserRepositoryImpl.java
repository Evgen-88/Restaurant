package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserRepositoryImpl implements UserRepository {
    private static final String ID_COLUMN = "id";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ADDRESS_COLUMN = "address";
    private static final String ROLE_NAME_COLUMN = "role_name";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM user";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM user WHERE id = ";
    private static final String INSERT_QUERY = "INSERT INTO user(last_name, first_name, login, password, address) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE user SET last_name=?, first_name=?, login=?, password=?, address=? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String DELETE_ORDER_LINK_QUERY = "DELETE FROM `order` WHERE user_id = ?";
    private static final String DELETE_LINK_QUERY = "DELETE FROM user_role_link WHERE user_id = ?";

    private static final String SELECT_USER_ROLES_QUERY = "SELECT role.id, role_name FROM user_role_link LEFT JOIN role ON role.id = role_id WHERE user_id = ";
    private static final String DELETE_USER_ROLE_QUERY = "DELETE FROM user_role_link WHERE user_id = ? AND role_id = ?";

    private final DataSource dataSource;

    public JDBCUserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User selectById(Long id) {
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                return constructUser(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new User();
    }

    private User constructUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(ID_COLUMN));
        user.setLastName(resultSet.getString(LAST_NAME_COLUMN));
        user.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
        user.setLogin(resultSet.getString(LOGIN_COLUMN));
        user.setPassword(resultSet.getString(PASSWORD_COLUMN));
        user.setAddress(resultSet.getString(ADDRESS_COLUMN));
        return user;
    }

    @Override
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                users.add(constructUser(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public void add(User user) {
        try (Connection con = dataSource.getConnection()) {
            insert(con, user);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insert(Connection con, User user) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatementForUserData(preparedStatement, user);
            final int effectiveRows = preparedStatement.executeUpdate();
            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    private void fillPreparedStatementForUserData(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getLastName());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLogin());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getAddress());
    }

    @Override
    public void addAll(List<User> users) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (User user : users) {
                    insert(con, user);
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
    public boolean update(User user) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            fillPreparedStatementForUserData(preparedStatement, user);
            preparedStatement.setLong(6, user.getId());
            return preparedStatement.executeUpdate() == 1;
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

    @Override
    public List<Role> findUserRolesById(Long userId) {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_USER_ROLES_QUERY + userId)) {
            while (resultSet.next()) {
                roles.add(new Role(resultSet.getLong(ID_COLUMN), resultSet.getString(ROLE_NAME_COLUMN)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roles;
    }

    @Override
    public boolean removeUserRoleById(Long userId, Long roleId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_ROLE_QUERY)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, roleId);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}