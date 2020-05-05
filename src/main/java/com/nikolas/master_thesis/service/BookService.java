package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.AddUpdateBookDTO;
import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.db.AuthorDAO;
import com.nikolas.master_thesis.db.BookDAO;
import com.nikolas.master_thesis.db.CategoryDAO;
import com.nikolas.master_thesis.mapstruct_mappers.BookMSMapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    private final Jdbi jdbi;
    private final BookMSMapper bookMSMapper;

    public BookService(Jdbi jdbi, BookMSMapper bookMSMapper) {
        this.jdbi = jdbi;
        this.bookMSMapper = bookMSMapper;
//        BookDAO bookDAO = jdbi.onDemand(BookDAO.class); // using this to create Book table if needed
//        bookDAO.createBookTable();
    }

    public List<BookDTO> getAllBooks() {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            BookDAO bookDAO = handle.attach(BookDAO.class);
            List<Book> books = bookDAO.getAllBooks();
            List<BookDTO> bookDTOList = new ArrayList<>();
            if (books != null && !books.isEmpty()) {
                AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
                CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
                for (Book book : books) {
                    List<Author> authors = authorDAO.getAuthorsByBookId(book.getBookId());
                    List<Category> categories = categoryDAO.getCategoriesByBookId(book.getBookId());
                    book.setAuthors(new HashSet<>(authors));
                    book.setCategories(new HashSet<>(categories));
                    BookDTO bookDTO = bookMSMapper.fromBook(book);
                    bookDTOList.add(bookDTO);
                }
                handle.commit();
                return bookDTOList;
            } else {
                throw new Exception("Error, books are empty or null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }

    public BookDTO getBookById(Long bookId) {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            BookDAO bookDAO = handle.attach(BookDAO.class);
            Book book = bookDAO.getBookById(bookId);
            if (book != null) {
                AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
                CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
                List<Author> authors = authorDAO.getAuthorsByBookId(book.getBookId());
                List<Category> categories = categoryDAO.getCategoriesByBookId(book.getBookId());
                book.setAuthors(new HashSet<>(authors));
                book.setCategories(new HashSet<>(categories));
                handle.commit();
                return bookMSMapper.fromBook(book);
            } else {
                throw new Exception("Error, requested book does NOT exist in DB with id = " + bookId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }


    public boolean createBook(AddUpdateBookDTO bookDTOToSave) {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            BookDAO bookDAO = handle.attach(BookDAO.class);
            Book savedBook = bookDAO.createBook(bookDTOToSave.getTitle(), bookDTOToSave.getPrice(),
                    bookDTOToSave.getAmount(), bookDTOToSave.isDeleted());

            if (savedBook != null) {
                for (Long aId : bookDTOToSave.getAuthors()) {
                    bookDAO.insertAuthorBook(aId, savedBook.getBookId());
                }
                for (Long cId : bookDTOToSave.getCategories()) {
                    bookDAO.insertCategoryBook(cId, savedBook.getBookId());
                }
                handle.commit();
                return true;
            } else {
                throw new Exception("Error, book has NOT been saved!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }

    public boolean updateBook(AddUpdateBookDTO bookDTOToUpdate, Long bookId) {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            BookDAO bookDAO = handle.attach(BookDAO.class);
            Book searchedBook = bookDAO.getBookById(bookId);
            if (searchedBook != null) {
                if(bookDAO.updateBookDTO(bookId, bookDTOToUpdate.getTitle(),
                        bookDTOToUpdate.getPrice(), bookDTOToUpdate.getAmount(), bookDTOToUpdate.isDeleted())){
                    AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
                    CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
                    List<Long> existingCategoryIds = categoryDAO.getCategoriesByBookId(bookId).stream()
                            .mapToLong(Category::getCategoryId)
                            .boxed().collect(Collectors.toList());

                    List<Long> existingAuthorIds = authorDAO.getAuthorsByBookId(bookId).stream()
                            .mapToLong(Author::getAuthorId)
                            .boxed().collect(Collectors.toList());

                    bookDAO.iterateAuthorBook(existingAuthorIds, bookDTOToUpdate, bookId);
                    bookDAO.iterateCategoryBook(existingCategoryIds, bookDTOToUpdate, bookId);
                    System.out.println(" ======= Book has been updated! ======== ");
                    handle.commit();
                    return true;
                } else {
                    throw new Exception("Error, book has NOT been updated!");
                }
            } else {
                throw new Exception("Error, book with id = " + bookId + " does NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }

    public boolean deleteBook(Long bookId) {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            BookDAO bookDAO = handle.attach(BookDAO.class);
            Book book = bookDAO.getBookById(bookId);
            if (book != null) {
                if (bookDAO.deleteBook(bookId)) {
                    handle.commit();
                    return true;
                } else {
                    throw new Exception("Error, book has NOT been deleted!");
                }
            } else {
                throw new Exception("Error, book with id = " + bookId + " does NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }

}
