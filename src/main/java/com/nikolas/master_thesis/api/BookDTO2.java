package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BookDTO2 {
    Long bookId;
    String title;
    double price;
    int amount;
    boolean isDeleted;
    List<AuthorDTO> authors;
    List<CategoryDTO> categories;

//    public BookDTO2(Long bookId, String title, double price, int amount, boolean isDeleted) {
//        this.bookId = bookId;
//        this.title = title;
//        this.price = price;
//        this.amount = amount;
//        this.isDeleted = isDeleted;
//    }
}
