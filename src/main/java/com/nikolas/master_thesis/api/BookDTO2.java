package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonFilter("myFilter")
public class BookDTO2 {
    private Long bookId;
    private String title;
    private double price;
    private int amount;
    private boolean isDeleted;
    private List<AuthorDTO> authors; // Many-To-Many
    private List<CategoryDTO> categories; // Many-To-Many
    private List<OrderItemDTO> orderItems; // One-To-Many

    public BookDTO2() {
        authors = new ArrayList<>();
        categories = new ArrayList<>();
        orderItems = new ArrayList<>();
    }

    public BookDTO2(String title, double price, int amount, boolean isDeleted) {
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

    public BookDTO2(Long bookId, String title, double price, int amount, boolean isDeleted) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

    public BookDTO2(Long bookId, String title, double price, int amount, boolean isDeleted,
                    List<AuthorDTO> authors) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
        this.authors = authors;
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

    public BookDTO2(Long bookId, String title, double price, int amount, boolean isDeleted,
                    List<AuthorDTO> authors, List<CategoryDTO> categories, List<OrderItemDTO> orderItems) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
        this.authors = authors;
        this.categories = categories;
        this.orderItems = orderItems;
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

    @JsonProperty("author_list")
    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    @JsonProperty("category_list")
    public List<CategoryDTO> getCategories() {
        return categories;
    }

    @JsonProperty("order_item_list")
    public List<OrderItemDTO> getOrderItems() {
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
