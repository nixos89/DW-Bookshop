package com.nikolas.master_thesis.core;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Category {

    private Long categoryId;
    private String name;
    private boolean isDeleted;
    private Set<Book> books; // @ManyToMany

    public Category() {
        books = new HashSet<>();
    }

    public Category(String name, boolean isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Category(Long categoryId, String name, boolean isDeleted) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return isDeleted == category.isDeleted &&
                categoryId.equals(category.categoryId) &&
                name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, isDeleted);
    }
}
