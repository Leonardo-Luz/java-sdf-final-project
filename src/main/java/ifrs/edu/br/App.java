package ifrs.edu.br;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ifrs.edu.br.cli.Profile;
import ifrs.edu.br.cli.Achievements;
import ifrs.edu.br.cli.Add;
import ifrs.edu.br.cli.Books;
import ifrs.edu.br.cli.Help;
import ifrs.edu.br.cli.Like;
import ifrs.edu.br.cli.Login;
import ifrs.edu.br.cli.Reviews;
import ifrs.edu.br.cli.Signup;
import ifrs.edu.br.cli.Version;

import ifrs.edu.br.context.Auth;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Hello world!
 */
public class App {
    public static EntityManager entityManager = null;
    public static EntityManagerFactory entityManagerFactory = null;

    public static void main(String[] args) {
        if (args.length == 0) {
            Version.command();
            System.out.println();
            Help.command();
            return;
        }

        if (args[0].equals("--help")) {
            Help.command();
            return;
        }

        options(args);

        if (entityManager != null)
            entityManager.close();

        if (entityManagerFactory != null)
            entityManagerFactory.close();

        return;
    }

    public static void options(String args[]) {
        String command = args[0];

        switch (command) {
            case "-s":
            case "--signup":
                connectDB();
                Signup.command(entityManager);
                break;

            case "-l":
            case "--login":
                connectDB();
                Login.command(entityManager);
                break;

            case "--logout":
                Auth.logout();
                break;

            case "--add":
                connectDB();
                Add.command(args, entityManager);
                break;

            case "-b":
            case "--books":
                connectDB();
                Books.command(args, entityManager);
                break;

            case "-r":
            case "--reviews":
                Reviews.command(args);
                break;

            case "--like":
                Like.command(args);
                break;

            case "-p":
            case "--profile":
                connectDB();
                Profile.command(args, entityManager);
                break;

            case "-a":
            case "--achievements":
                Achievements.command(args);
                break;

            case "-h":
            case "--help":
                Help.command();
                break;

            case "-v":
            case "--version":
                Version.command();
                break;

            default:
                System.out.println("Unknown command: " + command);
        }
    }

    public static void connectDB() {
        Dotenv dotenv = Dotenv.load();

        Map<String, Object> config = new HashMap<>();
        config.put("javax.persistence.jdbc.url", dotenv.get("POSTGRES_DATABASE_JDBC"));
        config.put("javax.persistence.jdbc.user", dotenv.get("POSTGRES_DATABASE_USER"));
        config.put("javax.persistence.jdbc.password", dotenv.get("POSTGRES_DATABASE_PASSWORD"));

        entityManagerFactory = Persistence.createEntityManagerFactory("PostgresSQLDefaultPU", config);
        entityManager = entityManagerFactory.createEntityManager();
    }
}
