package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Role;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class JDBCRoleRepositoryImpl extends JDBCAbstractRepositoryImpl<Role> {
    private static final String NAME_COLUMN = "role_name";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM user_role WHERE id = %d";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM user_role";
    private static final String INSERT_QUERY = "INSERT INTO user_role(role_name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE user_role SET role_name = ? WHERE id = %d";
    private static final String DELETE_QUERY = "DELETE FROM user_role WHERE id = %d";

    public JDBCRoleRepositoryImpl(DataSource dataSource) {
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
    protected void fillPreparedStatement(PreparedStatement preparedStatement, Role entity) throws SQLException {
        preparedStatement.setString(1, entity.getRoleName());
    }

    protected Role construct(ResultSet resultSet) throws SQLException {
        return new Role(
                resultSet.getLong(ID_COLUMN),
                resultSet.getString(NAME_COLUMN)
        );
    }
}