package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class BookDTO {
    private Long bookId;
    private String title;
    private double price;
    private int amount;
    private boolean isDeleted;
    private Set<Long> authors; // TODO: research HOW TO implement ManyToMany relationships (next 2 collections) with JDBi3 - Step2
    private Set<Long> categories; // same TODO as above
    private Set<Long> orderItems; // TODO: research HOW TO implement OneToMany relationship with JDBi3 - Step1

    public BookDTO() {
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
    public boolean isDeleted() {
        return isDeleted;
    }

//    @JsonProperty("author_ids")
//    public Set<Long> getAuthors() {
//        return authors;
//    }
//
//    @JsonProperty("category_ids")
//    public Set<Long> getCategories() {
//        return categories;
//    }
//
//    @JsonProperty("order_items_ids")
//    public Set<Long> getOrderItems() {
//        return orderItems;
//    }


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
