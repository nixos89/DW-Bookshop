package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.examples.AccountDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDTOMapper implements RowMapper<AccountDTO> {

    @Override
    public AccountDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new AccountDTO(rs.getInt("id"), rs.getString("name"));
    }
}
