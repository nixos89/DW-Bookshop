package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.db.AuthorDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

// TODO: test all methods
public class AuthorService {
    //    private final AuthorDAO authorDAO;
    private final Jdbi jdbi;

    public AuthorService(Jdbi jdbi) {
        this.jdbi = jdbi;
//        this.authorDAO = jdbi.onDemand(AuthorDAO.class);
//        authorDAO.createTableAuthor();
//        authorDAO.createTableAuthorBook();
    }

    public AuthorDTO getAuthorById(Long authorId) {
        Handle handle = jdbi.open();
        AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            Author author = authorDAO.getAuthorById(authorId);
            if (author != null) {
                handle.commit();
                return new AuthorDTO(author.getAuthorId(), author.getFirstName(), author.getLastName());
            } else {
                throw new Exception("Error, author with id = " + authorId + " does NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }


    public List<AuthorDTO> getAllAuthors() {
        Handle handle = jdbi.open();
        AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            List<Author> authors = authorDAO.getAllAuthorPojos();
            handle.commit();
            if (authors != null && !authors.isEmpty()) {
                List<AuthorDTO> authorDTOList = new ArrayList<>();
                for (Author a : authors) {
                    AuthorDTO authorDTO = new AuthorDTO(a.getAuthorId(), a.getFirstName(), a.getLastName());
                    authorDTOList.add(authorDTO);
                }
                return authorDTOList;
            } else {
                throw new Exception("Error, authors are NULL or they do NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }


    public boolean saveAuthor(AuthorDTO authorDTO) {
        Handle handle = jdbi.open();
        AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            boolean createdAut = authorDAO.createAuthor(authorDTO.getFirstName(), authorDTO.getLastName());
            if (createdAut) {
                handle.commit();
                return true;
            } else {
                throw new Exception("Error, author has not been saved!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }

    public boolean updateAuthor(AuthorDTO authorDTO, Long authorId) {
        Handle handle = jdbi.open();
        AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            Author searchedAuthor = authorDAO.getAuthorById(authorId);
            if (searchedAuthor != null) {
                boolean isUpdatedAuthor = authorDAO.updateAuthor(authorId, authorDTO.getFirstName(),
                        authorDTO.getLastName());
                if (isUpdatedAuthor) {
                    handle.commit();
                    return true;
                } else {
                    throw new Exception("Error, author has not been updated!");
                }
            } else {
                throw new Exception("Error, author with id = " + authorId + " does NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }

    public boolean deleteAuthor(Long authorId) {
        Handle handle = jdbi.open();
        try {
            AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            Author author = authorDAO.getAuthorById(authorId);
            if (author != null) {
                if (authorDAO.deleteAuthor(authorId)) {
                    handle.commit();
                    return true;
                } else {
                    throw new Exception("Error, author with id = " + authorId + " has NOT been deleted!");
                }
            } else {
                throw new Exception("Error, author with id = " + authorId + " does NOT exist in database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return false;
        } finally {
            handle.close();
        }
    }

}
