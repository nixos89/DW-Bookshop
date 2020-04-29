package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.db.CategoryDAO;
import com.nikolas.master_thesis.service.CategoryService;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private final CategoryService categoryService;

    @Inject
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GET
    public Response getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        if (!categories.isEmpty()) {
            return Response.ok(categories).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        if (category != null) {
            return Response.ok(category).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response saveCategory(CategoryDTO categoryDTO) {
        boolean savedCategory = categoryService.saveCategory(categoryDTO);
        if (savedCategory) {
            return Response.noContent().build();
        } else {
            System.out.println("Damn! Something went wrong ...");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateCategory(CategoryDTO categoryDTO, @PathParam("id") Long catId) {
        boolean isUpdated = categoryService.updateCategory(categoryDTO, catId);
        if (isUpdated) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long catId) {
        boolean isDeleted = categoryService.deleteCategory(catId);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_ACCEPTABLE).build();
        }
    }

}
