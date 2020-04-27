package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.db.BookDAO;
import com.nikolas.master_thesis.util.StoreException;
import org.apache.http.HttpStatus;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

public class BookService {


    //    private BookMapper bookMapper;
    private final BookDAO bookDAO;

    public BookService(Jdbi jdbi) {
        this.bookDAO = jdbi.onDemand(BookDAO.class);
        this.bookDAO.createBookTable();
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookDAO.getAllBookPojos();
        List<BookDTO> bookDTOList = new ArrayList<>();
        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
//               BookDTO bookDTO = bookMapper.convertBookToBookDTO(book);
//               bookDTOList.add(bookDTO);
            }
            return bookDTOList;
        } else {
            throw new StoreException("Error, no books in DB!", HttpStatus.SC_NOT_FOUND);
        }

    }


}
