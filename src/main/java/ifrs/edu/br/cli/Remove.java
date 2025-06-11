package ifrs.edu.br.cli;

import java.util.OptionalInt;
import java.util.Scanner;

import javax.persistence.EntityManager;

import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;
import ifrs.edu.br.models.Badge;
import ifrs.edu.br.models.Book;
import ifrs.edu.br.dao.ReviewDAO;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.context.Auth;
import ifrs.edu.br.dao.BadgeDAO;
import ifrs.edu.br.dao.BookDAO;

/**
 * Remove
 */
public class Remove {
    private static Scanner scanner;
    private static EntityManager entityManager;

    public static void command(String args[], EntityManager entityManager) {
        Remove.entityManager = entityManager;
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

            if(mode.equals("id"))
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
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to login before removing a review");
            return;
        }

        ReviewDAO reviewDAO = new ReviewDAO(entityManager);
        Review review = reviewDAO.find(reviewId);

        if (review == null) {
            System.out.println("Review not found!");
            return;
        }

        if (user.getRole() != "ADMIN" && review.getUser().getId() != user.getId()) {
            System.out.println("Only ADMINS can delete non-owned reviews.");
            return;
        }

        reviewDAO.delete(reviewId);

        System.out.println();
        System.out.println("Review removed!");
    }

    private static void removeBook(int bookId) {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to be logged to remove a book");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to remove a book");
            return;
        }

        BookDAO bookDAO = new BookDAO(entityManager);
        Book book = bookDAO.find(bookId);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        bookDAO.delete(bookId);

        System.out.println();
        System.out.println("Book removed!");
    }

    private static void removeAchievement(int achievementId) {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to login before removing a achievement");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to remove a achievement");
            return;
        }

        BadgeDAO badgeDAO = new BadgeDAO(entityManager);
        Badge badge = badgeDAO.find(achievementId);

        if (badge == null) {
            System.out.println("Achievement not found!");
            return;
        }

        badgeDAO.delete(achievementId);

        System.out.println();
        System.out.println("Achievement removed!");
    }

    private static void removeAccount(Integer userId) {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to login before removing a user");
            return;
        }

        UserDAO userDAO = new UserDAO(entityManager);

        if (userId != null && !user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to remove a user by ID");
            return;
        } else if(userId != null && user.getRole().equals("ADMIN")) {
             user = userDAO.find(userId);
        } else if (userId == null)
            Auth.logout();

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        userDAO.delete(userId != null ? userId : user.getId());

        System.out.println();
        System.out.println("User removed!");
    }
}
