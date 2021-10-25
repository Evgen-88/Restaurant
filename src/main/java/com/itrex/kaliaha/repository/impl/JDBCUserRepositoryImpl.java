package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserRepositoryImpl
        extends JDBCAbstractRepositoryImpl<User>
        implements UserRepository {
    private static final String ID_COLUMN = "id";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ADDRESS_COLUMN = "address";
    private static final String ROLE_NAME_COLUMN = "role_name";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id = %d";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String INSERT_QUERY = "INSERT INTO users(last_name, first_name, login, password, address) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET last_name=?, first_name=?, login=?, password=?, address=? WHERE id = %d";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = %d";

    private static final String DELETE_ORDERS_BY_USER_ID_QUERY = "DELETE FROM USER_ORDER WHERE user_id = %d";
    private static final String DELETE_ORDER_DISH_LINK_QUERY = "DELETE FROM order_dish_link WHERE order_id = %d";
    private static final String DELETE_USER_ROLES_LINK_QUERY = "DELETE FROM user_role_link WHERE user_id = %d";

    private static final String SELECT_USER_ROLES_QUERY = "SELECT r.id, r.role_name FROM user_role_link url LEFT JOIN user_role r ON r.id = url.role_id WHERE url.user_id = %d";
    private static final String DELETE_USER_ROLE_QUERY = "DELETE FROM user_role_link WHERE user_id = ? AND role_id = ?";

    public JDBCUserRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String defineSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineInsertQuery() {
        return INSERT_QUERY;
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
    protected void fillPreparedStatement(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedStatement.setString(1, entity.getLastName());
        preparedStatement.setString(2, entity.getFirstName());
        preparedStatement.setString(3, entity.getLogin());
        preparedStatement.setString(4, entity.getPassword());
        preparedStatement.setString(5, entity.getAddress());
    }

    protected User construct(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong(ID_COLUMN),
                resultSet.getString(LAST_NAME_COLUMN),
                resultSet.getString(FIRST_NAME_COLUMN),
                resultSet.getString(LOGIN_COLUMN),
                resultSet.getString(PASSWORD_COLUMN),
                resultSet.getString(ADDRESS_COLUMN)
        );
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = getDataSource().getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(String.format(SELECT_USER_ROLES_QUERY, userId))) {
            while (resultSet.next()) {
                roles.add(new Role(resultSet.getLong(ID_COLUMN), resultSet.getString(ROLE_NAME_COLUMN)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roles;
    }

    @Override
    public boolean deleteRoleByUserId(Long userId, Long roleId) {
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_ROLE_QUERY)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, roleId);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String query = String.format(defineDeleteQuery(), id);
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            try {
                deleteOrdersByUserId(conn, id);
                deleteUserRoles(conn, id);
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                conn.rollback();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void deleteOrdersByUserId(Connection conn, Long userId) throws SQLException {
        OrderRepository orderRepository = new JDBCOrderRepositoryImpl(getDataSource());
        List<Order> orders = orderRepository.findOrdersByUserId(userId);
        try (PreparedStatement preparedStatement = conn.prepareStatement(String.format(DELETE_ORDERS_BY_USER_ID_QUERY, userId))) {
            for (Order order : orders) {
                deleteOrderLinks(conn, order.getId());
            }
            preparedStatement.executeUpdate();
        }
    }

    private void deleteOrderLinks(Connection conn, Long orderId) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(String.format(DELETE_ORDER_DISH_LINK_QUERY, orderId))) {
            preparedStatement.executeUpdate();
        }
    }

    private void deleteUserRoles(Connection conn, Long userId) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(String.format(DELETE_USER_ROLES_LINK_QUERY, userId))) {
            preparedStatement.executeUpdate();
        }
    }
}