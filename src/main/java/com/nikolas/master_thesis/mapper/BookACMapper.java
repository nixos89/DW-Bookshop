package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.core.Book;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookACMapper implements RowMapper<Book> {

    @Override
    public Book map(ResultSet rs, StatementContext ctx) throws SQLException {
        final long bookId = rs.getLong("b_id");
        String title = rs.getString("title");
        double price = rs.getDouble("price");
        int amount = rs.getInt("amount");
        boolean is_deleted = rs.getBoolean("is_deleted");

        return new Book(bookId, title, price, amount, is_deleted); // impele
    }
}