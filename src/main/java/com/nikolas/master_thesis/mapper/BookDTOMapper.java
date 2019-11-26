package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.BookDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDTOMapper implements RowMapper<BookDTO> {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookDTOMapper.class);


    @Override
    public BookDTO map(ResultSet rs, StatementContext ctx) throws SQLException {

        long bookId = rs.getLong("book_id");
        String title = rs.getString("title");
        double price = rs.getDouble("price");
        int amount = rs.getInt("amount");
        boolean is_deleted = rs.getBoolean("is_deleted");

        return new BookDTO(bookId, title, price, amount, is_deleted);
    }
}
