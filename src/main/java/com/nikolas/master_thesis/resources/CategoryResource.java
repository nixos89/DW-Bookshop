package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.db.CategoryDAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private final CategoryDAO categoryDAO;

    public CategoryResource(Jdbi jdbi) {
        categoryDAO = jdbi.onDemand(CategoryDAO.class);
        categoryDAO.createCategoryTable();
        categoryDAO.createTableBookCategory();
    }

    @GET
    public Response getAllCategories(){
        return null;
    }
}
