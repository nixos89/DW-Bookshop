package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class CategoryDTO {

    private Long categoryId;
    private String name;
    private boolean isDeleted;
    private Set<BookDTO> books; //    @ManyToMany

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

    public CategoryDTO(Long categoryId, String name, boolean isDeleted, Set<BookDTO> books) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDeleted = isDeleted;
        this.books = books;
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

    @JsonProperty("books")
    public Set<BookDTO> getBooks() {
        return books;
    }
}
