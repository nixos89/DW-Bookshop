package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.CategoryDTO;
import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.mapper.CategoryDTOMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

public interface CategoryDAO {

    @RegisterBeanMapper(Category.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Category (category_id BIGSERIAL PRIMARY KEY, name VARCHAR(30), is_deleted boolean)")
    void createCategoryTable();

    @RegisterBeanMapper(Category.class)
    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Category_Book(" +
            " category_id INTEGER REFERENCES Category(category_id) ON UPDATE CASCADE ON DELETE CASCADE," +
            " book_id INTEGER REFERENCES Book(book_id) ON UPDATE CASCADE ON DELETE CASCADE," +
            " CONSTRAINT Category_Book_pkey PRIMARY KEY (category_id, book_id) )")
    void createTableBookCategory();

    @UseRowMapper(CategoryDTOMapper.class)
    @SqlQuery("SELECT category_id, name, is_deleted FROM category WHERE category_id = ?")
    CategoryDTO getCategoryById(Long categoryId);
}
