package com.nikolas.master_thesis.api;

/* contains getter methods, but NOT ANY setter methods
 * due to it's Thread safety */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AuthorDTO {

    private Long authorId;

    private String firstName;

    private String lastName;

    private List<BookDTO> books;

    public AuthorDTO() {
        books = new ArrayList<>();
    }

    public AuthorDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorDTO(Long authorId, String firstName, String lastName) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorDTO(Long authorId, String firstName, String lastName, List<BookDTO> books) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    @JsonProperty("author_id")
    public Long getAuthorId() {
        return authorId;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("books")
    public List<BookDTO> getBooks() {
        return books;
    }
}
