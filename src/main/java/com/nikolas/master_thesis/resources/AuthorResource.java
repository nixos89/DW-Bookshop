package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.service.AuthorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


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
    public Response getAuthorById(@PathParam("id") Long id) {
        return Response.ok(authorService.getAuthorById(id)).build();
    }

    @GET
    public Response getAllAuthors() {
        return Response.ok(authorService.getAllAuthors()).build();
    }

    @POST
    public Response saveAuthor(AuthorDTO authorDTO) {
        boolean isSavedAuthor = authorService.saveAuthor(authorDTO);
        if (isSavedAuthor) {
            return Response.ok().build();
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
