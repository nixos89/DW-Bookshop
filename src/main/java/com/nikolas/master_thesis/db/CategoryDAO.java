package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.Category;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface CategoryDAO {

    @RegisterBeanMapper(Category.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Category (category_id BIGSERIAL PRIMARY KEY, name VARCHAR(30), is_deleted boolean)")
    public void createCategoryTable();

    @RegisterBeanMapper(Category.class)
    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Category_Book(" +
            " category_id INTEGER REFERENCES Category(category_id) ON UPDATE CASCADE," +
            " book_id INTEGER REFERENCES Book(book_id) ON UPDATE CASCADE," +
            " CONSTRAINT Category_Book_pkey PRIMARY KEY (category_id, book_id) )")
    public void createTableBookCategory();

}
