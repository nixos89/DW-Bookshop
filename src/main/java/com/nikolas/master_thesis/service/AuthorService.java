package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.db.AuthorDAO;
import com.nikolas.master_thesis.util.StoreException;
import org.apache.http.HttpStatus;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    private final AuthorDAO authorDAO;

    public AuthorService(Jdbi jdbi) {
        this.authorDAO = jdbi.onDemand(AuthorDAO.class);
        authorDAO.createTableAuthor();
        authorDAO.createTableAuthorBook();
    }

    public AuthorDTO getAuthorById(Long authorId) {
        Author author = authorDAO.getAuthorById(authorId);
        if (author != null) {
            return new AuthorDTO(author.getAuthorId(), author.getFirstName(), author.getLastName());
        } else {
            return null;
        }
    }

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorDAO.getAllAuthorPojos();
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        for (Author a : authors) {
            AuthorDTO authorDTO = new AuthorDTO(a.getAuthorId(), a.getFirstName(), a.getLastName());
            authorDTOList.add(authorDTO);
        }
        return authorDTOList;
    }

    public boolean saveAuthor(AuthorDTO authorDTO) {
        Author author = authorDAO.createAuthor(authorDTO.getFirstName(), authorDTO.getLastName());
        return author != null;
    }

    public boolean updateAuthor(AuthorDTO authorDTO, Long authorId) {
        Author searchedAuthor = authorDAO.getAuthorById(authorId);
        if (searchedAuthor != null) {
            return authorDAO.updateAuthor(authorId, authorDTO.getFirstName(), authorDTO.getLastName());
        } else {
            return false;
        }
    }

    public boolean deleteAuthor(Long authorId) {
        Author author = authorDAO.getAuthorById(authorId);
        if (author != null) {
            return authorDAO.deleteAuthor(authorId);
        } else {
            throw new StoreException("Exception, author for id = " + authorId + " does NOT exist!", HttpStatus.SC_NOT_FOUND);
        }
    }


}
