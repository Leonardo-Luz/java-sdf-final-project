package ifrs.edu.br.cli;

import java.util.Scanner;

import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.models.User;

/**
 * Login
 */
public class Login {
    public static void command() {
        UserController userController = new UserController();
        User user = userController.verify();

        if (user != null) {
            System.out.println("Already logged in.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("> email");
        System.out.print("> ");
        String email = scanner.nextLine();

        System.out.println("> password");
        System.out.print("> ");
        String password = scanner.nextLine();

        scanner.close();
        userController.loginHandler(email, password);
    }
}
