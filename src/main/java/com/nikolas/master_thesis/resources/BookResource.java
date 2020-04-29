package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AddUpdateBookDTO;
import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.api.BookDTO2;
import com.nikolas.master_thesis.service.BookService;
import com.nikolas.master_thesis.util.DWBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookResource.class);
    private final BookService bookService;
    @Inject
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    public Response getAllBooks() {
        List<BookDTO2> books = bookService.getAllBooks();
        if (books != null) {
            return Response.ok(books).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id) throws DWBException {
        BookDTO2 book = bookService.getBookById(id);
        if (book != null) {
            return Response.ok(book).build();
        } else {
            throw new DWBException(404, "Error, no book with id = " + id);
        }
    }

//    @GET
//    @Path("/byAuthor/{authorId}")
//    public Response getBooksByAuthorId(@PathParam("authorId") Long id) {
//        List<BookDTO> books = bookDAO.getBooksByAuthorId(id);
//        if (books != null) {
//            return Response.ok(books).build();
//        } else {
//            return Response.status(Status.NOT_FOUND).build();
//        }
//    }


    @POST
    public Response saveBook(AddUpdateBookDTO bookDTO) {
        if (bookService.createBook(bookDTO)) {
            return Response.status(Status.CREATED).build();
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") Long bookId, AddUpdateBookDTO bookDTO) {
        BookDTO2 searchedBook = bookService.getBookById(bookId);
        if (searchedBook != null) {
            boolean isUpdated = bookService.updateBook(bookDTO, bookId);
            if (isUpdated) {
                return Response.noContent().build();
            } else {
                return Response.status(Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long bookId) {
        BookDTO2 book = bookService.getBookById(bookId);
        if (book != null) {
            if (bookService.deleteBook(bookId)) {
                return Response.noContent().build();
            } else {
                return Response.status(Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
