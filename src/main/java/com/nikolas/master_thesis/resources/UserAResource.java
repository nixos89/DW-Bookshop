package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.UserADTO;
import com.nikolas.master_thesis.db.UserADAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/usersa")
@Produces(MediaType.APPLICATION_JSON)
public class UserAResource {

    private final UserADAO userADAO;

    public UserAResource(Jdbi jdbi) {
        userADAO = jdbi.onDemand(UserADAO.class);
        userADAO.createUserATable();
    }


    @GET
    public Response getAllUsers() {
        List<UserADTO> users = userADAO.getAllUsers();
        if (!users.isEmpty()) {
            return Response.ok(users).build();
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        UserADTO user = userADAO.getUserById(id);
        if (user !=null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }


    // TODO: change this PATH so it has different URL -> google how PROPERLY NAME this route (Get Users by Account with id = ?)
    @GET
    @Path("/forAccount")
    public Response getUsersForAccountId(@QueryParam("accountId") int accountId) {
        List<UserADTO> usersByAccountId = userADAO.getUsersForAccount(accountId);
        if (!usersByAccountId.isEmpty()) {
            return Response.ok(usersByAccountId).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @POST
    public Response createUser(UserADTO userADTO) {
        UserADTO savedUserADTO = userADAO.saveUser(userADTO.getName(), userADTO.getAccountId());
        if (savedUserADTO != null) {
            return Response.ok(savedUserADTO).build();
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

}
