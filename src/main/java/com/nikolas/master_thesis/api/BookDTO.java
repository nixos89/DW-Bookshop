package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class BookDTO {
    Long bookId;
    String title;
    double price;
    int amount;
    @JsonProperty("isDeleted")
    boolean deleted;
    List<AuthorDTO> authors;
    List<CategoryDTO> categories;


}
