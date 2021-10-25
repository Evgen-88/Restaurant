package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.enums.Group;
import com.itrex.kaliaha.repository.DishRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JDBCDishRepositoryImpl
        extends JDBCAbstractRepositoryImpl<Dish>
        implements DishRepository {
    public static final String DISH_NAME_COLUMN = "dish_name";
    public static final String PRICE_COLUMN = "price";
    public static final String GROUP_COLUMN = "dish_group";
    public static final String DESCRIPTION_COLUMN = "dish_description";
    public static final String IMAGE_PATH_COLUMN = "image_path";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM dish WHERE id = %d";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM dish";
    private static final String INSERT_QUERY = "INSERT INTO dish(dish_name, price, dish_group, dish_description, image_path) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE dish SET dish_name=?, price=?, dish_group=?, dish_description=?, image_path=?  WHERE id = %d";
    private static final String DELETE_QUERY = "DELETE FROM dish WHERE id = %d";

    private static final String SELECT_ALL_DISHES_IN_ORDER_QUERY =
            "SELECT d.id, d.DISH_NAME, d.PRICE, d.DISH_GROUP, d.DISH_DESCRIPTION, d.IMAGE_PATH " +
                    "FROM dish d LEFT JOIN order_dish_link odl ON d.id = odl.dish_id " +
                    "WHERE odl.order_id = %d";

    public JDBCDishRepositoryImpl(DataSource dataSource) {
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
    protected Dish construct(ResultSet resultSet) throws SQLException {
        return new Dish(
                resultSet.getLong(ID_COLUMN),
                resultSet.getString(DISH_NAME_COLUMN),
                resultSet.getInt(PRICE_COLUMN),
                Group.valueOf(resultSet.getString(GROUP_COLUMN)),
                resultSet.getString(DESCRIPTION_COLUMN),
                resultSet.getString(IMAGE_PATH_COLUMN)
        );
    }

    protected void fillPreparedStatement(PreparedStatement preparedStatement, Dish dish) throws SQLException {
        preparedStatement.setString(1, dish.getDishName());
        preparedStatement.setInt(2, dish.getPrice());
        preparedStatement.setString(3, dish.getGroup().toString());
        preparedStatement.setString(4, dish.getDescription());
        preparedStatement.setString(5, dish.getImagePath());
    }

    @Override
    public List<Dish> findAllDishesInOrderById(Long orderId) {
        List<Dish> dishes = new ArrayList<>();
        try (Connection conn = getDataSource().getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(String.format(SELECT_ALL_DISHES_IN_ORDER_QUERY, orderId))) {
            while (resultSet.next()) {
                dishes.add(construct(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dishes;
    }
}