package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.AddUpdateCategoryDTO;
import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.db.CategoryDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;


public class CategoryService {

    //    private final CategoryDAO categoryDAO;
    private final Jdbi jdbi;

    public CategoryService(Jdbi jdbi) {
        this.jdbi = jdbi;
//        this.categoryDAO = jdbi.onDemand(CategoryDAO.class);
//        categoryDAO.createCategoryTable();
//        categoryDAO.createTableBookCategory();
    }

    public CategoryDTO getCategoryById(Long catId) {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
            Category category = categoryDAO.getCategoryById(catId);
            if (category != null) {
                handle.commit();
                return new CategoryDTO(category.getCategoryId(), category.getName(), category.isDeleted());
            } else {
                throw new Exception("Error, category with id = " + catId + " does NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }


    public List<CategoryDTO> getAllCategories() {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
            List<Category> categories = categoryDAO.getAllCategories();
            if (categories != null && !categories.isEmpty()) {
                List<CategoryDTO> categoryDTOS = new ArrayList<>();
                for (Category cat : categories) {
                    categoryDTOS.add(new CategoryDTO(cat.getCategoryId(), cat.getName(), cat.isDeleted()));
                }
                handle.commit();
                return categoryDTOS;
            } else {
                throw new Exception("Error, with categories are NULL or there are no categories in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }


    public boolean saveCategory(AddUpdateCategoryDTO catDTO) {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
            Category createdCat = categoryDAO.createCategory(catDTO.getName(), catDTO.isDeleted());
            if (createdCat != null) {
                handle.commit();
                return true;
            } else {
                throw new Exception("Error, category has NOT been saved!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }


    public boolean updateCategory(AddUpdateCategoryDTO catDTO, Long catId) {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
            Category searchedCat = categoryDAO.getCategoryById(catId);
            if (searchedCat != null) {
                if (categoryDAO.updateCategory(catId, catDTO.getName(), catDTO.isDeleted())) {
                    handle.commit();
                    return true;
                } else {
                    throw new Exception("Error, category has NOT been updated!");
                }
            } else {
                throw new Exception("Error, category with id = " + catId + " does NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }


    public boolean deleteCategory(Long catId) {
        Handle handle = jdbi.open();
        try {
            CategoryDAO categoryDAO = handle.attach(CategoryDAO.class);
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            Category cat = categoryDAO.getCategoryById(catId);
            if (cat != null) {
                handle.begin();
                if (categoryDAO.deleteCategory(catId)) {
                    handle.commit();
                    return true;
                } else {
                    throw new Exception("Error, category with id = " + catId + " has NOT been deleted!");
                }
            } else {
                throw new Exception("Error, category with id = " + catId + " does NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }

}
