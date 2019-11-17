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
        String msg = String.format("====  map values: %d, %s, %.4f, %d, %b ====",rs.getLong("book_id"), rs.getString("title"),
                rs.getDouble("price"), rs.getInt("amount"), rs.getBoolean("is_deleted"));
        LOGGER.info(msg);
        return new BookDTO(rs.getLong("book_id"), rs.getString("title"),
                rs.getDouble("price"), rs.getInt("amount"), rs.getBoolean("is_deleted"));
    }
}
