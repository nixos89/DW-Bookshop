package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Role;
import com.nikolas.master_thesis.core.User;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class UserRoleReducer implements LinkedHashMapRowReducer<Long, User> {

    @Override
    public void accumulate(Map<Long, User> container, RowView rowView) {
        User user = container.computeIfAbsent(
                rowView.getColumn("u_user_id", Long.class),
                id -> rowView.getRow(User.class));

        if (rowView.getColumn("r_role_id", Long.class) != null) {
            user.setRole(rowView.getRow(Role.class));
        }
    }
}
