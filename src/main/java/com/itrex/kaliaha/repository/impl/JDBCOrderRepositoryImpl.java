package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.enums.OrderStatus;
import com.itrex.kaliaha.repository.OrderRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JDBCOrderRepositoryImpl extends JDBCAbstractRepositoryImpl<Order> implements OrderRepository {
    private static final String PRICE_COLUMN = "price";
    private static final String DATE_COLUMN = "order_date";
    private static final String ADDRESS_COLUMN = "address";
    private static final String ORDER_STATUS_COLUMN = "order_status";
    private static final String USER_ID_COLUMN = "user_id";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM user_order WHERE id = %d";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM user_order";
    private static final String INSERT_QUERY = "INSERT INTO user_order(price, order_date, address, order_status, user_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE user_order SET price=?, order_date=?, address=?, order_status=?, user_id = ?  WHERE id = %d";
    private static final String DELETE_QUERY = "DELETE FROM user_order WHERE id = %d";

    private static final String INSERT_DISH_TO_ORDER = "INSERT INTO order_dish_link(order_id, dish_id) VALUES (?, ?)";
    private static final String DELETE_ALL_DISHES_BY_ORDER_ID_QUERY = "DELETE FROM order_dish_link WHERE order_id = %d";

    private static final String SELECT_ORDERS_BY_USER_ID_QUERY = "SELECT * FROM user_order WHERE user_id = %d";
    private static final String DELETE_ORDER_LINKS_QUERY = "DELETE FROM order_dish_link WHERE order_id = %d";

    public JDBCOrderRepositoryImpl(DataSource dataSource) {
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
    protected Order construct(ResultSet resultSet) throws SQLException {
        return new Order(
                resultSet.getLong(ID_COLUMN),
                resultSet.getInt(PRICE_COLUMN),
                resultSet.getDate(DATE_COLUMN).toLocalDate(),
                resultSet.getString(ADDRESS_COLUMN),
                OrderStatus.valueOf(resultSet.getString(ORDER_STATUS_COLUMN)),
                new User(resultSet.getLong(USER_ID_COLUMN))
        );
    }

    protected void fillPreparedStatement(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setLong(1, order.getPrice());
        preparedStatement.setDate(2, Date.valueOf(order.getDate()));
        preparedStatement.setString(3, order.getAddress());
        preparedStatement.setString(4, order.getOrderStatus().toString());
        preparedStatement.setLong(5, order.getUserId());
    }

    @Override
    public boolean orderDish(Long orderId, Long dishId) {
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DISH_TO_ORDER)) {
            fillPreparedStatementForOrdering(preparedStatement, orderId, dishId);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void fillPreparedStatementForOrdering(PreparedStatement preparedStatement, Long orderId, Long dishId) throws SQLException {
        preparedStatement.setLong(1, orderId);
        preparedStatement.setLong(2, dishId);
    }

    @Override
    public boolean deleteDishesByOrderId(Long orderId) {
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(String.format(DELETE_ALL_DISHES_BY_ORDER_ID_QUERY, orderId))) {
            preparedStatement.executeUpdate();
            return  true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        try (Connection conn = getDataSource().getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(String.format(SELECT_ORDERS_BY_USER_ID_QUERY, userId))) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(construct(resultSet));
            }
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean delete(Long id) {
        String query = String.format(defineDeleteQuery(), id);
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            try{
                deleteOrderLinks(conn, id);
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

    private void deleteOrderLinks(Connection conn, Long orderId) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(String.format(DELETE_ORDER_LINKS_QUERY, orderId))) {
            preparedStatement.executeUpdate();
        }
    }
}