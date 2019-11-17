package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.User;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class UserRoleReducer implements LinkedHashMapRowReducer<Long, User> {

    @Override
    public void accumulate(Map<Long, User> container, RowView rowView) {
        // TODO: implement proper RowReducing!!!
    }
}
