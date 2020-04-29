package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.Category;
import com.nikolas.master_thesis.mapper.CategoryMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface CategoryDAO {

    @RegisterBeanMapper(Category.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Category (category_id BIGSERIAL PRIMARY KEY, name VARCHAR(30), is_deleted boolean)")
    void createCategoryTable();

    @RegisterBeanMapper(Category.class)
    @RegisterBeanMapper(Book.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Category_Book(" +
            " category_id BIGINT REFERENCES Category(category_id) ON UPDATE CASCADE ON DELETE CASCADE," +
            " book_id BIGINT REFERENCES Book(book_id) ON UPDATE CASCADE ON DELETE CASCADE," +
            " CONSTRAINT Category_Book_pkey PRIMARY KEY (category_id, book_id) )")
    void createTableBookCategory();


    @UseRowMapper(CategoryMapper.class)
    @SqlQuery("SELECT category_id, name, is_deleted FROM category WHERE category_id = ?")
    Category getCategoryById(Long categoryId);


    @UseRowMapper(CategoryMapper.class)
    @SqlQuery("SELECT category_id, name, is_deleted FROM category")
    List<Category> getAllCategories2();


    @UseRowMapper(CategoryMapper.class)
    @SqlQuery("SELECT c.category_id, c.name, c.is_deleted FROM category AS c " +
            "LEFT JOIN category_book AS cb ON c.category_id = cb.category_id " +
            "LEFT JOIN book AS b ON cb.book_id = b.book_id " +
            "WHERE b.book_id = :bId")
    List<Category> getCategoriesByBookId(@Bind("bId") Long bId);

    @GetGeneratedKeys
    @UseRowMapper(CategoryMapper.class)
    @SqlUpdate("INSERT INTO Category(name, is_deleted) VALUES(?, ?)")
    boolean createCategory(String name, boolean isDeleted);

    @SqlUpdate("UPDATE Category SET name = :name, is_deleted = :is_deleted WHERE category_id = :category_id")
    boolean updateCategory(@Bind("category_id") Long catId, @Bind("name") String firstName, @Bind("is_deleted") Boolean isDeleted);

    @SqlUpdate("DELETE FROM category WHERE category_id = :category_id")
    boolean deleteCategory(@Bind("category_id") Long categoryId);
}
