package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.entity.util.Measurement;
import com.itrex.kaliaha.repository.IngredientRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JDBCIngredientRepositoryImpl implements IngredientRepository {
    private static final String ID_COLUMN = "id";
    private static final String INGREDIENT_NAME_COLUMN = "ingredient_name";
    private static final String PRICE_COLUMN = "price";
    private static final String REMAINDER_COLUMN = "remainder";
    private static final String MEASUREMENT_COLUMN = "measurement";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM ingredient";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM ingredient WHERE id = ";
    private static final String INSERT_QUERY = "INSERT INTO ingredient(ingredient_name, price, remainder, measurement) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE ingredient SET ingredient_name=?, price=?, remainder=?, measurement=? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM ingredient WHERE id = ?";

    private final DataSource dataSource;

    public JDBCIngredientRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Ingredient selectById(Long id) {
        Ingredient ingredient = new Ingredient();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                constructIngredient(resultSet, ingredient);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ingredient;
    }

    private Ingredient constructIngredient(ResultSet resultSet, Ingredient ingredient) throws SQLException {
        ingredient.setId(resultSet.getLong(ID_COLUMN));
        ingredient.setIngredientName(resultSet.getString(INGREDIENT_NAME_COLUMN));
        ingredient.setPrice(resultSet.getInt(PRICE_COLUMN));
        ingredient.setRemainder(resultSet.getInt(REMAINDER_COLUMN));
        ingredient.setMeasurement(Measurement.valueOf(resultSet.getString(MEASUREMENT_COLUMN)));
        return ingredient;
    }

    @Override
    public List<Ingredient> selectAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredients.add(constructIngredient(resultSet, ingredient));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ingredients;
    }

    @Override
    public void add(Ingredient ingredient) {
        try (Connection con = dataSource.getConnection()) {
            insert(con, ingredient);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insert(Connection con, Ingredient ingredient) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(preparedStatement, ingredient);
            final int effectiveRows = preparedStatement.executeUpdate();
            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ingredient.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Ingredient ingredient) throws SQLException {
        preparedStatement.setString(1, ingredient.getIngredientName());
        preparedStatement.setInt(2, ingredient.getPrice());
        preparedStatement.setInt(3, ingredient.getRemainder());
        preparedStatement.setString(4, ingredient.getMeasurement().toString());
    }

    @Override
    public void addAll(List<Ingredient> ingredients) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Ingredient ingredient : ingredients) {
                    insert(con, ingredient);
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
    public boolean update(Ingredient ingredient) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            fillPreparedStatement(preparedStatement, ingredient);
            preparedStatement.setLong(5, ingredient.getId());
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
}
