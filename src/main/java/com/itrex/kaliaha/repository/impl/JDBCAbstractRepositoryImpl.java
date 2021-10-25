package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.repository.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public abstract class JDBCAbstractRepositoryImpl<Entity extends BaseEntity<Long>>
        implements Repository<Entity> {

    public static final String ID_COLUMN = "id";

    private final DataSource dataSource;

    public JDBCAbstractRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    protected abstract String defineSelectByIdQuery();

    protected abstract String defineSelectAllQuery();

    protected abstract String defineInsertQuery();

    protected abstract String defineUpdateQuery();

    protected abstract String defineDeleteQuery();

    @Override
    public Entity findById(Long id) {
        Entity entity;
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(String.format(defineSelectByIdQuery(), id))) {
            if (resultSet.next()) {
                entity = construct(resultSet);
                return entity;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected abstract Entity construct(ResultSet resultSet) throws SQLException;

    @Override
    public List<Entity> findAll() {
        List<Entity> entities = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(defineSelectAllQuery())) {
            while (resultSet.next()) {
                entities.add(construct(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entities;
    }

    @Override
    public void add(Entity entity) {
        try (Connection con = dataSource.getConnection()) {
            insert(con, entity);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insert(Connection con, Entity entity) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(defineInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(preparedStatement, entity);
            if (preparedStatement.executeUpdate() == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    protected abstract void fillPreparedStatement(PreparedStatement preparedStatement, Entity entity) throws SQLException;

    @Override
    public void addAll(List<Entity> entities) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Entity entity : entities) {
                    insert(con, entity);
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
    public boolean update(Entity entity) {
        String query = String.format(defineUpdateQuery(), entity.getId());
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            fillPreparedStatement(preparedStatement, entity);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String query = String.format(defineDeleteQuery(), id);
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return preparedStatement.executeUpdate() >= 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}