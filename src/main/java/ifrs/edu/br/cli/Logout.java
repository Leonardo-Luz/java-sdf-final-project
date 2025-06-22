package ifrs.edu.br.cli;

import ifrs.edu.br.controllers.UserController;

/**
 * Logout
 */
public class Logout {
    public static void command() {
        UserController userController = new UserController();

        userController.logout();
        System.out.println("Logout Successfully!");
    }
}
