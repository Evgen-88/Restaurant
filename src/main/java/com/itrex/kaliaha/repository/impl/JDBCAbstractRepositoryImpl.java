package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.repository.BaseRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public abstract class JDBCAbstractRepositoryImpl<E extends BaseEntity<Long>>
        implements BaseRepository<E> {

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
    public E findById(Long id) {
        E e;
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(String.format(defineSelectByIdQuery(), id))) {
            if (resultSet.next()) {
                e = construct(resultSet);
                return e;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected abstract E construct(ResultSet resultSet) throws SQLException;

    @Override
    public List<E> findAll() {
        List<E> entities = new ArrayList<>();
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
    public void add(E e) {
        try (Connection con = dataSource.getConnection()) {
            insert(con, e);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void insert(Connection con, E e) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(defineInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(preparedStatement, e);
            if (preparedStatement.executeUpdate() == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        e.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }

    protected abstract void fillPreparedStatement(PreparedStatement preparedStatement, E e) throws SQLException;

    @Override
    public void addAll(List<E> entities) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (E e : entities) {
                    insert(con, e);
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
    public boolean update(E e) {
        String query = String.format(defineUpdateQuery(), e.getId());
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            fillPreparedStatement(preparedStatement, e);
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