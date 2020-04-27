package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.AuthorDTO;
import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.mapper.AuthorDTOMapper;
import com.nikolas.master_thesis.mapper.AuthorMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface AuthorDAO {

    @RegisterBeanMapper(Author.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Author(author_id BIGSERIAL PRIMARY KEY, first_name VARCHAR(30), last_name VARCHAR(30) )")
    void createTableAuthor();

    @RegisterBeanMapper(Author.class)
    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Author_Book (author_id BIGINT REFERENCES Author(author_id) ON UPDATE CASCADE ON DELETE CASCADE," +
            " book_id BIGINT REFERENCES Book(book_id) ON UPDATE CASCADE ON DELETE CASCADE," +
            " CONSTRAINT Author_Book_pkey PRIMARY KEY (author_id, book_id) )")
    void createTableAuthorBook();


    @GetGeneratedKeys
    @UseRowMapper(AuthorMapper.class) // TODO: Implement map() method!!!
    @SqlUpdate("INSERT INTO Author(first_name, last_name) VALUES(?, ?) ")
    Author createAuthor(String firstName, String lastName); // switched return type from AuthorDTO to boolean

    @UseRowMapper(AuthorMapper.class)
    @SqlQuery("SELECT author_id, first_name, last_name FROM Author WHERE Author.author_id = ?")
    Author getAuthorById(Long authorId);

    @UseRowMapper(AuthorDTOMapper.class)
    @SqlQuery("SELECT author_id, first_name, last_name FROM Author")
    List<AuthorDTO> getAllAuthors();

    @UseRowMapper(AuthorMapper.class)
    @SqlQuery("SELECT author_id, first_name, last_name FROM Author")
    List<Author> getAllAuthorPojos();

    @SqlUpdate("UPDATE Author SET first_name = :first_name, last_name = :last_name WHERE author_id = :author_id")
    boolean updateAuthor(@Bind("author_id") Long authorId, @Bind("first_name") String firstName, @Bind("last_name") String lastName);


    @SqlUpdate("DELETE FROM author WHERE author_id = :author_id")
    boolean deleteAuthor(@Bind("author_id") Long authorId);

}
