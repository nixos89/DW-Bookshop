package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.AddUpdateBookDTO;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.mapper.BookMapper;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface BookDAO extends SqlObject {

    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Book( book_id BIGSERIAL PRIMARY KEY, title VARCHAR(255), price double precision, amount INTEGER, is_deleted boolean)")
    void createBookTable();

    @SqlUpdate("INSERT INTO author_book(author_id, book_id) VALUES (?, ?)")
    void insertAuthorBook(Long authorId, Long bookId);

    @SqlUpdate("INSERT INTO category_book(category_id, book_id) VALUES (?, ?)")
    void insertCategoryBook(Long categoryId, Long bookId);

    @SqlUpdate("DELETE FROM author_book WHERE author_id = ? AND book_id = ?")
    void deleteAuthorBook(Long authorId, Long bookId);

    @SqlUpdate("DELETE FROM category_book WHERE category_book.category_id = ? AND category_book.book_id = ?")
    void deleteCategoryBook(Long categoryId, Long bookId);

    @UseRowMapper(BookMapper.class)
    @SqlUpdate("UPDATE book SET title = :title, price = :price, amount = :amount, is_deleted = :is_deleted WHERE book_id = :book_id")
    boolean updateBookDTO(@Bind("book_id") Long bookId, @Bind("title") String title, @Bind("price") double price, @Bind("amount") int amount, @Bind("is_deleted") boolean is_deleted);


    // FIXME: put this into Service layer class
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

    // FIXME: put this into Service layer class
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


    /*
    @Transaction
    default BookDTO createBookDefault(BookDTO bookDTOToSave) {
        Handle handle = getHandle();
        BookDTO savedBookDTO = handle.createUpdate("INSERT INTO Book(title, price, amount, is_deleted) VALUES(:title, :price, :amount, :is_deleted)")
                .bind("title", bookDTOToSave.getTitle()).bind("price", bookDTOToSave.getPrice())
                .bind("amount", bookDTOToSave.getAmount()).bind("is_deleted", bookDTOToSave.getIsDeleted())
                .executeAndReturnGeneratedKeys()
                .map((rs, ctx) -> new BookDTO(rs.getLong("book_id"), rs.getString("title"), rs.getDouble("price"),
                        rs.getInt("amount"), rs.getBoolean("is_deleted")
                )).findFirst().orElse(null);

        if (savedBookDTO != null) {
            List<Long> authorIds = new ArrayList<>();
            List<Long> categoryIds = new ArrayList<>();

            for (Long authorId : bookDTOToSave.getAuthors()) {
                authorIds.add(authorId);
                insertAuthorBook(authorId, savedBookDTO.getBookId());
            }
            for (Long categoryId : bookDTOToSave.getCategories()) {
                categoryIds.add(categoryId);
                insertCategoryBook(categoryId, savedBookDTO.getBookId());
            }
            return new BookDTO(savedBookDTO.getBookId(), savedBookDTO.getTitle(), savedBookDTO.getPrice(),
                    savedBookDTO.getAmount(), savedBookDTO.getIsDeleted(),
                    authorIds, categoryIds);
        } else {
            return null;
        }
    }// createBookDefault
*/


    /*
    // FIXME: to speed up querying use ARRAY Constructor instead of ARRAY_AGG Function from this suggestion https://dba.stackexchange.com/a/173879/202258
    @UseRowMapper(BookDTOACMapper.class)
    @SqlQuery("SELECT b.book_id AS b_id, b.title, b.price, b.amount, b.is_deleted, ARRAY_AGG(aut.author_id) as aut_ids, " +
            "ARRAY_AGG(cat.category_id) as cat_ids FROM book b " +
            "LEFT JOIN author_book ON author_book.book_id = b.book_id " +
            "LEFT JOIN author aut ON aut.author_id = author_book.author_id " +
            "LEFT JOIN category_book ON category_book.book_id = b.book_id " +
            "LEFT JOIN category cat ON cat.category_id = category_book.category_id " +
            "GROUP BY b_id ORDER BY b_id ASC")
    List<BookDTO> getAllBookDTOs();


    @UseRowMapper(BookACMapper.class)
    @SqlQuery("SELECT b.book_id AS b_id, b.title, b.price, b.amount, b.is_deleted, " +
            "ARRAY_AGG(DISTINCT(aut.*)) as authors, " +
            "ARRAY_AGG(DISTINCT(cat.*)) as categories FROM book b " +
            "LEFT JOIN author_book ON author_book.book_id = b.book_id " +
            "LEFT JOIN author aut ON aut.author_id = author_book.author_id " +
            "LEFT JOIN category_book ON category_book.book_id = b.book_id " +
            "LEFT JOIN category cat ON cat.category_id = category_book.category_id " +
            "GROUP BY b_id ORDER BY b_id ASC")
    List<Book> getAllBooks();

    @UseRowMapper(BookACMapper.class)
    @SqlQuery("SELECT b.book_id AS b_id, b.title, b.price, b.amount, b.is_deleted, ARRAY_AGG(aut.author_id) as aut_ids, " +
            "ARRAY_AGG(cat.category_id) as cat_ids FROM book b " +
            "LEFT JOIN author_book ON author_book.book_id = b.book_id " +
            "LEFT JOIN author aut ON aut.author_id = author_book.author_id " +
            "LEFT JOIN category_book ON category_book.book_id = b.book_id " +
            "LEFT JOIN category cat ON cat.category_id = category_book.category_id " +
            "GROUP BY b_id ORDER BY b_id ASC")
    List<Book> getAllBookPojos();


    // FIXME: to speed up querying use ARRAY Constructor instead of ARRAY_AGG Function from this suggestion https://dba.stackexchange.com/a/173879/202258
    @UseRowMapper(BookDTOACMapper.class)
    @SqlQuery("SELECT b.book_id AS b_id, b.title, b.price, b.amount, b.is_deleted, ARRAY_AGG(aut.author_id) as aut_ids, " +
            "ARRAY_AGG(cat.category_id) as cat_ids FROM book b " +
            "LEFT JOIN author_book ON author_book.book_id = b.book_id " +
            "LEFT JOIN author aut ON aut.author_id = author_book.author_id " +
            "LEFT JOIN category_book ON category_book.book_id = b.book_id " +
            "LEFT JOIN category cat ON cat.category_id = category_book.category_id " +
            "WHERE b.book_id = :id GROUP BY b_id ORDER BY b_id ASC")
    BookDTO getBookById(@Bind("id") Long book_id);


    // FIXME: to speed up querying use ARRAY Constructor instead of ARRAY_AGG Function from this suggestion https://dba.stackexchange.com/a/173879/202258
    @UseRowMapper(BookDTOACMapper.class)
    @SqlQuery("SELECT b.book_id AS b_id, b.title, b.price, b.amount, b.is_deleted, ARRAY_AGG(aut.author_id) as aut_ids, " +
            "ARRAY_AGG(cat.category_id) as cat_ids FROM book b " +
            "LEFT JOIN author_book ON author_book.book_id = b.book_id " +
            "LEFT JOIN author aut ON aut.author_id = author_book.author_id " +
            "LEFT JOIN category_book ON category_book.book_id = b.book_id " +
            "LEFT JOIN category cat ON cat.category_id = category_book.category_id " +
            "WHERE aut.author_id = :id GROUP BY b_id ORDER BY b_id ASC")
    List<BookDTO> getBooksByAuthorId(@Bind("id") Long authorId);
*/

}
