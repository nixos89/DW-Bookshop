package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

//@Builder
//@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BookDTO2 {
    private Long bookId;
    private String title;
    private double price;
    private int amount;
    private boolean isDeleted;
    private List<AuthorDTO> authors;
    private List<CategoryDTO> categories;


    public BookDTO2() {
        authors = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public BookDTO2(Long bookId, String title, double price, int amount, boolean isDeleted) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

    public BookDTO2(Long bookId, String title, double price, int amount, boolean isDeleted,
                   List<AuthorDTO> authors, List<CategoryDTO> categories) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
        this.authors = authors;
        this.categories = categories;
    }


    public Long getBookId() {
        return bookId;
    }


    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }


    public int getAmount() {
        return amount;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "BookDTO2 {" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", isDeleted=" + isDeleted +
                ", authors=" + authors +
                ", categories=" + categories +
                '}';
    }
}
