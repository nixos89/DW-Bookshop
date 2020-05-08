package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.core.User;
import com.nikolas.master_thesis.db.UserDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class UserService {

    private final Jdbi jdbi;


    public UserService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public User getUserByUserName(String username) {
        Handle handle = jdbi.open();
        try {
            handle.begin();
            handle.getConnection().setAutoCommit(false);
            UserDAO userDAO = handle.attach(UserDAO.class);
            User user = userDAO.findUserByUsername(username);
            if (user != null) {
                handle.commit();
                return user;
            } else {
                throw new Exception("Error, user does NOT exist in database by username: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle.rollback();
            return null;
        } finally {
            handle.close();
        }
    }

}
