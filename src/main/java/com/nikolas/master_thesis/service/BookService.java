package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.AddUpdateBookDTO;
import com.nikolas.master_thesis.api.BookDTO2;
import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.db.AuthorDAO;
import com.nikolas.master_thesis.db.BookDAO;
import com.nikolas.master_thesis.db.CategoryDAO;
import com.nikolas.master_thesis.mapper.BookMapper;
import com.nikolas.master_thesis.mapstruct_mappers.BookMSMapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BookService {

    private final BookDAO bookDAO;
    private final CategoryDAO categoryDAO;
    private final AuthorDAO authorDAO;
    private final Jdbi jdbi;
    private final BookMSMapper bookMSMapper;

    public BookService(Jdbi jdbi, BookMSMapper bookMSMapper) {
        this.jdbi = jdbi;
        this.bookMSMapper = bookMSMapper;
        this.bookDAO = jdbi.onDemand(BookDAO.class);
        this.categoryDAO = jdbi.onDemand(CategoryDAO.class);
        this.authorDAO = jdbi.onDemand(AuthorDAO.class);
        this.bookDAO.createBookTable();
    }

    public List<BookDTO2> getAllBooks() {
        Handle handle = jdbi.open();

        List<Book> books = handle.createQuery("SELECT b.book_id, b.title, b.price, b.amount, b.is_deleted FROM book b ORDER BY b.book_id")
                .map(new BookMapper()).list();

        List<BookDTO2> bookDTO2List = new ArrayList<>();
        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                List<Author> authors = authorDAO.getAuthorsByBookId(book.getBookId());
                List<Category> categories = categoryDAO.getCategoriesByBookId(book.getBookId());
                book.setAuthors(new HashSet<>(authors));
                book.setCategories(new HashSet<>(categories));
                BookDTO2 bookDTO = bookMSMapper.fromBook(book);
                bookDTO2List.add(bookDTO);
            }
            return bookDTO2List;
        } else {
            return null;
        }
    }

    public BookDTO2 getBookById(Long bookId) {
        Handle handle = jdbi.open();
        Book book = handle.createQuery("SELECT b.book_id, b.title, b.price, b.amount, b.is_deleted FROM book b WHERE b.book_id = :bId")
                .bind("bId", bookId)
                .map(new BookMapper())
                .findFirst().orElse(null);

        if (book != null) {
            List<Author> authors = authorDAO.getAuthorsByBookId(book.getBookId());
            List<Category> categories = categoryDAO.getCategoriesByBookId(book.getBookId());
            book.setAuthors(new HashSet<>(authors));
            book.setCategories(new HashSet<>(categories));
            return bookMSMapper.fromBook(book);
        } else {
            return null;
        }
    }


    public boolean createBook(AddUpdateBookDTO bookDTOToSave) {
        Handle handle = jdbi.open();
        Book savedBook = handle.createUpdate("INSERT INTO Book(title, price, amount, is_deleted) VALUES(:title, :price, :amount, :is_deleted)")
                .bind("title", bookDTOToSave.getTitle()).bind("price", bookDTOToSave.getPrice())
                .bind("amount", bookDTOToSave.getAmount()).bind("is_deleted", bookDTOToSave.isDeleted())
                .executeAndReturnGeneratedKeys()
                .map((rs, ctx) -> new Book(rs.getLong("book_id"), rs.getString("title"), rs.getDouble("price"),
                        rs.getInt("amount"), rs.getBoolean("is_deleted")
                )).findFirst().orElse(null);

        if (savedBook != null) {
            for (Long aId: bookDTOToSave.getAuthors()) {
                bookDAO.insertAuthorBook(aId, savedBook.getBookId());
            }
            for (Long cId: bookDTOToSave.getCategories()) {
                bookDAO.insertCategoryBook(cId, savedBook.getBookId());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean updateBook(AddUpdateBookDTO bookDTOToUpdate, Long bookId) {
        BookDTO2 searchedBook = getBookById(bookId);
        if (searchedBook != null) {
            try {
                Handle handle = jdbi.open();
                boolean isUpdated = bookDAO.updateBookDTO(bookId, bookDTOToUpdate.getTitle(),
                            bookDTOToUpdate.getPrice(), bookDTOToUpdate.getAmount(), bookDTOToUpdate.isDeleted());
                if (!isUpdated) {
                    throw new Exception("Book has NOT been updated! Status: " + Response.Status.NOT_MODIFIED);
                } else {
                    System.out.println(" ======= Book has been updated! ======== ");
                }
                List<Long> existingCategoryIds = handle
                        .createQuery("SELECT category.category_id FROM category LEFT JOIN category_book ON category.category_id = category_book.category_id WHERE category_book.book_id = :book_id")
                        .bind("book_id", bookId)
                        .mapTo(Long.class).list();
                List<Long> existingAuthorIds = handle
                        .createQuery("SELECT author.author_id FROM author LEFT JOIN author_book ON author.author_id = author_book.author_id WHERE author_book.book_id = :book_id")
                        .bind("book_id", bookId)
                        .mapTo(Long.class).list();

                bookDAO.iterateAuthorBook(existingAuthorIds, bookDTOToUpdate, bookId);
                bookDAO.iterateCategoryBook(existingCategoryIds, bookDTOToUpdate, bookId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }


    public boolean deleteBook(Long id) {
        BookDTO2 book = getBookById(id);
        if (book != null) {
            return bookDAO.deleteBook(id);
        } else {
            return false;
        }
    }
}
