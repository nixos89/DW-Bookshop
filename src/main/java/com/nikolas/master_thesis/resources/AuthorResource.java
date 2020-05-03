package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.service.AuthorService;
import com.nikolas.master_thesis.util.DWBException;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private AuthorService authorService;

    @Inject
    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") Long id) throws DWBException {
        AuthorDTO author = authorService.getAuthorById(id);
        if (author != null) {
            return Response.ok(author).build();
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "No author in database exist for id = " + id);
        }
    }

    @GET
    public Response getAllAuthors() throws DWBException {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        if(authors != null && !authors.isEmpty()) {
            return Response.ok(authors).build();
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "No authors in database exist!");
        }

    }

    @POST
    public Response saveAuthor(AuthorDTO authorDTO) {
        boolean isSavedAuthor = authorService.saveAuthor(authorDTO);
        if (isSavedAuthor) {
            return Response.status(Status.CREATED).build();
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(AuthorDTO authorDTO, @PathParam("id") Long authorId) {
        boolean isUpdatedAuthor = authorService.updateAuthor(authorDTO, authorId);
        if (isUpdatedAuthor) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long authorId) {
        boolean isDeleted = authorService.deleteAuthor(authorId);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_ACCEPTABLE).build();
        }
    }
}
