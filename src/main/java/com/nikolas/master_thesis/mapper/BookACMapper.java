package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.Category;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class BookACMapper implements RowMapper<Book> {

    private Logger LOGGER = LoggerFactory.getLogger(BookACMapper.class);

    @Override
    public Book map(ResultSet rs, StatementContext ctx) throws SQLException {
        final long bookId = rs.getLong("b_id");
        String title = rs.getString("title");
        double price = rs.getDouble("price");
        int amount = rs.getInt("amount");
        boolean is_deleted = rs.getBoolean("is_deleted");

        // ...ORR Lists
        Set<Author> authors = new LinkedHashSet<>();
        Set<Category> categories = new LinkedHashSet<>();

        Object authorArr[] = (Object[]) (rs.getArray("authors")).getArray();
        Object categoryArr[] = (Object[]) (rs.getArray("categories")).getArray();

        for (Object aut : authorArr) {
            LOGGER.info("aut.toString: " + aut.toString());
            Author author = new Author();
            authors.add(author);
        }


        return new Book(bookId, title, price, amount, is_deleted, authors, categories); // impele
    }
}
