package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.db.CategoryDAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
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
        if (!categories.isEmpty()) {
            return Response.ok(categories).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Long id) {
        CategoryDTO category = categoryDAO.getCategoryById(id);
        if (category != null) {
            return Response.ok(category).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @POST
    public Response saveCategory(CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryDAO.createCategory(categoryDTO.getName(), categoryDTO.getIsDeleted());
        if (savedCategory != null) {
            return Response.ok(savedCategory).build();
        } else {
            System.out.println("Damn! Something went wrong ...");
            return Response.status(Status.NOT_IMPLEMENTED).build();
        }
    }
}
