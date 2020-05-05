package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.CategoryDTO;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;

@Contract
public interface CategoryService {


    CategoryDTO getCategoryById(Long catId);

    List<CategoryDTO> getAllCategories();

    boolean saveCategory(CategoryDTO catDTO);

    boolean updateCategory(CategoryDTO catDTO, Long catId);

    boolean deleteCategory(Long catId);

}
