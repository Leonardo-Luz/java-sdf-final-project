package ifrs.edu.br.cli;

import java.util.Scanner;

import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;
import ifrs.edu.br.controllers.BadgeController;
import ifrs.edu.br.controllers.BookController;
import ifrs.edu.br.controllers.ReviewController;
import ifrs.edu.br.controllers.UserController;

/**
 * Remove
 */
public class Remove {
    private static Scanner scanner;

    public static void command(String args[]) {
        scanner = new Scanner(System.in);

        if (args.length < 2) {
            System.out.println("Error: Incomplete arguments for --remove.");
            System.out.println("Usage:");
            System.out.println("  --remove review      [id|name] <value>");
            System.out.println("  --remove book        [id|name] <value>");
            System.out.println("  --remove achievement [id|name] <value>");
            System.out.println("  --remove account     [id|name|self] <value>");
            return;
        }

        String type = args[1];

        if (type.equals("review")) {
            if (args.length < 4) {
                System.out.println("Error: Missing arguments for removing a review.");
                System.out.println("Usage: --remove review [id|name] <value>");
                return;
            }

            String mode = args[2];
            String value = args[3];

            if (!mode.equals("id") && !mode.equals("name")) {
                System.out.println("Error: Invalid mode for review. Use 'id' or 'name'.");
                return;
            }

            if (mode.equals("name")) {
                System.out.println("Error: 'name' query not implemented!");
                return;
            }

            int reviewId = -1;
            try {
                reviewId = Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Error: invalid ID");
            }

            if (reviewId < 0) {
                System.out.println("Error: Review Id must be positive");
            }

            System.out.println("Removing a review using " + mode + ": " + value);
            removeReview(reviewId);
        } else if (type.equals("book")) {
            if (args.length < 4) {
                System.out.println("Error: Missing arguments for removing a book.");
                System.out.println("Usage: --remove book [id|name] <value>");
                return;
            }

            String mode = args[2];
            String value = args[3];

            if (!mode.equals("id") && !mode.equals("name")) {
                System.out.println("Error: Invalid mode for book. Use 'id' or 'name'.");
                return;
            }

            if (mode.equals("name")) {
                System.out.println("Error: 'name' query not implemented!");
                return;
            }

            int bookId = -1;
            try {
                bookId = Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Error: invalid ID");
            }

            if (bookId < 0) {
                System.out.println("Error: Book Id must be positive");
            }

            System.out.println("Removing a book using " + mode + ": " + value);
            removeBook(bookId);
        } else if (type.equals("achievement")) {
            if (args.length < 4) {
                System.out.println("Error: Missing arguments for removing a achievement.");
                System.out.println("Usage: --remove achievement [id|name] <value>");
                return;
            }

            String mode = args[2];
            String value = args[3];

            if (!mode.equals("id") && !mode.equals("name")) {
                System.out.println("Error: Invalid mode for achievement. Use 'id' or 'name'.");
                return;
            }

            if (mode.equals("name")) {
                System.out.println("Error: 'name' query not implemented!");
                return;
            }

            int achievementId = -1;
            try {
                achievementId = Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Error: invalid ID");
            }

            if (achievementId < 0) {
                System.out.println("Error: Achievement Id must be positive");
            }

            System.out.println("Removing a achievement using " + mode + ": " + value);
            removeAchievement(achievementId);
        } else if (type.equals("account")) {
            if (args.length < 3) {
                System.out.println("Error: Missing arguments for removing a account.");
                System.out.println("Usage: --remove account [id|name|self] <value>");
                return;
            }

            String mode = args[2];
            String value = args[3];

            if (!mode.equals("id") && !mode.equals("name") && !mode.equals("self")) {
                System.out.println("Error: Invalid mode for account. Use 'id', 'name' or 'self'.");
                return;
            }

            if (mode.equals("name")) {
                System.out.println("Error: 'name' query not implemented!");
                return;
            }
            Integer accountId = null;

            if (mode.equals("id"))
                try {
                    accountId = Integer.parseInt(value);
                } catch (NumberFormatException exception) {
                    System.out.println("Error: invalid ID");
                }

            System.out.println("Removing a account using " + mode + ": " + value);
            removeAccount(accountId);
        } else {
            System.out.println("Error: Invalid type. Use 'review' or 'book'.");
        }

        scanner.close();
    }

    private static void removeReview(int reviewId) {
        UserController userController = new UserController();
        User user = userController.verify();
        if (user == null) {
            System.out.println("You need to login before removing a review");
            return;
        }

        ReviewController reviewController = new ReviewController();

        Review review = reviewController.findHandler(reviewId);

        if (user.getRole() != "ADMIN" && review.getUser().getId() != user.getId()) {
            System.out.println("Only ADMINS can delete non-owned reviews.");
            return;
        }

        reviewController.deleteHandler(reviewId);

        System.out.println();
        System.out.println("Review removed!");
    }

    private static void removeBook(int bookId) {
        UserController userController = new UserController();
        User user = userController.verify();
        if (user == null) {
            System.out.println("You need to be logged to remove a book");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to remove a book");
            return;
        }

        BookController bookController = new BookController();

        bookController.findHandler(bookId);
        bookController.deleteHandler(bookId);

        System.out.println();
        System.out.println("Book removed!");
    }

    private static void removeAchievement(int achievementId) {
        UserController userController = new UserController();
        User user = userController.verify();
        if (user == null) {
            System.out.println("You need to login before removing a achievement");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to remove a achievement");
            return;
        }

        BadgeController badgeController = new BadgeController();

        badgeController.findHandler(achievementId);
        badgeController.deleteHandler(achievementId);

        System.out.println();
        System.out.println("Achievement removed!");
    }

    private static void removeAccount(Integer userId) {
        UserController userController = new UserController();
        User user = userController.verify();

        if (user == null) {
            System.out.println("You need to login before removing a user");
            return;
        }

        if (userId != null && !user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to remove a user by ID");
            return;
        } else if (userId != null && user.getRole().equals("ADMIN"))
            user = userController.findHandler(userId);
        else if (userId == null)
            userController.logout();

        userController.deleteHandler(userId != null ? userId : user.getId());

        System.out.println();
        System.out.println("User removed!");
    }
}
