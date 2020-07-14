package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateBookDTO {

    private Long bookId;
    private String title;
    private double price;
    private int amount;
    @JsonProperty("isDeleted")
    private boolean deleted;
    private List<Long> authors;
    private List<Long> categories;

}
