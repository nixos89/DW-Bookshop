package com.nikolas.master_thesis.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthorListDTO {

    List<AuthorDTO> authors;

    public AuthorListDTO() {
        this.authors = new ArrayList<>();
    }
}
