package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.db.AuthorDAO;
import com.nikolas.master_thesis.db.BookDAO;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookResource.class);
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;

    public BookResource(Jdbi jdbi) {
        authorDAO = jdbi.onDemand(AuthorDAO.class);
        bookDAO = jdbi.onDemand(BookDAO.class);
        bookDAO.createBookTable();
    }


    @GET
    public Response getAllBooks() {
        List<BookDTO> books = bookDAO.getAllBooks();
        if (books != null) {
            return Response.ok(books).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id) {
        BookDTO book = bookDAO.getBookById(id);
        if (book != null) {
            return Response.ok(book).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @POST
    public Response createBook(BookDTO bookDTO) {
        System.out.println("bookDTO: " + bookDTO.toString());
        LOGGER.info("bookDTO: " + bookDTO.toString());
        int [] rowsAffected = bookDAO.createBook(bookDTO.getTitle(), bookDTO.getPrice(), bookDTO.getAmount(),
                bookDTO.getIsDeleted(), bookDTO.getAuthors(), bookDTO.getCategories());
        if (rowsAffected.length>0/*.length>0*/) {
            return Response.ok(rowsAffected.length).build();
        } else {
            return Response.status(Status.NOT_IMPLEMENTED).build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") Long bookId, BookDTO bookDTO) {
        BookDTO searchedBook = bookDAO.getBookById(bookId);
        if (searchedBook != null) {
            boolean isUpdated = bookDAO.updateBook(bookId, bookDTO.getTitle(), bookDTO.getPrice(),
                    bookDTO.getAmount(), bookDTO.getIsDeleted(), bookDTO.getAuthors(), bookDTO.getCategories());
            return Response.ok(isUpdated).build();
        } else {
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long bookId) {
        BookDTO book = bookDAO.getBookById(bookId);
        if (book != null) {
            boolean isDeleted = bookDAO.deleteBook(bookId);
            if (isDeleted) {
                return Response.ok(isDeleted).build();
            } else {
                return Response.status(Status.NOT_MODIFIED).build();
            }
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
