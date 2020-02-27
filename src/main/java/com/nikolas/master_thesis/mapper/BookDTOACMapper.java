package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.BookDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
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

        long authorId = rs.getLong("aut_id");
        if (authorId > 0) {
            authorIds.add(authorId);
        }
        long categoryId = rs.getLong("cat_id");
        if (categoryId > 0) {
            categoryIds.add(categoryId);
        }
        LOGGER.info("bookId = " + bookId + ", title = '" + title + "'" + ", price = " + price + ", amount = " + amount
                + ", is_deleted: " + is_deleted + ", authorId = " + authorId
                + ", categoryId = " + categoryId);

        /* TODO: try with getRow() and than access ONLY that retrieved row in while-loop
            ...or might better use suggestion from: https://stackoverflow.com/questions/54202685/mysql-one-to-many-join-mapping-join-with-jdbi-to-list
            ...or even this one inside of BookDAO in default method https://jdbi.org/#_resultbearing_reducerows
            ... TRY THIS: iterate with rs.next in condition of while-loop and nest whole body statetments in IF-statement with condition 'rs.getLong("b_id") == bookId' and
            BREAK in ELSE-statement
        */

        while (rs.next()) {
            if (rs.getLong("b_id") != bookId) {
                break;
            } else {
                authorId = rs.getLong("aut_id");
                if (authorId > 0) {
                    authorIds.add(authorId);
                }

                categoryId = rs.getLong("cat_id");
                if (categoryId > 0) {
                    categoryIds.add(categoryId);
                }

//                LOGGER.info("(inside of while-loop) bookId = " + bookId + ", title = '" + title + "'" + ", price = " + price + ", amount = " + amount
//                        + ", is_deleted: " + is_deleted + ", authorIds = " + authorId
//                        + ", categoryIds = " + categoryId);
            }
        }
        final List<Long> authorIdsList = new ArrayList<>(authorIds);
        final List<Long> categoryIdsList = new ArrayList<>(categoryIds);

        return new BookDTO(bookId, title, price, amount, is_deleted, authorIdsList, categoryIdsList);
    }

}
