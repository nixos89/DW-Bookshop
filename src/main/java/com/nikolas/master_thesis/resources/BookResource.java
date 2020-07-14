package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AddUpdateBookDTO;
import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.api.BookListDTO;
import com.nikolas.master_thesis.service.BookService;
import com.nikolas.master_thesis.util.DWBException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookResource.class);
    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }


    @GET
    public Response getAllBooks() throws DWBException {
        BookListDTO books = bookService.getAllBooks();
        if (books != null) {
            return Response.ok(books).build();
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "Error, no books have been found!");
        }
    }


    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id) throws DWBException {
        BookDTO book = bookService.getBookById(id);
        if (book != null) {
            return Response.ok(book).build();
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "Error, no book with id = " + id);
        }
    }


    @POST
    public Response saveBook(AddUpdateBookDTO bookDTO) throws DWBException {
        if (bookDTO == null) {
            throw new DWBException(HttpStatus.SC_NOT_ACCEPTABLE, "Error, request body is empty! Please fill all fields for saving book!");
        }
        if (bookService.createBook(bookDTO)) {
            return Response.status(Status.CREATED).build();
        } else {
            throw new DWBException(HttpStatus.SC_BAD_REQUEST, "Error, book creation failed! Please fill correctly all fields for saving book!");
        }
    }


    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") Long bookId, AddUpdateBookDTO bookDTO) throws DWBException {
        BookDTO searchedBook = bookService.getBookById(bookId);
        if (searchedBook != null) {
            boolean isUpdated = bookService.updateBook(bookDTO, bookId);
            if (isUpdated) {
                return Response.noContent().build();
            } else {
                throw new DWBException(HttpStatus.SC_BAD_REQUEST, "Error, book can NOT be saved! Check all fields to be correctly field!");
            }
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "Error, book for id " + bookId + " has not been found!");
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long bookId) throws DWBException {
        BookDTO book = bookService.getBookById(bookId);
        if (book != null) {
            if (bookService.deleteBook(bookId)) {
                return Response.noContent().build();
            } else {
                throw new DWBException(HttpStatus.SC_BAD_REQUEST, "Error, book for id " + bookId + " can NOT be deleted!");
            }
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "Error, book for id " + bookId + " has not been found!");
        }
    }
}
