package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddUpdateBookDTO {
    private Long bookId;
    private String title;
    private double price;
    private int amount;
    @JsonProperty("deleted")
    private boolean isDeleted;
    private List<Long> authors;
    private List<Long> categories;

    /*
    public AddUpdateBookDTO() {
        authors = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public AddUpdateBookDTO(Long bookId, String title, double price, int amount, boolean isDeleted,
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

    @JsonProperty("authors")
    public List<Long> getAuthors() {
        return authors;
    }

    @JsonProperty("categories")
    public List<Long> getCategories() {
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

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setAuthors(List<Long> authors) {
        this.authors = authors;
    }

    public void setCategories(List<Long> categories) {
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

     */
}
