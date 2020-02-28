package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.BookDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class BookDTOACMapper implements RowMapper<BookDTO> {

    public static final Logger LOGGER = LoggerFactory.getLogger(BookDTOACMapper.class);

    @Override
    public BookDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        LOGGER.info("rs.getStatement().toString() = " + rs.getStatement().toString());
        final long bookId = rs.getLong("b_id");
        String title = rs.getString("title");
        double price = rs.getDouble("price");
        int amount = rs.getInt("amount");
        boolean is_deleted = rs.getBoolean("is_deleted");

        Set<Long> authorIds = new HashSet<>();
        Set<Long> categoryIds = new HashSet<>();

        Array autIdsArray = rs.getArray("aut_ids");
        LOGGER.info("For bookId = " + bookId+ " authorIdsString: " + autIdsArray);
        Long[] autIds = (Long[]) (rs.getArray("aut_ids").getArray());
        LOGGER.info("For bookId = " + bookId+ " autIds: " + Arrays.toString(autIds));
        Collections.addAll(authorIds, autIds);

        Long[] catIds = (Long[]) (rs.getArray("cat_ids").getArray());
        LOGGER.info("For bookId = " + bookId+ " catIds: " + Arrays.toString(catIds));
        Collections.addAll(categoryIds, catIds);

//        LOGGER.info("bookId = " + bookId + ", title = '" + title + "'" + ", price = " + price + ", amount = " + amount
//                + ", is_deleted: " + is_deleted + ", authorId = " + authorId + ", categoryId = " + categoryId);
        final List<Long> authorIdsList = new ArrayList<>(authorIds);
        final List<Long> categoryIdsList = new ArrayList<>(categoryIds);

        return new BookDTO(bookId, title, price, amount, is_deleted, authorIdsList, categoryIdsList);
    }

}
