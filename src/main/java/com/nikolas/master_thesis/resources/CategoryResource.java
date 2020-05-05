package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AddUpdateCategoryDTO;
import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.db.CategoryDAO;
import com.nikolas.master_thesis.service.CategoryService;
import com.nikolas.master_thesis.util.DWBException;
import org.apache.http.HttpStatus;
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

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GET
    public Response getAllCategories() throws DWBException {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        if (categories != null && !categories.isEmpty()) {
            return Response.ok(categories).build();
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "Error, NO categories in database!");
        }
    }


    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Long id) throws DWBException {
        CategoryDTO category = categoryService.getCategoryById(id);
        if (category != null) {
            return Response.ok(category).build();
        } else {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "Error, category with id = " + id + " does NOT exist in database!");
        }
    }

    @POST
    public Response saveCategory(AddUpdateCategoryDTO categoryDTO) throws DWBException {
        if (categoryDTO == null) {
            throw new DWBException(HttpStatus.SC_NOT_ACCEPTABLE, "Error, request body is empty! Please fill all fields for saving category!");
        }
        boolean savedCategory = categoryService.saveCategory(categoryDTO);
        if (savedCategory) {
            return Response.noContent().build();
        } else {
            throw new DWBException(HttpStatus.SC_BAD_REQUEST, "Error, request body is NOT correctly field or something is missing!");
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateCategory(AddUpdateCategoryDTO categoryDTO, @PathParam("id") Long catId) throws DWBException {
        if (categoryDTO == null) {
            throw new DWBException(HttpStatus.SC_NOT_ACCEPTABLE, "Error, request body is empty! Please fill all fields for saving category!");
        }
        boolean isUpdated = categoryService.updateCategory(categoryDTO, catId);
        if (isUpdated) {
            return Response.noContent().build();
        } else {
            throw new DWBException(HttpStatus.SC_BAD_REQUEST, "Error, request body is NOT correctly field or something is missing!");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long catId) throws DWBException {
        if (null == categoryService.getCategoryById(catId)) {
            throw new DWBException(HttpStatus.SC_NOT_FOUND, "Error, category with id = " + catId + " does NOT exist in database!");
        } else {
            boolean isDeleted = categoryService.deleteCategory(catId);
            if (isDeleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Status.NOT_ACCEPTABLE).build();
            }
        }
    }

}
