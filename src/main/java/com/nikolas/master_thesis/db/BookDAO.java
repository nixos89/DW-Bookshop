package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.mapper.BookDTOMapper;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.UseTemplateEngine;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.*;
import org.jdbi.v3.stringtemplate4.UseStringTemplateEngine;

import java.util.List;

public interface BookDAO {

    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Book( book_id BIGSERIAL PRIMARY KEY, title VARCHAR(255), price double precision, amount INTEGER, is_deleted boolean)")
    void createBookTable();

    // TODO: implement inserting into book and Author_Book using WITH statement. Sources:
    //  1) https://dba.stackexchange.com/questions/218234/how-to-perform-multiple-inserts-into-postgresql-database-in-one-query
    //  2) https://www.postgresql.org/docs/11/queries-with.html
    //  3) http://jdbi.org/#__sqlbatch
    @UseRowMapper(BookDTOMapper.class)
    @SqlBatch("WITH new_book as ( " +
                "INSERT INTO book(title, price, amount, is_deleted) VALUES(:title, :price, :amount, :is_deleted)" +
            " returning book_id, title, price, amount, is_deleted ), new_category_book AS ( " +
                "INSERT INTO category_book(category_id, book_id) VALUES(:categoryIds, (SELECT book_id FROM new_book)) returning category_id ) " +
            "INSERT INTO author_book (author_id, book_id) VALUES(:authorIds, (SELECT book_id FROM new_book))")
    @GetGeneratedKeys
    int[] createBook(@Bind("title") String title, @Bind("price")double price, @Bind("amount")int amount, @Bind("is_deleted") boolean is_deleted,
                       @Bind("authorIds")List<Long> authorIds, @Bind("categoryIds")List<Long> categoryIds);

    @UseRowMapper(BookDTOMapper.class)
    @SqlQuery("SELECT book.book_id, book.title, book.price, book.amount, book.is_deleted, author.author_id, category.category_id FROM book " +
            "LEFT JOIN author_book ON book.book_id = author_book.book_id " +
            "LEFT JOIN author ON author_book.author_id = author.author_id " +
            "LEFT JOIN category_book ON book.book_id = category_book.book_id " +
            "LEFT JOIN category ON category_book.category_id = category.category_id")
    List<BookDTO> getAllBooks();

    @UseRowMapper(BookDTOMapper.class)
    @SqlQuery("SELECT book.book_id, book.title, book.price, book.amount, book.is_deleted, author.author_id, category.category_id FROM book " +
            "LEFT JOIN author_book ON book.book_id = author_book.book_id " +
            "LEFT JOIN author ON author_book.author_id = author.author_id " +
            "LEFT JOIN category_book ON book.book_id = category_book.book_id " +
            "LEFT JOIN category ON category_book.category_id = category.category_id " +
            "WHERE book.book_id = :id")
    BookDTO getBookById(@Bind("id") Long book_id);

    @SqlUpdate("DELETE FROM Book WHERE Book.book_id = ?")
    boolean deleteBook(Long bookId);

    // TODO: Also UPDATE author_ids and category_ids!!!
    @SqlUpdate(" WITH updated_book AS (" +
                    "UPDATE book SET title = :title, price = :price, amount = :amount, is_deleted = :is_deleted WHERE book_id = :book_id " +
                "returning book_id), updated_book_category AS (" +
                    "UPDATE book_category SET category_id = :category_ids WHERE book_id = :book_id) " +
                "UPDATE book_author(book_id, author_id) SET author_id = :authorIds WHERE book_id = :book_id")
    boolean updateBook(@Bind("book_id")Long bookId, @Bind("title") String title, @Bind("price") double price,
                       @Bind("amount")int amount, @Bind("is_deleted") boolean is_deleted,
                       @Bind("authorIds")List<Long> authorIds, @Bind("categoryIds")List<Long> categoryIds);
}
