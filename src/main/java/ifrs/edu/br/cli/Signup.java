package ifrs.edu.br.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.models.User;
import ifrs.edu.br.utils.FileManager;

/**
 * Signup
 */
public class Signup {
    public static void command(EntityManager entityManager) {
        FileManager fileManager = new FileManager();
        UserDAO userDAO = new UserDAO(entityManager);
        UserController userController = new UserController(userDAO, fileManager, new BCryptPasswordEncoder());
        User user = userController.verify();
        if (user != null) {
            System.out.println("Already logged in");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("> email");
        System.out.print("> ");
        String email = scanner.nextLine();

        System.out.println("> name");
        System.out.print("> ");
        String name = scanner.nextLine();

        System.out.println("> password");
        System.out.print("> ");
        String password = scanner.nextLine();

        System.out.println("> birthday (dd/MM/yyyy)");
        System.out.print("> ");
        String birthdayInput = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate birthday = null;
        try {
            birthday = LocalDate.parse(birthdayInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            scanner.close();
            return;
        }

        if (birthday == null) {
            System.out.println("Invalid date.");
            scanner.close();
            return;
        }

        try {
            user = new User(email, name, password, birthday);
        } catch (RuntimeException e) {
            System.out.println(e);
        }

        userController.signup(user);
        scanner.close();
    }
}
