package com.nikolas.master_thesis.api;

import java.util.HashSet;
import java.util.Set;

public class CategoryDTO {

    private Long id;
    private String name;
    private boolean isDeleted;
    private Set<BookDTO> books; //    @ManyToMany

    public CategoryDTO() {
    }

    public CategoryDTO(String name, boolean isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public CategoryDTO(Long id, String name, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Set<BookDTO> getBooks() {
        return books;
    }
}
