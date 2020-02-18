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

    @GET
    @Path("/byAuthor/{authorId}")
    public Response getBooksByAuthorId(@PathParam("authorId") Long id) {
        List<BookDTO> books = bookDAO.getBooksByAuthorId(id);
        if (books != null) {
            return Response.ok(books).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @POST
    public Response saveBook(BookDTO bookDTO) {
        BookDTO bookDTOSaved = bookDAO.createBookDefault(bookDTO);
        if (bookDTOSaved != null) {
            return Response.ok(bookDTOSaved).build();
        } else {
            return Response.status(Status.NOT_IMPLEMENTED).build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") Long bookId, BookDTO bookDTO) {
        BookDTO searchedBook = bookDAO.getBookById(bookId);
        if (searchedBook != null) {
            BookDTO updateBookDTODefault = null;
            try {
                updateBookDTODefault = bookDAO.updateBookDefault(bookDTO);
            } catch (Exception e) {
                System.out.println(" ======== ERROR, exception occurred! Exception " + e.getMessage() + " ======== ");
                e.printStackTrace();
            }
            if (updateBookDTODefault != null) {
                return Response.ok(updateBookDTODefault).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
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
