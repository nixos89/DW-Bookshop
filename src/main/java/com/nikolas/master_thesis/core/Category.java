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
    private boolean deleted;
    private Set<Book> books;

    public Category(Long categoryId, String name, boolean deleted) {
        this.categoryId = categoryId;
        this.name = name;
        this.deleted = deleted;
    }

}
