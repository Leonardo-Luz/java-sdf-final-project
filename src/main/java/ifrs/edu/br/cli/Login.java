package ifrs.edu.br.cli;

import java.util.Scanner;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.models.User;
import ifrs.edu.br.utils.FileManager;

/**
 * Login
 */
public class Login {
    public static void command(EntityManager entityManager) {
        UserController userController = new UserController(new UserDAO(entityManager), new FileManager(),
                new BCryptPasswordEncoder());
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
