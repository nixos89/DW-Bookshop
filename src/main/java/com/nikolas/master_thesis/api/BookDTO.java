package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class BookDTO {
    private Long bookId;
    private String title;
    private double price;
    private int amount;
    private boolean deleted;
    private List<Long> authors;
    private List<Long> categories;

    public BookDTO() {
        authors = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public BookDTO(Long bookId, String title, double price, int amount, boolean deleted) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.deleted = deleted;
    }

    public BookDTO(Long bookId, String title, double price, int amount, boolean deleted,
                   List<Long> authors, List<Long> categories) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.deleted = deleted;
        this.authors = authors;
        this.categories = categories;
    }

    @JsonProperty("book_id")
    public Long getBookId() {
        return bookId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("price")
    public double getPrice() {
        return price;
    }

    @JsonProperty("amount")
    public int getAmount() {
        return amount;
    }

    @JsonProperty("deleted")
    public boolean isDeleted() {
        return deleted;
    }

    @JsonProperty("author_ids")
    public List<Long> getAuthors() {
        return authors;
    }

    @JsonProperty("category_ids")
    public List<Long> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", isDeleted=" + deleted +
                '}';
    }
}
