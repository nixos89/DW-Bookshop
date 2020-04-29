package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class CategoryDTO {

    private Long categoryId;
    private String name;
    private boolean isDeleted;

    public CategoryDTO() {
    }

    public CategoryDTO(String name, boolean isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public CategoryDTO(Long categoryId, String name, boolean isDeleted) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    @JsonProperty("category_id")
    public Long getCategoryId() {
        return categoryId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("is_deleted")
    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
