package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.db.CategoryDAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

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
    public Response getAllCategories() {
        List<CategoryDTO> categories = categoryDAO.getAllCategories();
        if(!categories.isEmpty()){
            return Response.ok(categories).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }

    }
}
