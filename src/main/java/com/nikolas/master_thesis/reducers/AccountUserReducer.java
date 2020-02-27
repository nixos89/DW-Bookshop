package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Account;
import com.nikolas.master_thesis.core.UserA;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

/* Reducer class created as an example */
public class AccountUserReducer implements LinkedHashMapRowReducer<Integer, Account> {

    @Override
    public void accumulate(Map<Integer, Account> container, RowView rowView) {
        final Account account = container.computeIfAbsent(
                rowView.getColumn("a_id", Integer.class),
                id -> rowView.getRow(Account.class));

        if (rowView.getColumn("u_id", Integer.class) != null) {
            UserA userA = rowView.getRow(UserA.class);
            userA.setAccount(account); // might need to save to final UserA variable due to concurrent access
            account.addUser(userA);
        }

    }

}
