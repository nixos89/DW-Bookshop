package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.examples.UserADTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserADTOMapper implements RowMapper<UserADTO> {

    @Override
    public UserADTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new UserADTO(rs.getInt("id"), rs.getString("name"), rs.getInt("account_id"));
    }
}
