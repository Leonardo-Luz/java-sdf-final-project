package ifrs.edu.br;

import ifrs.edu.br.cli.Profile;
import ifrs.edu.br.cli.Remove;
import ifrs.edu.br.cli.Achievements;
import ifrs.edu.br.cli.Add;
import ifrs.edu.br.cli.Books;
import ifrs.edu.br.cli.Help;
import ifrs.edu.br.cli.Like;
import ifrs.edu.br.cli.Login;
import ifrs.edu.br.cli.Logout;
import ifrs.edu.br.cli.Reviews;
import ifrs.edu.br.cli.Signup;
import ifrs.edu.br.cli.Update;
import ifrs.edu.br.cli.Version;

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
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Signup.command();
                break;

            case "-l":
            case "--login":
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Login.command();
                break;

            case "--logout":
                Logout.command();
                break;

            case "--add":
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Add.command(args);
                break;

            case "--remove":
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Remove.command(args);
                break;

            case "-u":
            case "--update":
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Update.command(args);
                break;

            case "-b":
            case "--books":
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Books.command(args);
                break;

            case "-r":
            case "--reviews":
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Reviews.command(args);
                break;

            case "--like":
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Like.command(args);
                break;

            case "-p":
            case "--profile":
                Database.connect(DatabaseEnum.DEVELOPMENT);
                Profile.command(args);
                break;

            case "-a":
            case "--achievements":
                Database.connect(DatabaseEnum.DEVELOPMENT);
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
}
