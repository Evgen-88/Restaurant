package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.util.Group;
import com.itrex.kaliaha.repository.DishRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JDBCDishRepositoryImpl implements DishRepository {
    public static final String ID_COLUMN = "id";
    public static final String DISH_NAME_COLUMN = "dish_name";
    public static final String PRICE_COLUMN = "price";
    public static final String GROUP_COLUMN = "group";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String IMAGE_PATH_COLUMN = "image_path";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM dish";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM dish WHERE id = ";
    private static final String INSERT_QUERY = "INSERT INTO dish(dish_name, price, `group`, description, image_path) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE dish SET dish_name=?, price=?, `group`=?, description=?, image_path=?  WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM dish WHERE id = ?";

    private static final String SELECT_ALL_DISHES_IN_ORDER_QUERY  = "SELECT * FROM dish LEFT JOIN order_dish_link on dish.id = dish_id WHERE order_id = ";

    private final DataSource dataSource;

    public JDBCDishRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dish selectById(Long id) {
        Dish dish;
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {

            if (resultSet.next()) {
                dish = constructDish(resultSet);
                return dish;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Dish();
    }

    private Dish constructDish(ResultSet resultSet) throws SQLException {
        Dish dish = new Dish();
        dish.setId(resultSet.getLong(ID_COLUMN));
        dish.setDishName(resultSet.getString(DISH_NAME_COLUMN));
        dish.setPrice(resultSet.getInt(PRICE_COLUMN));
        dish.setGroup(Group.valueOf(resultSet.getString(GROUP_COLUMN)));
        dish.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
        dish.setImagePath(resultSet.getString(IMAGE_PATH_COLUMN));
        return dish;
    }

    @Override
    public List<Dish> selectAll() {
        List<Dish> dishes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                dishes.add(constructDish(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dishes;
    }

    @Override
    public void add(Dish dish) {
        try (Connection con = dataSource.getConnection()) {
            insert(con, dish);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insert(Connection con, Dish dish) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(preparedStatement, dish);
            final int effectiveRows = preparedStatement.executeUpdate();
            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dish.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Dish dish) throws SQLException {
        preparedStatement.setString(1, dish.getDishName());
        preparedStatement.setInt(2, dish.getPrice());
        preparedStatement.setString(3, dish.getGroup().toString());
        preparedStatement.setString(4, dish.getDescription());
        preparedStatement.setString(5, dish.getImagePath());
    }

    @Override
    public void addAll(List<Dish> dishes) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Dish dish : dishes) {
                    insert(con, dish);
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
    public boolean update(Dish dish) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            fillPreparedStatement(preparedStatement, dish);

            preparedStatement.setLong(6, dish.getId());

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
    public List<Dish> findAllDishesInOrderById(Long orderId) {
        List<Dish> dishes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_DISHES_IN_ORDER_QUERY + orderId)) {
            while (resultSet.next()) {
                dishes.add(constructDish(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dishes;
    }
}