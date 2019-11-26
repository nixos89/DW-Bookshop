package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class BookDTO {
    private Long bookId;
    private String title;
    private double price;
    private int amount;
    private boolean isDeleted;
    private List<Long> authors = new ArrayList<>(); // Many-To-Many
    private List<Long> categories = new ArrayList<>(); // Many-To-Many
    private List<Long> orderItems = new ArrayList<>(); // One-To-Many

    public BookDTO() {
    }

    public BookDTO(Long bookId, String title, double price, int amount, boolean isDeleted) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

    public BookDTO(Long bookId, String title, double price, int amount, boolean isDeleted,
                   List<Long> authors, List<Long> categories) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
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

    @JsonProperty("is_deleted")
    public boolean getIsDeleted() {
        return isDeleted;
    }

    @JsonProperty("author_ids")
    public List<Long> getAuthors() {
        return authors;
    }

    @JsonProperty("category_ids")
    public List<Long> getCategories() {
        return categories;
    }

    @JsonProperty("order_item_ids")
    public List<Long> getOrderItems() {
        return orderItems;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
