package com.nikolas.master_thesis.core;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"books"})
public class Author {

    private Long authorId;
    private String firstName;
    private String lastName;
    private Set<Book> books;

    public Author(Long authorId, String firstName, String lastName) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
