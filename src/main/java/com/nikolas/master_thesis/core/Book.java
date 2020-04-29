package com.nikolas.master_thesis.core;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"authors", "categories", "orderItems"})
public class Book {

    private Long bookId;
    private String title;
    private double price;
    private int amount;
    private boolean isDeleted;
    private Set<Author> authors;
    private Set<Category> categories;
    private Set<OrderItem> orderItems;

    public Book(Long bookId, String title, double price, int amount, boolean isDeleted) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.isDeleted = isDeleted;
    }

}
