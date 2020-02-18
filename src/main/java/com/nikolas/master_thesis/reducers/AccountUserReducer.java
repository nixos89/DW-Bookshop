package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Account;
import com.nikolas.master_thesis.core.UserA;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AccountUserReducer implements LinkedHashMapRowReducer<Integer, Account> {
    //private static final Logger LOGGER = LoggerFactory.getLogger(AccountUserReducer.class);

    @Override
    public void accumulate(Map<Integer, Account> container, RowView rowView) {
        final Account account = container.computeIfAbsent(
                rowView.getColumn("a_id", Integer.class),
                id -> rowView.getRow(Account.class));
        //LOGGER.info("Account (in accumulate method)" + account.toString());

        if (rowView.getColumn("u_id", Integer.class) != null) {
            UserA userA = rowView.getRow(UserA.class);
            userA.setAccount(account); // might need to save to final UserA variable due to concurrent access
            //LOGGER.info("UserA (in accumulate method)" + userA.toString());
            account.addUser(userA);
        }

    }

}
