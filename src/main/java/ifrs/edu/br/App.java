package ifrs.edu.br;

import ifrs.edu.br.cli.Profile;
import ifrs.edu.br.cli.Remove;
import ifrs.edu.br.cli.Achievements;
import ifrs.edu.br.cli.Add;
import ifrs.edu.br.cli.Books;
import ifrs.edu.br.cli.Help;
import ifrs.edu.br.cli.Like;
import ifrs.edu.br.cli.Login;
import ifrs.edu.br.cli.Reviews;
import ifrs.edu.br.cli.Signup;
import ifrs.edu.br.cli.Update;
import ifrs.edu.br.cli.Version;

import ifrs.edu.br.context.Auth;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            Version.command();
            System.out.println();
            Help.command();
            return;
        }

        options(args);

        Database.close();
    }

    public static void options(String args[]) {
        String command = args[0];

        switch (command) {
            case "-s":
            case "--signup":
                Database.connect();
                Signup.command(Database.getEntityManager());
                break;

            case "-l":
            case "--login":
                Database.connect();
                Login.command(Database.getEntityManager());
                break;

            case "--logout":
                Auth.logout();
                break;

            case "--add":
                Database.connect();
                Add.command(args, Database.getEntityManager());
                break;

            case "--remove":
                Database.connect();
                Remove.command(args, Database.getEntityManager());
                break;

            case "-u":
            case "--update":
                Database.connect();
                Update.command(args, Database.getEntityManager());
                break;

            case "-b":
            case "--books":
                Database.connect();
                Books.command(args, Database.getEntityManager());
                break;

            case "-r":
            case "--reviews":
                Database.connect();
                Reviews.command(args, Database.getEntityManager());
                break;

            case "--like":
                Database.connect();
                Like.command(args, Database.getEntityManager());
                break;

            case "-p":
            case "--profile":
                Database.connect();
                Profile.command(args, Database.getEntityManager());
                break;

            case "-a":
            case "--achievements":
                Database.connect();
                Achievements.command(args, Database.getEntityManager());
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
}
