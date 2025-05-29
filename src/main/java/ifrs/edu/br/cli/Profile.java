package ifrs.edu.br.cli;

import javax.persistence.EntityManager;

import ifrs.edu.br.context.Auth;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.models.User;

/**
 * Profile
 */
public class Profile {
    private static EntityManager entityManager;

    public static void command(String[] args, EntityManager entityManager) {
        Profile.entityManager = entityManager;

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
        UserDAO userDAO = new UserDAO(entityManager);

        User user = Auth.verify(entityManager);

        if (user == null) {
            System.out.println("Not logged in.");
            return;
        }

        System.out.println(userDAO.find(user.getId()));
    }

    public static void showProfileFromUser(int id) {
        UserDAO userDAO = new UserDAO(entityManager);

        User user = userDAO.find(id);

        if (user == null) {
            System.out.println("User not Found!");
            return;
        }

        String emailStart = user.getEmail().substring(0, user.getEmail().length() / 5);
        String emailEnd = user.getEmail().substring(user.getEmail().length() - user.getEmail().length() / 5);

        user.setEmail(emailStart + "..." + emailEnd);

        System.out.println(user);
    }
}
