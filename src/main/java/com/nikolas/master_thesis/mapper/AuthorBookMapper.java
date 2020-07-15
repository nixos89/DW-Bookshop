package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.core.AuthorBook;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorBookMapper implements RowMapper<AuthorBook> {
    @Override
    public AuthorBook map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new AuthorBook(rs.getLong("author_id"), rs.getLong("book_id"));
    }
}
