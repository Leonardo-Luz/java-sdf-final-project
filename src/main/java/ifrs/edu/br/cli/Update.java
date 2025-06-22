package ifrs.edu.br.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;
import ifrs.edu.br.models.Badge;
import ifrs.edu.br.models.Book;
import ifrs.edu.br.controllers.BadgeController;
import ifrs.edu.br.controllers.BookController;
import ifrs.edu.br.controllers.ReviewController;
import ifrs.edu.br.controllers.UserController;

/**
 * Update
 */
public class Update {
    private static Scanner scanner;

    public static void command(String args[]) {
        scanner = new Scanner(System.in);

        if (args.length < 2) {
            System.out.println("Error: Incomplete arguments for --update.");
            System.out.println("Usage:");
            System.out.println("  --update review      [id|name] <value>");
            System.out.println("  --update book        [id|name] <value>");
            System.out.println("  --update achievement [id|name] <value>");
            return;
        }

        String type = args[1];

        if (type.equals("review")) {
            if (args.length < 4) {
                System.out.println("Error: Missing arguments for updating a review.");
                System.out.println("Usage: --update review [id|name] <value>");
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

            System.out.println("Updating a review using " + mode + ": " + value);
            updateReview(reviewId);
        } else if (type.equals("book")) {
            if (args.length < 4) {
                System.out.println("Error: Missing arguments for updating a book.");
                System.out.println("Usage: --update book [id|name] <value>");
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

            System.out.println("Updating a book using " + mode + ": " + value);
            updateBook(bookId);
        } else if (type.equals("achievement")) {
            if (args.length < 4) {
                System.out.println("Error: Missing arguments for updating a achievement.");
                System.out.println("Usage: --update achievement [id|name] <value>");
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

            System.out.println("Updating a achievement using " + mode + ": " + value);
            updateAchievement(achievementId);
        } else {
            System.out.println("Error: Invalid type. Use 'review' or 'book'.");
        }

        scanner.close();
    }

    private static void updateReview(int reviewId) {
        UserController userController = new UserController();
        User user = userController.verify();
        if (user == null) {
            System.out.println("You need to login before updating a review");
            return;
        }

        ReviewController reviewController = new ReviewController();
        Review review = reviewController.findHandler(reviewId);

        if (!user.getRole().equals("ADMIN") && review.getUser().getId() != user.getId()) {
            System.out.println("Only ADMINS can update non-owned reviews.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("> title");
        System.out.print("> ");
        String title = scanner.nextLine();

        System.out.println("> review");
        System.out.print("> ");
        String text = scanner.nextLine();

        System.out.println("> start date (dd/MM/yyyy)");
        System.out.print("> ");
        String readStartDateInput = scanner.nextLine();

        LocalDate readStartDate = null;
        try {
            readStartDate = LocalDate.parse(readStartDateInput, formatter);
        } catch (DateTimeParseException e) {
            readStartDate = null;
        }

        System.out.println("> end date (dd/MM/yyyy)");
        System.out.print("> ");
        String readEndDateInput = scanner.nextLine();

        LocalDate readEndDate = null;
        try {
            readEndDate = LocalDate.parse(readEndDateInput, formatter);
        } catch (DateTimeParseException e) {
            readEndDate = null;
        }

        System.out.println("> book id");
        System.out.print("> ");
        int bookId = -1;
        try {
            bookId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException err) {
            bookId = -1;
        }

        BookController bookController = new BookController();
        Book book = bookController.findHandler(bookId);

        try {
            review.setTitle(title);
            review.setText(text);
            review.setReadStartDate(readStartDate);
            review.setReadEndDate(readEndDate);
            review.setBook(book);
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }

        reviewController.updateHandler(review);

        System.out.println();
        System.out.println("Review updated!");
    }

    private static void updateBook(int bookId) {
        UserController userController = new UserController();
        User user = userController.verify();
        if (user == null) {
            System.out.println("You need to be logged to update a book");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to update a book");
            return;
        }

        BookController bookController = new BookController();
        Book book = bookController.findHandler(bookId);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        System.out.println("> title");
        System.out.print("> ");
        String title = scanner.nextLine();

        System.out.println("> pages");
        System.out.print("> ");
        int pages = 0;
        try {
            pages = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException err) {
            pages = 0;
        }
        scanner.nextLine();

        System.out.println("> synopsis");
        System.out.print("> ");
        String synopsis = scanner.nextLine();

        try {
            book.setTitle(title);
            book.setPages(pages);
            book.setSynopsis(synopsis);
        } catch (RuntimeException e) {
            System.out.println(e);
        }

        bookController.updateHandler(book);

        System.out.println();
        System.out.println("Book updated!");
    }

    private static void updateAchievement(int badgeId) {
        UserController userController = new UserController();
        User user = userController.verify();
        if (user == null) {
            System.out.println("You need to login before updating a review");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to update a book");
            return;
        }

        BadgeController badgeController = new BadgeController();
        Badge badge = badgeController.findHandler(badgeId);

        System.out.println("> name");
        System.out.print("> ");
        String name = scanner.nextLine();

        System.out.println("> requirements");
        System.out.print("> ");
        String requirements = scanner.nextLine();

        try {
            badge.setName(name);
            badge.setRequirements(requirements);
        } catch (Exception e) {
            System.out.println(e);
        }

        badgeController.updateHandler(badge);

        System.out.println();
        System.out.println("Achievement updated!");
    }
}
