package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateCategoryDTO {

    Long categoryId;
    String name;
    @JsonProperty("isDeleted")
    boolean deleted;

}
