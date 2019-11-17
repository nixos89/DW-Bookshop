package com.nikolas.master_thesis.examples;

import com.nikolas.master_thesis.core.Account;
import com.nikolas.master_thesis.core.UserA;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.core.result.ResultIterable;
import org.jdbi.v3.core.statement.Query;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HandleJdbiExamples {

    public static void perform(Jdbi jdbi){
        // Debugging::START
        Handle handle = jdbi.open();
        Query queryAllUsers = handle.createQuery("SELECT * FROM Users");
        ResultIterable resultIterable = queryAllUsers.mapToMap();
        Iterator iterator = resultIterable.iterator();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        while (iterator.hasNext()) {
            System.out.println("UserA: " + iterator.next());
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println("******************************************************");
        queryAllUsers.mapToMap().list().forEach(map -> map.entrySet().forEach( stringObjectEntry -> System.out.println(stringObjectEntry.getKey() + " : " + stringObjectEntry.getValue())));
        System.out.println("******************************************************");
        // Debugging::END

        // ************************************** List of accounts::START ************************************** //
        String SQL_JOIN_Account_User_By_AccountId = "SELECT a.id a_id, a.name a_name, u.id u_id, u.name u_name " +
                "FROM Account a " +
                "LEFT JOIN Users u ON u.account_id = a.id ";
//               + "WHERE a.id = :id";

        List<Account> accounts = handle.createQuery(SQL_JOIN_Account_User_By_AccountId)
//                .bind("id", 1) // to query by certain id
                .registerRowMapper(BeanMapper.factory(Account.class, "a"))
                .registerRowMapper(BeanMapper.factory(UserA.class, "u"))
                .reduceRows(new LinkedHashMap<Integer, Account>(),
                        (map, rowView) -> {
                            Account account = map.computeIfAbsent(
                                    rowView.getColumn("a_id", Integer.class),
                                    id -> rowView.getRow(Account.class));

                            if (rowView.getColumn("u_id", Integer.class) != null) {
                                UserA userA = rowView.getRow(UserA.class);
                                userA.setAccount(account);
                                account.addUser(userA);
                            }
                            return map;
                        })
                .values()
                .stream()
                .collect(Collectors.toList());

        System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        for(Account account: accounts)
            System.out.println(account);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        // ************************************** List of accounts::END **************************************** //

        // ************************************** Single account::START **************************************** //
        Optional<Account> account = handle.createQuery(SQL_JOIN_Account_User_By_AccountId)
//                .bind("id", 1)
                .registerRowMapper(BeanMapper.factory(Account.class, "a"))
                .registerRowMapper(BeanMapper.factory(UserA.class, "u"))
                .<Integer, Account>reduceRows((map, rowView) -> {
                    Account account2 = map.computeIfAbsent(rowView.getColumn("a_id", Integer.class),
                            id -> rowView.getRow(Account.class));

                    if (rowView.getColumn("u_id", Integer.class) != null) {
                        UserA userA = rowView.getRow(UserA.class);
                        userA.setAccount(account2);
                        account2.addUser(userA);
                    }
                }).findFirst();
        System.out.println("\n*******************************************************");
        System.out.println("Account: " + account.get().toString());
        System.out.println("*******************************************************\n");
        // ************************************** Single account::END ****************************************** //
    }
}
