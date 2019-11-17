package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.BookDTO;
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

    private final BookDAO bookDAO;

    private final static Logger LOGGER = LoggerFactory.getLogger(BookResource.class);

    public BookResource(Jdbi jdbi) {
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
        BookDTO savedBook = bookDAO.createBook(bookDTO.getTitle(), bookDTO.getPrice(), bookDTO.getAmount(), bookDTO.isDeleted());
        if (savedBook != null) {
            return Response.ok(savedBook).build();
        } else {
            return Response.status(Status.NOT_IMPLEMENTED).build();
        }
    }
}
