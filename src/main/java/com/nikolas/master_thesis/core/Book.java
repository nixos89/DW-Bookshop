package com.nikolas.master_thesis.core;

import java.util.Objects;
import java.util.Set;


public class Book {

    private Long bookId;
    private String title;
    private double price;
    private int amount;
    private boolean isDeleted;
    private Set<Author> authors; // TODO: research HOW TO implement Many-To-Many relationships (next 2 collections) with JDBi3 - Step2
    private Set<Category> categories; // same as TODO above (Many-To-Many)
    private Set<OrderItem> orderItems; // TODO: research HOW TO implement One-To-Many relationship with JDBi3 - Step1

    public Book() {
    }

    public Book(String title, double price, int amount, boolean isDeleted) {
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

    public Book(Long bookId, String title, double price, int amount, boolean isDeleted) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(book.price, price) == 0 &&
                amount == book.amount &&
                isDeleted == book.isDeleted &&
                bookId.equals(book.bookId) &&
                title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, price, amount, isDeleted);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
