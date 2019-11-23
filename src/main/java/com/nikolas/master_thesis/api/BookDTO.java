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
    private List<Long> authors; // TODO: research HOW TO implement ManyToMany relationships (next 2 collections) with JDBi3 - Step2
    private List<Long> categories; // same TODO as above
    private List<Long> orderItems; // TODO: research HOW TO implement OneToMany relationship with JDBi3 - Step1

    public BookDTO() {
        authors = new ArrayList<>();
        categories = new ArrayList<>();
        orderItems = new ArrayList<>();
    }

    public BookDTO(String title, double price, int amount, boolean isDeleted) {
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

    public BookDTO(Long bookId, String title, double price, int amount, boolean isDeleted) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

    public BookDTO(Long bookId, String title, double price, int amount, boolean isDeleted,
                   List<Long> authors) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
        this.authors = authors;
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
