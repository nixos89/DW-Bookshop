package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.AddUpdateBookDTO;
import com.nikolas.master_thesis.api.BookDTO;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;

@Contract
public interface BookService {

    List<BookDTO> getAllBooks();

    BookDTO getBookById(Long bookId);

    boolean createBook(AddUpdateBookDTO bookDTOToSave);

    boolean updateBook(AddUpdateBookDTO bookDTOToUpdate, Long bookId);

    boolean deleteBook(Long bookId);

}
