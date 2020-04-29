package com.nikolas.master_thesis.core;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"books"})
public class Category {

    private Long categoryId;
    private String name;
    private boolean isDeleted;
    private Set<Book> books;

    public Category(Long categoryId, String name, boolean isDeleted) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDeleted = isDeleted;
    }

}
