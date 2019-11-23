package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.BookDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookDTOMapper implements RowMapper<BookDTO> {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookDTOMapper.class);

    @Override
    public BookDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
//        String msg = String.format("====  map values: %d, %s, %.2f, %d, %b ====", rs.getLong("book_id"), rs.getString("title"),
//                rs.getDouble("price"), rs.getInt("amount"), rs.getBoolean("is_deleted"));
//        LOGGER.info("BookDTOMapper map(..) OLD BookDTO: " + msg);

        // TODO: implement using while(rs.next()) {..} from: https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
        System.out.println(" ======== rs.getLong(\"author_ids\") " + rs.getLong("author_id") + " ======= ");

        Long bookId = rs.getLong("book_id");
        String title = rs.getString("title");
        System.out.println(" ===== title: " + title + " ===== ");
        double price = rs.getDouble("price");
        int amount = rs.getInt("amount");
        boolean is_deleted = rs.getBoolean("is_deleted");
        Long authorId = rs.getLong("author_id");

        Set<Long> authorIds = new HashSet<>();
        Set<Long> categoryIds = new HashSet<>();
        if (authorId > 0) {
            authorIds.add(authorId);
        }

        Long categoryId = rs.getLong("category_id");
        if (categoryId > 0) {
            categoryIds.add(categoryId);
        }

//        boolean hasNext = rs.next();
//        while (hasNext && (rs.getLong("book_id") == bookId)) {
//            if (rs.getLong("author_id") > 0) {
//                authorIds.add(rs.getLong("author_id"));
//            }
//
//            if (rs.getLong("category_id") > 0) {
//                categoryIds.add(rs.getLong("category_id"));
//            }
//            hasNext = rs.next();
//        }
        System.out.println(" +++++++++++ authorsIds: " + authorIds + " +++++++++++ ");
        System.out.println(" +++++++++++ categoryIds: " + categoryIds + " +++++++++++ ");

        return new BookDTO(bookId, title,
                price, amount, is_deleted, new ArrayList<>(authorIds), new ArrayList<>(categoryIds));
    }
}
