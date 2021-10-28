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

public class JDBCUserRepositoryImpl implements UserRepository {
    private static final String ID_COLUMN = "id";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ADDRESS_COLUMN = "address";
    private static final String ROLE_NAME_COLUMN = "role_name";

    private static final Long DEFAULT_ROLE_ID = 2L;

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id = %d";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String INSERT_QUERY = "INSERT INTO users(last_name, first_name, login, password, address) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET last_name=?, first_name=?, login=?, password=?, address=? WHERE id = %d";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = %d";

    private static final String DELETE_ORDERS_BY_USER_ID_QUERY = "DELETE FROM USER_ORDER WHERE user_id = %d";
    private static final String DELETE_ORDER_DISH_LINK_QUERY = "DELETE FROM order_dish_link WHERE order_id = %d";

    private static final String SELECT_USER_ROLES_QUERY = "SELECT r.id, r.role_name FROM user_role_link url LEFT JOIN user_role r ON r.id = url.role_id WHERE url.user_id = %d";
    private static final String INSERT_USER_ROLE_LINK_QUERY = "INSERT INTO user_role_link(user_id, role_id) VALUES (?, ?)";
    private static final String DELETE_USER_ROLE_QUERY = "DELETE FROM user_role_link WHERE user_id = ? AND role_id = ?";
    private static final String DELETE_USER_ROLES_LINK_QUERY = "DELETE FROM user_role_link WHERE user_id = %d";

    private DataSource dataSource;

    public JDBCUserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long id) {
        User user;
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(String.format(SELECT_BY_ID_QUERY, id))) {
            if (resultSet.next()) {
                user = construct(resultSet);
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private User construct(ResultSet resultSet) throws SQLException {
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
    public List<User> findAll() {
        List<User> entities = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                entities.add(construct(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entities;
    }

    @Override
    public boolean addAll(List<User> users, List<Role> roles) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (User e : users) {
                    insert(con, e);
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
        return false;
    }

    @Override
    public boolean update(User user) {
        return true;
    }

    @Override
    public boolean add(User user, List<Role> roles) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                insert(con, user);
                addUserRoleById(con, user.getId());
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    protected void insert(Connection con, User user) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(preparedStatement, user);
            if (preparedStatement.executeUpdate() == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getLastName());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLogin());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getAddress());
    }

    private void addUserRoleById(Connection conn, Long userId) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USER_ROLE_LINK_QUERY)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, DEFAULT_ROLE_ID);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
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
    public boolean deleteRoleFromUserById(Long userId, Long roleId) {
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

    @Override
    public boolean delete(Long id) {
        String query = String.format(DELETE_QUERY, id);
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            try {
                deleteOrdersByUserId(conn, id);
                deleteUserRolesLinks(conn, id);
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
        OrderRepository orderRepository = new JDBCOrderRepositoryImpl(dataSource);
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

    private void deleteUserRolesLinks(Connection conn, Long userId) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(String.format(DELETE_USER_ROLES_LINK_QUERY, userId))) {
            preparedStatement.executeUpdate();
        }
    }
}