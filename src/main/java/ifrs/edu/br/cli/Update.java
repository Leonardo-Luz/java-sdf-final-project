package ifrs.edu.br.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.persistence.EntityManager;

import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;
import ifrs.edu.br.models.Badge;
import ifrs.edu.br.models.Book;
import ifrs.edu.br.dao.ReviewDAO;
import ifrs.edu.br.context.Auth;
import ifrs.edu.br.dao.BadgeDAO;
import ifrs.edu.br.dao.BookDAO;

/**
 * Update
 */
public class Update {
    private static Scanner scanner;
    private static EntityManager entityManager;

    public static void command(String args[], EntityManager entityManager) {
        Update.entityManager = entityManager;
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
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to login before updating a review");
            return;
        }

        ReviewDAO reviewDAO = new ReviewDAO(entityManager);
        Review review = reviewDAO.find(reviewId);

        if (review == null) {
            System.out.println("Review not found!");
            return;
        }

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

        BookDAO bookDAO = new BookDAO(entityManager);
        Book book = bookDAO.find(bookId);

        if (title.length() > 0)
            review.setTitle(title);
        if (text.length() > 0)
            review.setText(text);
        if (readStartDate != null)
            review.setReadStartDate(readStartDate);
        if (readEndDate != null)
            review.setReadEndDate(readEndDate);
        if (book != null)
            review.setBook(book);

        reviewDAO.update(review);

        System.out.println();
        System.out.println("Review updated!");
    }

    private static void updateBook(int bookId) {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to be logged to update a book");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to update a book");
            return;
        }

        BookDAO bookDAO = new BookDAO(entityManager);
        Book book = bookDAO.find(bookId);

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

        if (title.length() > 0)
            book.setTitle(title);
        if (pages > 0)
            book.setPages(pages);
        if (synopsis.length() > 0)
            book.setSynopsis(synopsis);

        bookDAO.update(book);

        System.out.println();
        System.out.println("Book updated!");
    }

    private static void updateAchievement(int badgeId) {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to login before updating a review");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to update a book");
            return;
        }

        BadgeDAO badgeDAO = new BadgeDAO(entityManager);
        Badge badge = badgeDAO.find(badgeId);

        if (badge == null) {
            System.out.println("Badge not found!");
            return;
        }

        System.out.println("> name");
        System.out.print("> ");
        String name = scanner.nextLine();

        System.out.println("> requirements");
        System.out.print("> ");
        String requirements = scanner.nextLine();

        if (name.length() > 0)
            badge.setName(name);
        if (requirements.length() > 0)
            badge.setRequirements(requirements);

        badgeDAO.update(badge);

        System.out.println();
        System.out.println("Achievement updated!");
    }
}
