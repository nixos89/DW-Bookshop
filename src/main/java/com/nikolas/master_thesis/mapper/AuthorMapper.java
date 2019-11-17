package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.core.Author;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author map(ResultSet rs, StatementContext ctx) throws SQLException {
        Author author = new Author();
        return null;
    }
}
