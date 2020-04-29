package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.db.RoleDAO;
import com.nikolas.master_thesis.db.UserDAO;
import com.nikolas.master_thesis.service.UserService;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    
    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    public Response getAllUsers() {
//        List<UserADTO> users = userDAO.getAllUsers();
//        if (!users.isEmpty()) {
//            return Response.ok(users).build();
//        } else {
//            return Response.status(Status.BAD_REQUEST).build();
//        }
        return null; // TODO: implement everything needed
    }
}
