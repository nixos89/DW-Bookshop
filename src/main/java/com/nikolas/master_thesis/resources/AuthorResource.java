package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.db.AuthorDAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final AuthorDAO authorDAO;

    public AuthorResource(Jdbi jdbi) {
        this.authorDAO = jdbi.onDemand(AuthorDAO.class);
        authorDAO.createTableAuthor();
        authorDAO.createTableAuthorBook();
    }

    @GET
    public Response getAllAuthors() {
        return Response.ok(authorDAO.getAllAuthors()).build();
    }

    @POST
    public Response saveAuthor(AuthorDTO authorDTO) {
        AuthorDTO savedAuthor = authorDAO.createAuthor(authorDTO.getFirstName(), authorDTO.getLastName());
        if (savedAuthor != null) {
            return Response.ok(savedAuthor).build();
        } else {
            return Response.status(Status.NOT_IMPLEMENTED).build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") Long authorId, AuthorDTO authorDTO) {
        AuthorDTO searchedAuthor = authorDAO.getAuthorById(authorDTO.getAuthorId());
        if(searchedAuthor!=null){
            return Response.ok(searchedAuthor).build();
        }else{
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }
}
