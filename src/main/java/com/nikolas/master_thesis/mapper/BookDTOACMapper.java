package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.BookDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class BookDTOACMapper implements RowMapper<BookDTO> {

    @Override
    public BookDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        final long bookId = rs.getLong("b_id");
        String title = rs.getString("title");
        double price = rs.getDouble("price");
        int amount = rs.getInt("amount");
        boolean is_deleted = rs.getBoolean("is_deleted");

        Set<Long> authorIds = new HashSet<>();
        Set<Long> categoryIds = new HashSet<>();

        Long[] autIds = (Long[]) (rs.getArray("aut_ids").getArray());
        Long[] catIds = (Long[]) (rs.getArray("cat_ids").getArray());

        Collections.addAll(authorIds, autIds);
        Collections.addAll(categoryIds, catIds);

        final List<Long> authorIdsList = new ArrayList<>(authorIds);
        final List<Long> categoryIdsList = new ArrayList<>(categoryIds);

        return new BookDTO(bookId, title, price, amount, is_deleted, authorIdsList, categoryIdsList);
    }

}
