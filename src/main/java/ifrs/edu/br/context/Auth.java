package ifrs.edu.br.context;

import ifrs.edu.br.models.User;
import ifrs.edu.br.dao.UserDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Auth
 */
public class Auth {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String AUTH_PATH = System.getProperty("user.home") + "/.config/.reviewauth.pass";

    public static void login(EntityManager entityManager, String email, String password) {
        if (isLogged()) {
            System.out.println("Already logged in.");
            return;
        }

        UserDAO userDAO = new UserDAO(entityManager);
        User user = userDAO.findByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Error: Password or Email invalid");
            return;
        }

        try (FileWriter writer = new FileWriter(AUTH_PATH)) {
            writer.write(email);
            writer.write("\n");
            writer.write(password);
            System.out.println("Login successful.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signup(EntityManager entityManager, User user) {
        if (isLogged()) {
            System.out.println("Already logged in.");
            return;
        }
        String rawPassword = user.getPassword();

        UserDAO userDAO = new UserDAO(entityManager);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userDAO.insert(user);

        try (FileWriter writer = new FileWriter(AUTH_PATH)) {
            writer.write(user.getEmail());
            writer.write("\n");
            writer.write(rawPassword);
            System.out.println("Signup successful.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logout() {
        File file = new File(AUTH_PATH);
        if (!file.exists()) {
            System.out.println("Not logged in.");
            return;
        }

        if (file.delete()) {
            System.out.println("Logged out successfully.");
        } else {
            System.out.println("Logout failed.");
        }
    }

    public static User verify(EntityManager entityManager) {
        try {
            File file = new File(AUTH_PATH);
            if (!file.exists())
                return null;

            Scanner scanner = new Scanner(file);
            if (!scanner.hasNextLine()) {
                scanner.close();
                System.out.println("Verification failed, loggin out...");
                return null;
            }

            String email = scanner.nextLine().trim();

            if (!scanner.hasNextLine()) {
                scanner.close();
                System.out.println("Verification failed, loggin out...");
                logout();
                return null;
            }

            String password = scanner.nextLine().trim();
            scanner.close();

            UserDAO userDAO = new UserDAO(entityManager);
            User user = userDAO.findByEmail(email);

            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("Verification failed, loggin out...");
                logout();
                return null;
            }

            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isLogged() {
        return new File(AUTH_PATH).exists();
    }
}
