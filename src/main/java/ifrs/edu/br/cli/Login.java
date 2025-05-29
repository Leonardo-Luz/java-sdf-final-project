package ifrs.edu.br.cli;

import java.util.Scanner;

import javax.persistence.EntityManager;

import ifrs.edu.br.context.Auth;

/**
 * Login
 */
public class Login {
    public static void command(EntityManager entityManager) {
        if (Auth.isLogged()) {
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
        Auth.login(entityManager, email, password);
    }
}
