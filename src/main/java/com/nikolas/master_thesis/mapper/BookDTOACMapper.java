package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.BookDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookDTOACMapper implements RowMapper<BookDTO> {

    @Override
    public BookDTO map(ResultSet rs, StatementContext ctx) throws SQLException {

        long bookId = rs.getLong("book_id");
        String title = rs.getString("title");
        double price = rs.getDouble("price");
        int amount = rs.getInt("amount");
        boolean is_deleted = rs.getBoolean("is_deleted");

        Set<Long> authorIds = new HashSet<>();
        Set<Long> categoryIds = new HashSet<>();

        Long authorId = rs.getLong("author_id");
        if (authorId > 0) {
            authorIds.add(authorId);
        }
        Long categoryId = rs.getLong("category_id");
        if (categoryId > 0) {
            categoryIds.add(categoryId);
        }

        boolean hasNext = rs.next();
        while (hasNext && (rs.getLong("book_id") == bookId)) {
            if (rs.getLong("author_id") > 0) {
                authorIds.add(rs.getLong("author_id"));
            }

            if (rs.getLong("category_id") > 0) {
                categoryIds.add(rs.getLong("category_id"));
            }
            hasNext = rs.next();
        }

        return new BookDTO(bookId, title, price, amount, is_deleted, new ArrayList<>(authorIds), new ArrayList<>(categoryIds));
    }

}
