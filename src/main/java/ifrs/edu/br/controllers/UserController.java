package ifrs.edu.br.controllers;

import ifrs.edu.br.dao.UserDAO;

/**
 * UserController
 */
public class UserController {
    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
