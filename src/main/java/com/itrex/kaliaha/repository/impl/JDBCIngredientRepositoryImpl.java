package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.enums.Measurement;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class JDBCIngredientRepositoryImpl extends JDBCAbstractRepositoryImpl<Ingredient> {
    private static final String INGREDIENT_NAME_COLUMN = "ingredient_name";
    private static final String PRICE_COLUMN = "price";
    private static final String REMAINDER_COLUMN = "remainder";
    private static final String MEASUREMENT_COLUMN = "measurement";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM ingredient WHERE id = %d";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM ingredient";
    private static final String INSERT_QUERY = "INSERT INTO ingredient(ingredient_name, price, remainder, measurement) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE ingredient SET ingredient_name=?, price=?, remainder=?, measurement=? WHERE id = %d";
    private static final String DELETE_QUERY = "DELETE FROM ingredient WHERE id = %d";

    public JDBCIngredientRepositoryImpl(DataSource dataSource) {
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

    protected Ingredient construct(ResultSet resultSet) throws SQLException {
        return new Ingredient(
                resultSet.getLong(ID_COLUMN),
                resultSet.getString(INGREDIENT_NAME_COLUMN),
                resultSet.getInt(PRICE_COLUMN),
                resultSet.getInt(REMAINDER_COLUMN),
                Measurement.valueOf(resultSet.getString(MEASUREMENT_COLUMN))
        );
    }

    protected void fillPreparedStatement(PreparedStatement preparedStatement, Ingredient ingredient) throws SQLException {
        preparedStatement.setString(1, ingredient.getIngredientName());
        preparedStatement.setInt(2, ingredient.getPrice());
        preparedStatement.setInt(3, ingredient.getRemainder());
        preparedStatement.setString(4, ingredient.getMeasurement().toString());
    }
}
