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
        try {
            AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
            handle.begin();
            Author author = authorDAO.getAuthorById(authorId);
            handle.commit();
            if (author != null) {
                return new AuthorDTO(author.getAuthorId(), author.getFirstName(), author.getLastName());
            } else {
                return null;
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
        try {
            AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
            handle.begin();
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
                handle.rollback();
                return null;
            }
        } catch (Exception e) {
            handle.rollback();
            e.printStackTrace();
            return null;
        } finally {
            handle.close();
        }
    }


    public boolean saveAuthor(AuthorDTO authorDTO) {
        Handle handle = jdbi.open();
        try {
            AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
            handle.begin();
            boolean createdAut = authorDAO.createAuthor(authorDTO.getFirstName(), authorDTO.getLastName());
            if (createdAut) {
                handle.commit();
                return true;
            } else {
                handle.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
        } finally {
            handle.close();
        }
        return false;
    }

    public boolean updateAuthor(AuthorDTO authorDTO, Long authorId) {
        Handle handle = jdbi.open();
        try {
            AuthorDAO authorDAO = handle.attach(AuthorDAO.class);
            Author searchedAuthor = authorDAO.getAuthorById(authorId);
            handle.begin();
            if (searchedAuthor != null) {
                boolean isUpdatedAuthor = authorDAO.updateAuthor(authorId, authorDTO.getFirstName(),
                        authorDTO.getLastName());
                if (isUpdatedAuthor) {
                    handle.commit();
                    return true;
                } else {
                    handle.rollback();
                    return false;
                }
            } else {
                handle.rollback();
                return false;
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
            Author author = authorDAO.getAuthorById(authorId);
            if (author != null) {
                if (authorDAO.deleteAuthor(authorId)) {
                    handle.commit();
                    return true;
                } else {
                    handle.rollback();
                    return false;
                }
            } else {
                handle.rollback();
                return false;
            }
        } catch (Exception e) {
            handle.rollback();
            e.printStackTrace();
            return false;
        } finally {
            handle.close();
        }


    }

}
