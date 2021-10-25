package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
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

public class JDBCOrderRepositoryImpl
        extends JDBCAbstractRepositoryImpl<Order>
        implements OrderRepository {
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
    private static final String DELETE_ALL_DISHES_BY_ORDER_ID_QUERY = "DELETE FROM order_dish_link WHERE order_id = ?";

    private static final String SELECT_ORDERS_BY_USER_ID_QUERY = "SELECT * FROM user_order WHERE user_id = ";
    private static final String DELETE_ORDERS_BY_USER_ID_QUERY = "DELETE FROM user_order WHERE user_id = ?";

    private final JDBCUserRepositoryImpl userRepository;

    public JDBCOrderRepositoryImpl(DataSource dataSource) {
        super(dataSource);
        userRepository = new JDBCUserRepositoryImpl(dataSource);
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
                userRepository.findById(resultSet.getLong(USER_ID_COLUMN))
        );
    }

    protected void fillPreparedStatement(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setLong(1, order.getPrice());
        preparedStatement.setDate(2, Date.valueOf(order.getDate()));
        preparedStatement.setString(3, order.getAddress());
        preparedStatement.setString(4, order.getOrderStatus().toString());
        preparedStatement.setLong(5, order.getUser().getId());
    }

    @Override
    public boolean orderDish(Order order, Long dishId) {
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DISH_TO_ORDER)) {
            preparedStatement.setLong(1, order.getId());
            preparedStatement.setLong(2, dishId);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDishesByOrderId(Long orderId) {
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ALL_DISHES_BY_ORDER_ID_QUERY)) {
            preparedStatement.setLong(1, orderId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = getDataSource().getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ORDERS_BY_USER_ID_QUERY + userId)) {
            while (resultSet.next()) {
                orders.add(construct(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean deleteOrdersByUserId(Long userId) {
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ORDERS_BY_USER_ID_QUERY)) {
            preparedStatement.setLong(1, userId);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}