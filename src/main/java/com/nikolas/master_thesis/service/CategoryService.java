package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.db.CategoryDAO;
import com.nikolas.master_thesis.util.StoreException;
import org.apache.http.HttpStatus;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryService(Jdbi jdbi) {
        this.categoryDAO = jdbi.onDemand(CategoryDAO.class);
        categoryDAO.createCategoryTable();
        categoryDAO.createTableBookCategory();
    }

    public CategoryDTO getCategoryById(Long catId) {
        Category category = categoryDAO.getCategoryById2(catId);
        if (category != null) {
            return new CategoryDTO(category.getCategoryId(), category.getName(), category.isDeleted());
        } else {
            throw new StoreException("Error, no category found for id = " + catId + " in database!", HttpStatus.SC_NOT_FOUND);
        }
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryDAO.getAllCategories2();
        if (categories != null && !categories.isEmpty()) {
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (Category cat : categories) {
                categoryDTOS.add(new CategoryDTO(cat.getCategoryId(), cat.getName(), cat.isDeleted()));
            }
            return categoryDTOS;
        } else {
            throw new StoreException("Error, no categories in database!", HttpStatus.SC_NOT_FOUND);
        }
    }

    public boolean saveCategory(CategoryDTO catDTO) {
        return categoryDAO.createCategory(catDTO.getName(), catDTO.getIsDeleted());
    }

    public boolean updateAuthor(CategoryDTO catDTO, Long catId) {
        Category searchedCat = categoryDAO.getCategoryById2(catId);
        if (searchedCat != null) {
            return categoryDAO.updateCategory(catDTO.getCategoryId(), catDTO.getName(), catDTO.getIsDeleted());
        } else {
            throw new StoreException("Exception, category with id = " + catId + " not found", HttpStatus.SC_NOT_FOUND);
        }
    }

    public boolean deleteAuthor(Long catId) {
        Category cat = categoryDAO.getCategoryById2(catId);
        if (cat != null) {
            return categoryDAO.deleteCategory(catId);
        } else {
            throw new StoreException("Exception, category for id = " + catId + " does NOT exist!", HttpStatus.SC_NOT_FOUND);
        }
    }

}