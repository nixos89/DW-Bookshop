package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.BookDTO;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.mapper.BookDTOMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface BookDAO {

    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Book( book_id BIGSERIAL PRIMARY KEY, title VARCHAR(30), price double precision, amount INTEGER, is_deleted boolean)")
    public void createBookTable();


    @UseRowMapper(BookDTOMapper.class)
    @SqlUpdate("INSERT INTO Book(title, price, amount, is_deleted) VALUES(:title, :price, :amount, :is_deleted)")
    @GetGeneratedKeys
    public BookDTO createBook(@Bind("title") String title, @Bind("price")double price, @Bind("amount")int amount, @Bind("is_deleted")boolean is_deleted);


    @UseRowMapper(BookDTOMapper.class)
    @SqlQuery("SELECT book_id, title, price, amount, is_deleted FROM Book")
    public List<BookDTO> getAllBooks(); // TODO: refactor this method so it uses AuthorBookReducer, CategoryBookReducer, OrderItemReduces and RegisterBeanMappers


    @UseRowMapper(BookDTOMapper.class)
    @SqlQuery("SELECT book_id, title, price, amount, is_deleted FROM Book WHERE Book.book_id = ?")
    public BookDTO getBookById(Long book_id); // TODO: refactor this method so it uses AuthorBookReducer, CategoryBookReducer, OrderItemReduces and RegisterBeanMappers

}
