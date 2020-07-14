package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class CategoryDTO {

    Long categoryId;
    String name;
    @JsonProperty("isDeleted")
    boolean deleted;

}
