package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class JDBCCompositionRepositoryImpl extends JDBCAbstractRepositoryImpl<Composition> {
    private static final String DISH_ID_COLUMN = "dish_id";
    private static final String INGREDIENT_ID_COLUMN = "ingredient_id";
    private static final String QUANTITY_COLUMN = "quantity";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM composition WHERE id = %d";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM composition ";
    private static final String INSERT_QUERY = "INSERT INTO composition(dish_id, ingredient_id, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE composition SET dish_id=?, ingredient_id=?, quantity=? WHERE id = %d";
    private static final String DELETE_QUERY = "DELETE FROM composition WHERE id = %d";

    public JDBCCompositionRepositoryImpl(DataSource dataSource) {
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

    protected Composition construct(ResultSet resultSet) throws SQLException {
        return new Composition(
                resultSet.getLong(ID_COLUMN),
                resultSet.getLong(DISH_ID_COLUMN),
                resultSet.getLong(INGREDIENT_ID_COLUMN),
                resultSet.getInt(QUANTITY_COLUMN)
        );
    }

    protected void fillPreparedStatement(PreparedStatement preparedStatement, Composition composition) throws SQLException {
        preparedStatement.setLong(1, composition.getDishId());
        preparedStatement.setLong(2, composition.getIngredientId());
        preparedStatement.setInt(3, composition.getQuantity());
    }
}