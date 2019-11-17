package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.core.UserA;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAMapper implements RowMapper<UserA> {

    @Override
    public UserA map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new UserA(rs.getInt("id"), rs.getString("name"));
    }
}
