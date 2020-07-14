package com.nikolas.master_thesis.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateAuthorDTO {
    Long authorId;
    String firstName;
    String lastName;
}
