package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Author;
import com.nikolas.master_thesis.core.Book;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class AuthorBookReducer implements LinkedHashMapRowReducer<Long, Book> {

    @Override
    public void accumulate(Map<Long, Book> container, RowView rowView) {
        // TODO: implement AuthorBookReducer accumulate!!!!
        Book book = container.computeIfAbsent(
                rowView.getColumn("book_id", Long.class),
                id -> rowView.getRow(Book.class));

        if(rowView.getColumn("author_id", Long.class) != null ){
            Author author = rowView.getRow(Author.class);
            author.getBooks().add(book);
            book.getAuthors().add(author);
        }
    }

}
