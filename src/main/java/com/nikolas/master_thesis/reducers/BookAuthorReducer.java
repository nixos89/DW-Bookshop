package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.core.Book;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class BookAuthorReducer implements LinkedHashMapRowReducer<Long, Book> {

    @Override
    public void accumulate(Map<Long, Book> container, RowView rowView) {
        final Book book = container.computeIfAbsent(
                rowView.getColumn("b_id", Long.class),
                id -> rowView.getRow(Book.class));

        if (rowView.getColumn("a_id", Long.class) != null) {
            Author author = rowView.getRow(Author.class);
            author.getBooks().add(book);
            book.getAuthors().add(author);
        }

    }
}
