package ifrs.edu.br.cli;

import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.models.User;

/**
 * Profile
 */
public class Profile {
    public static void command(String[] args) {
        if (args.length == 1) {
            System.out.println("Showing profile of the logged-in user");
            showLoggedProfile();
        } else if (args.length == 2) {
            String secondArg = args[1];
            try {
                int userId = Integer.parseInt(secondArg);
                System.out.println("Showing profile of user ID " + userId);
                showProfileFromUser(userId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid user ID: " + secondArg);
            }
        } else {
            System.out.println("Invalid usage of --profile.");
            System.out.println("Usage:");
            System.out.println("  --profile           Show profile of logged user");
            System.out.println("  --profile <User ID> Show profile of specific user");
        }
    }

    public static void showLoggedProfile() {
        UserController userController = new UserController();
        User user = userController.verify();

        if (user == null) {
            System.out.println("You need to login to see your profile");
            return;
        }

        System.out.println(user);
    }

    public static void showProfileFromUser(int id) {
        UserController userController = new UserController();

        User user = userController.findHandler(id);

        String emailStart = user.getEmail().substring(0, user.getEmail().length() / 5);
        String emailEnd = user.getEmail().substring(user.getEmail().length() - user.getEmail().length() / 5);

        user.setEmail(emailStart + "..." + emailEnd);

        System.out.println(user);
    }
}
