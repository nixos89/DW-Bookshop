package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.db.AuthorDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jvnet.hk2.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

@Contract
public interface AuthorService {

    AuthorDTO getAuthorById(Long authorId);

    List<AuthorDTO> getAllAuthors();

    boolean saveAuthor(AuthorDTO authorDTO);

    boolean updateAuthor(AuthorDTO authorDTO, Long authorId);

    boolean deleteAuthor(Long authorId);

}
