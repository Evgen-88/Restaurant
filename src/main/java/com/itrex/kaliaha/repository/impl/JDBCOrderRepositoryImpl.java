package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.util.OrderStatus;
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

public class JDBCOrderRepositoryImpl implements OrderRepository {
    private static final String ID_COLUMN = "id";
    private static final String PRICE_COLUMN = "price";
    private static final String DATE_COLUMN = "date";
    private static final String ADDRESS_COLUMN = "address";
    private static final String ORDER_STATUS_COLUMN = "order_status";
    private static final String USER_ID_COLUMN = "user_id";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM `order`";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM `order` WHERE id = ";
    private static final String INSERT_QUERY = "INSERT INTO `order`(price, date, address, order_status, user_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE `order` SET price=?, date=?, address=?, order_status=?  WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM `order` WHERE id = ?";

    private static final String INSERT_DISH_TO_ORDER = "INSERT INTO order_dish_link(order_id, dish_id) VALUES (?, ?)";
    private static final String DELETE_DISH_BY_ORDER_ID_QUERY = "DELETE FROM order_dish_link WHERE order_id = ?";

    private static final String SELECT_ORDERS_BY_USER_ID_QUERY = "SELECT * FROM `order` WHERE user_id = ";
    private static final String DELETE_ORDERS_BY_USER_ID_QUERY = "DELETE FROM `order` WHERE user_id = ?";

    private final DataSource dataSource;
    private final JDBCUserRepositoryImpl userRepository;

    public JDBCOrderRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        userRepository = new JDBCUserRepositoryImpl(dataSource);
    }

    @Override
    public Order selectById(Long id) {
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                return constructOrder(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Order();
    }

    private Order constructOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong(ID_COLUMN));
        order.setPrice(resultSet.getInt(PRICE_COLUMN));
        order.setDate(resultSet.getDate(DATE_COLUMN).toLocalDate());
        order.setAddress(resultSet.getString(ADDRESS_COLUMN));
        order.setOrderStatus(OrderStatus.valueOf(resultSet.getString(ORDER_STATUS_COLUMN)));
        order.setUser(userRepository.selectById(resultSet.getLong(USER_ID_COLUMN)));
        return order;
    }

    @Override
    public List<Order> selectAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                orders.add(constructOrder(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    @Override
    public void add(Order order) {
        try (Connection con = dataSource.getConnection()) {
            insert(con, order);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insert(Connection con, Order order) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(preparedStatement, order);
            final int effectiveRows = preparedStatement.executeUpdate();
            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setLong(1, order.getPrice());
        preparedStatement.setDate(2, Date.valueOf(order.getDate()));
        preparedStatement.setString(3, order.getAddress());
        preparedStatement.setString(4, order.getOrderStatus().toString());
        preparedStatement.setLong(5, order.getUser().getId());
    }

    @Override
    public void addAll(List<Order> orders) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Order order : orders) {
                    insert(con, order);
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
    public boolean update(Order order) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            fillPreparedStatement(preparedStatement, order);
            preparedStatement.setLong(5, order.getId());
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
    public boolean orderDish(Order order, Long dishId) {
        try (Connection conn = dataSource.getConnection();
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
    public boolean removeDishesByOrderId(Long orderId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_DISH_BY_ORDER_ID_QUERY)) {
            preparedStatement.setLong(1, orderId);
            return  preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ORDERS_BY_USER_ID_QUERY + userId)) {
            while (resultSet.next()) {
                orders.add(constructOrder(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean removeOrdersByUserId(Long userId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ORDERS_BY_USER_ID_QUERY)) {
            preparedStatement.setLong(1, userId);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}