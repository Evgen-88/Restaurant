package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.repository.CompositionRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JDBCCompositionRepositoryImpl implements CompositionRepository {
    private static final String ID_COLUMN = "id";
    private static final String DISH_ID_COLUMN = "dish_id";
    private static final String INGREDIENT_ID_COLUMN = "ingredient_id";
    private static final String QUANTITY_COLUMN = "quantity";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM composition WHERE id = ";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM composition ";
    private static final String INSERT_QUERY = "INSERT INTO composition(dish_id, ingredient_id, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE composition SET dish_id=?, ingredient_id=?, quantity=? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM composition WHERE id = ?";

    private final DataSource dataSource;

    public JDBCCompositionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Composition selectById(Long id) {
        Composition composition;
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                composition = constructComposition(resultSet);
                return composition;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Composition();
    }

    private Composition constructComposition(ResultSet resultSet) throws SQLException {
        Composition composition = new Composition();
        composition.setId(resultSet.getLong(ID_COLUMN));
        composition.setDishId(resultSet.getLong(DISH_ID_COLUMN));
        composition.setIngredientId(resultSet.getLong(INGREDIENT_ID_COLUMN));
        composition.setQuantity(resultSet.getInt(QUANTITY_COLUMN));
        return composition;
    }

    @Override
    public List<Composition> selectAll() {
        List<Composition> compositions = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                compositions.add(constructComposition(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return compositions;
    }

    @Override
    public void add(Composition composition) {
        try (Connection con = dataSource.getConnection()) {
            insert(con, composition);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insert(Connection con, Composition composition) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(preparedStatement, composition);
            if (preparedStatement.executeUpdate() == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        composition.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Composition composition) throws SQLException {
        preparedStatement.setLong(1, composition.getDishId());
        preparedStatement.setLong(2, composition.getIngredientId());
        preparedStatement.setInt(3, composition.getQuantity());
    }

    @Override
    public void addAll(List<Composition> compositions) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Composition composition : compositions) {
                    insert(con, composition);
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
    public boolean update(Composition composition) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            fillPreparedStatement(preparedStatement, composition);
            preparedStatement.setLong(4, composition.getId());
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