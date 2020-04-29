package com.nikolas.master_thesis.service;

import com.nikolas.master_thesis.db.RoleDAO;
import com.nikolas.master_thesis.db.UserDAO;
import org.jdbi.v3.core.Jdbi;

public class UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    public UserService(Jdbi jdbi) {
        this.roleDAO = jdbi.onDemand(RoleDAO.class);
        this.userDAO = jdbi.onDemand(UserDAO.class);
        roleDAO.createTableRole();
        userDAO.createUserTable();
    }
}
