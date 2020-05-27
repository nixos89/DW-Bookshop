package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.AddUpdateBookDTO;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.mapper.BookMapper;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface BookDAO extends SqlObject {

    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Book( book_id BIGSERIAL PRIMARY KEY, title VARCHAR(255), price double precision, amount INTEGER, is_deleted boolean)")
    void createBookTable();

    @UseRowMapper(BookMapper.class)
    @SqlQuery("SELECT b.book_id, b.title, b.price, b.amount, b.is_deleted FROM book b WHERE b.book_id = :bId")
    Book getBookById(@Bind("bId") Long bookId);

    @UseRowMapper(BookMapper.class)
    @SqlQuery("SELECT b.book_id, b.title, b.price, b.amount, b.is_deleted FROM book b ORDER BY b.book_id")
    List<Book> getAllBooks();

    @UseRowMapper(BookMapper.class)
    @SqlQuery("SELECT b.book_id, b.title, b.price, b.amount, b.is_deleted FROM book b " +
            "LEFT JOIN author_book AS ab ON b.book_id = ab.book_id " +
            "WHERE ab.author_id = :authorId " +
            "ORDER BY b.book_id")
    List<Book> getAllBooksByAuthorId(@Bind("authorId") Long authorId);

    @GetGeneratedKeys
    @UseRowMapper(BookMapper.class)
    @SqlUpdate("INSERT INTO Book(title, price, amount, is_deleted) VALUES(:title, :price, :amount, :is_deleted)")
    Book createBook(@Bind("title") String title, @Bind("price") double price, @Bind("amount") int amount, @Bind("is_deleted") boolean isDeleted);

    @SqlUpdate("INSERT INTO author_book(author_id, book_id) VALUES (?, ?)")
    void insertAuthorBook(Long authorId, Long bookId);

    @SqlUpdate("INSERT INTO category_book(category_id, book_id) VALUES (?, ?)")
    void insertCategoryBook(Long categoryId, Long bookId);

    @SqlUpdate("DELETE FROM author_book WHERE author_id = ? AND book_id = ?")
    void deleteAuthorBook(Long authorId, Long bookId);

    @SqlUpdate("DELETE FROM category_book WHERE category_book.category_id = ? AND category_book.book_id = ?")
    void deleteCategoryBook(Long categoryId, Long bookId);

    @UseRowMapper(BookMapper.class)
    @SqlUpdate("UPDATE book SET title = :title, price = :price, amount = :amount, is_deleted = :isDeleted WHERE book_id = :bookId")
    boolean updateBookDTO(@Bind("bookId") Long bookId, @Bind("title") String title, @Bind("price") double price, @Bind("amount") int amount, @Bind("isDeleted") boolean isDeleted);


    default void iterateAuthorBook(List<Long> existingAuthorIds, AddUpdateBookDTO bookDTOToSave, Long bookId) {
        List<Long> toBeSavedAuthorIds = bookDTOToSave.getAuthors();
        for (Long existAuthorId : existingAuthorIds) {
            if (!toBeSavedAuthorIds.contains(existAuthorId)) {
                deleteAuthorBook(existAuthorId, bookId);
            }
        }
        for (Long toSaveAId : toBeSavedAuthorIds) {
            if (!existingAuthorIds.contains(toSaveAId)) {
                insertAuthorBook(toSaveAId, bookId);
            }
        }
    }


    default void iterateCategoryBook(List<Long> existingCategoryIds, AddUpdateBookDTO bookDTOToSave, Long bookId) {
        List<Long> toBeSavedCategoryIds = bookDTOToSave.getCategories();
        for (Long eCatId : existingCategoryIds) {
            if (!toBeSavedCategoryIds.contains(eCatId)) {
                deleteCategoryBook(eCatId, bookId);
            }
        }

        for (Long tbSavedId : toBeSavedCategoryIds) {
            if (!existingCategoryIds.contains(tbSavedId)) {
                insertCategoryBook(tbSavedId, bookId);
            }
        }
    }

    @SqlUpdate("DELETE FROM Book WHERE Book.book_id = ?")
    boolean deleteBook(Long bookId);

}
