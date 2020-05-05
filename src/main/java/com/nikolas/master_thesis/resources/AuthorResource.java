package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AddUpdateAuthorDTO;
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

    // TODO: test ALL method below

    @POST
    public Response saveAuthor(AddUpdateAuthorDTO authorDTO)  throws DWBException {
        if(authorDTO == null) {
            throw new DWBException(HttpStatus.SC_NOT_ACCEPTABLE, "Request Body for creating author is empty");
        }
        boolean isSavedAuthor = authorService.saveAuthor(authorDTO);
        if (isSavedAuthor) {
            return Response.status(Status.CREATED).build();
        } else {
            throw new DWBException(HttpStatus.SC_BAD_REQUEST, "Error, please fill correctly all fields for saving author!");
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(AddUpdateAuthorDTO authorDTO, @PathParam("id") Long authorId) throws DWBException  {
        if(authorDTO == null) {
            throw new DWBException(HttpStatus.SC_NOT_ACCEPTABLE, "Request Body for creating author is empty");
        }
        boolean isUpdatedAuthor = authorService.updateAuthor(authorDTO, authorId);
        if (isUpdatedAuthor) {
            return Response.noContent().build();
        } else {
            throw new DWBException(HttpStatus.SC_BAD_REQUEST, "Error, please fill correctly all fields for saving author!");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long authorId) throws DWBException {
        boolean isDeleted = authorService.deleteAuthor(authorId);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "Error, author for id = " + authorId + " does NOT exist in database!");
        }
    }
}
