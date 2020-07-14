package com.nikolas.master_thesis.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AuthorDTO {

    Long authorId;
    String firstName;
    String lastName;

}
