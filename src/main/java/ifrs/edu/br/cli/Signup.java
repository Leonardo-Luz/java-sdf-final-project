package ifrs.edu.br.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import javax.persistence.EntityManager;

import ifrs.edu.br.context.Auth;
import ifrs.edu.br.models.User;

/**
 * Signup
 */
public class Signup {
    public static void command(EntityManager entityManager) {
        if (Auth.isLogged()) {
            System.out.println("Already logged in.");
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

        User user = new User(email, name, password, birthday);

        Auth.signup(entityManager, user);
        scanner.close();
    }
}
