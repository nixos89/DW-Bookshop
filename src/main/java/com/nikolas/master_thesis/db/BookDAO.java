package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.mapper.BookDTOACMapper;
import com.nikolas.master_thesis.mapper.BookDTOMapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.*;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface BookDAO extends SqlObject {

    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Book( book_id BIGSERIAL PRIMARY KEY, title VARCHAR(255), price double precision, amount INTEGER, is_deleted boolean)")
    void createBookTable();

    @UseRowMapper(BookDTOMapper.class)
    @SqlBatch("WITH new_book as ( " +
            "INSERT INTO book(title, price, amount, is_deleted) VALUES(:title, :price, :amount, :is_deleted)" +
            " returning book_id, title, price, amount, is_deleted ), new_category_book AS ( " +
            "INSERT INTO category_book(category_id, book_id) VALUES(:category.category_id, (SELECT book_id FROM new_book)) returning category_id ) " +
            "INSERT INTO author_book (author_id, book_id) VALUES(:author.author_id, (SELECT book_id FROM new_book))")
    @GetGeneratedKeys
    int[] createBook(@Bind("title") String title, @Bind("price") double price, @Bind("amount") int amount, @Bind("is_deleted") boolean is_deleted,
                     @BindBean("author") Iterable<Long> authors, @BindBean("category") Iterable<Long> categories);


    @SqlUpdate("INSERT INTO author_book(author_id, book_id) VALUES (?, ?)")
    boolean insertAuthorBook(Long authorId, Long bookId);

    @SqlUpdate("INSERT INTO category_book(category_id, book_id) VALUES (?, ?)")
    boolean insertCategoryBook(Long categoryId, Long bookId);


    // TODO: try this out http://jdbi.org/#_default_methods  and this http://jdbi.org/#_joins
    @Transaction
    default BookDTO createBookDefault(BookDTO bookDTOToSave) {
        Handle handle = getHandle();
        BookDTO savedBookDTO = handle.createUpdate("INSERT INTO book(title, price, amount, is_deleted) VALUES(:title, :price, :amount, :is_deleted)")
                .bind("title", bookDTOToSave.getTitle()).bind("price", bookDTOToSave.getPrice())
                .bind("amount", bookDTOToSave.getAmount()).bind("is_deleted", bookDTOToSave.getIsDeleted())
                .executeAndReturnGeneratedKeys()
                .map((rs, ctx) -> new BookDTO(rs.getLong("book_id"), rs.getString("title"), rs.getDouble("price"),
                        rs.getInt("amount"), rs.getBoolean("is_deleted")
                )).findFirst().get();
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
    }// createBookDefault


    @SqlUpdate("DELETE FROM author_book WHERE author_id = ? AND book_id = ?")
    void deleteAuthorBook(Long authorId, Long bookId);

    @SqlUpdate("DELETE FROM category_book WHERE category_book.category_id = ? AND category_book.book_id = ?")
    void deleteCategoryBook(Long categoryId, Long bookId);

    @UseRowMapper(BookDTOMapper.class)
    @SqlUpdate("UPDATE book SET title = :title, price = :price, amount = :amount, is_deleted = :is_deleted WHERE book_id = :book_id")
    boolean updateBookDTO(@Bind("book_id") Long bookId, @Bind("title") String title, @Bind("price") double price, @Bind("amount") int amount, @Bind("is_deleted") boolean is_deleted);



    default BookDTO updateBookDefault(BookDTO bookDTOToSave) throws Exception {
        Handle handle = getHandle();
        boolean isUpdated = updateBookDTO(bookDTOToSave.getBookId(), bookDTOToSave.getTitle(), bookDTOToSave.getPrice(), bookDTOToSave.getAmount(), bookDTOToSave.getIsDeleted());
        if (!isUpdated) {
            throw new Exception("Book has NOT been updated! Status: " + Response.Status.NOT_MODIFIED);
        } else {
            System.out.println(" ======= Book has been updated! ======== ");
        }

//                handle.createUpdate("UPDATE book SET title = :title, price = :price, amount = :amount, is_deleted = :is_deleted WHERE book_id = :book_id")
//                .bind("book_id", bookDTOToSave.getBookId()).bind("title", bookDTOToSave.getTitle()).bind("price", bookDTOToSave.getPrice())
//                .bind("amount", bookDTOToSave.getAmount()).bind("is_deleted", bookDTOToSave.getIsDeleted())
//                .executeAndReturnGeneratedKeys()
//                .map((rs, ctx) -> new BookDTO(rs.getLong("book_id"), rs.getString("title"), rs.getDouble("price"),
//                        rs.getInt("amount"), rs.getBoolean("is_deleted")
//                )).findFirst().get();

        List<Long> existingCategoryIds = handle
                .createQuery("SELECT category.category_id FROM category LEFT JOIN category_book ON category.category_id = category_book.category_id WHERE category_book.book_id = :book_id")
                .bind("book_id", bookDTOToSave.getBookId())
                .mapTo(Long.class).list();

        List<Long> existingAuthorIds = handle
                .createQuery("SELECT author.author_id FROM author LEFT JOIN author_book ON author.author_id = author_book.author_id WHERE author_book.book_id = :book_id")
                .bind("book_id", bookDTOToSave.getBookId())
                .mapTo(Long.class).list();

        Set<Long> authorIds = iterateAuthorBook(existingAuthorIds, bookDTOToSave);
        Set<Long> categoryIds = iterateCategoryBook(existingCategoryIds, bookDTOToSave);

        return new BookDTO(bookDTOToSave.getBookId(), bookDTOToSave.getTitle(), bookDTOToSave.getPrice(),
                bookDTOToSave.getAmount(), bookDTOToSave.getIsDeleted(),
                new ArrayList<>(authorIds), new ArrayList<>(categoryIds));
    }// updateBook

    default Set<Long> iterateAuthorBook(List<Long> existingAuthorIds, BookDTO bookDTOToSave){
        Set<Long> authorIds = new HashSet<>();
        int i = 0;
        if (existingAuthorIds.size() > bookDTOToSave.getAuthors().size()) {
            for (Long authorId : existingAuthorIds) {
                if (!bookDTOToSave.getAuthors().contains(authorId)) {
                    //TODO: Duuuude, u r DELETING and INSERTING same combo...WTF???!! :/ Fix it (ALSO in next for-loop)!!!!
                    deleteAuthorBook(authorId, bookDTOToSave.getBookId());
                    insertAuthorBook(bookDTOToSave.getAuthors().get(i), bookDTOToSave.getBookId());
                    ++i;
                }
                authorIds.add(authorId);
            }
        } else {
            for(Long authorId: bookDTOToSave.getAuthors()){
                if(!existingAuthorIds.contains(authorId)){
                    deleteAuthorBook(authorId, bookDTOToSave.getBookId());
                    insertAuthorBook(bookDTOToSave.getAuthors().get(i), bookDTOToSave.getBookId());
                    ++i;
                }
                authorIds.add(authorId);
            }
        }
        return authorIds;
    } // iterateAuthorBook

    default Set<Long> iterateCategoryBook(List<Long> existingCategoryIds, BookDTO bookDTOToSave){
        Set<Long> categoryIds = new HashSet<>();
        int i = 0;
        if (existingCategoryIds.size() > bookDTOToSave.getCategories().size()) {
            for (Long categoryId : existingCategoryIds) {
                if (!bookDTOToSave.getCategories().contains(categoryId)) {
                    deleteCategoryBook(categoryId, bookDTOToSave.getBookId());
                    insertCategoryBook(bookDTOToSave.getCategories().get(i), bookDTOToSave.getBookId());
                    ++i;
                }
                categoryIds.add(categoryId);
            }
        } else {
            for(Long categoryId: bookDTOToSave.getCategories()){
                if(!existingCategoryIds.contains(categoryId)){
                    deleteCategoryBook(categoryId, bookDTOToSave.getBookId());
                    insertCategoryBook(bookDTOToSave.getCategories().get(i), bookDTOToSave.getBookId());
                    ++i;
                }
                categoryIds.add(categoryId);
            }
        }
        return categoryIds;
    }


    @UseRowMapper(BookDTOACMapper.class)
    @SqlQuery("SELECT book.book_id, book.title, book.price, book.amount, book.is_deleted, author.author_id, category.category_id FROM book " +
            "LEFT JOIN author_book ON book.book_id = author_book.book_id " +
            "LEFT JOIN author ON author_book.author_id = author.author_id " +
            "LEFT JOIN category_book ON book.book_id = category_book.book_id " +
            "LEFT JOIN category ON category_book.category_id = category.category_id")
    List<BookDTO> getAllBooks();

    @UseRowMapper(BookDTOACMapper.class)
    @SqlQuery("SELECT book.book_id, book.title, book.price, book.amount, book.is_deleted, author.author_id, category_book.category_id FROM book " +
            "LEFT JOIN author_book ON book.book_id = author_book.book_id " +
            "LEFT JOIN author ON author_book.author_id = author.author_id " +
            "LEFT JOIN category_book ON book.book_id = category_book.book_id " +
            "LEFT JOIN category ON category_book.category_id = category.category_id " +
            "WHERE book.book_id = :id")
    BookDTO getBookById(@Bind("id") Long book_id);

    @UseRowMapper(BookDTOACMapper.class)
    @SqlQuery("SELECT book.book_id, book.title, book.amount, book.price, book.is_deleted, author_book.author_id, category_book.category_id FROM book " +
            "LEFT JOIN author_book ON book.book_id = author_book.book_id " +
            "LEFT JOIN category_book ON book.book_id = category_book.book_id " +
            "WHERE author_book.author_id = :author_id")
    List<BookDTO> getBooksByAuthorId(@Bind("author_id") Long authorId);

    @SqlUpdate("DELETE FROM Book WHERE Book.book_id = ?")
    boolean deleteBook(Long bookId);

    // TODO: Also UPDATE author_ids and category_ids!!!
    @SqlUpdate(" WITH updated_book AS (" +
            "UPDATE book SET title = :title, price = :price, amount = :amount, is_deleted = :is_deleted WHERE book_id = :book_id " +
            "returning book_id), updated_book_category AS (" +
            "UPDATE book_category SET category_id = :category_ids WHERE book_id = :book_id) " +
            "UPDATE book_author(book_id, author_id) SET author_id = :authorIds WHERE book_id = :book_id")
    boolean updateBook(@Bind("book_id") Long bookId, @Bind("title") String title, @Bind("price") double price,
                       @Bind("amount") int amount, @Bind("is_deleted") boolean is_deleted,
                       @BindBean("authorIds") List<Long> authorIds, @BindBean("categoryIds") List<Long> categoryIds);


    @SqlUpdate(" WITH updated_book AS (" +
            "UPDATE book SET title = :title, price = :price, amount = :amount, is_deleted = :is_deleted WHERE book_id = :book_id " +
            "returning book_id), updated_book_category AS (" +
            "UPDATE book_category SET category_id = :category_ids WHERE book_id = :book_id) " +
            "UPDATE book_author(book_id, author_id) SET author_id = :authorIds WHERE book_id = :book_id")
    boolean updateBook2(@Bind("book_id") Long bookId, @Bind("title") String title, @Bind("price") double price,
                        @Bind("amount") int amount, @Bind("is_deleted") boolean is_deleted,
                        @BindBean("authorIds") List<AuthorDTO> authorIds, @BindBean("categoryIds") List<CategoryDTO> categoryIds);
}
