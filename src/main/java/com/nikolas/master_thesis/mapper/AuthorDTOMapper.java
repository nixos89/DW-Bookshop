package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.AuthorDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDTOMapper implements RowMapper<AuthorDTO> {

    @Override
    public AuthorDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new AuthorDTO(rs.getLong("author_id"), rs.getString("first_name"), rs.getString("last_name") );
    }
}
