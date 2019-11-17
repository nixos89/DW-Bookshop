package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Author;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class AuthorCategoryReducer implements LinkedHashMapRowReducer<Long, Author> {

    @Override
    public void accumulate(Map<Long, Author> container, RowView rowView) {

    }
}
