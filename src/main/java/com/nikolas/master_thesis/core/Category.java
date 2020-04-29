package com.nikolas.master_thesis.core;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Category {

    private Long categoryId;
    private String name;
    private boolean isDeleted;
    private Set<Book> books;

    public Category() {
        books = new HashSet<>();
    }

    public Category(Long categoryId, String name, boolean isDeleted) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Category(Long categoryId, String name, boolean isDeleted, Set<Book> books) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDeleted = isDeleted;
        this.books = books;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
