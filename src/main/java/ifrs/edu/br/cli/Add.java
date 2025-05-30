package ifrs.edu.br.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
 * Add
 */
public class Add {
    private static Scanner scanner;
    private static EntityManager entityManager;

    public static void command(String args[], EntityManager entityManager) {
        Add.entityManager = entityManager;
        scanner = new Scanner(System.in);

        if (args.length < 2) {
            System.out.println("Error: Incomplete arguments for --add.");
            System.out.println("Usage:");
            System.out.println("  --add review [id|name] <value>");
            System.out.println("  --add book");
            return;
        }

        String type = args[1];

        if (type.equals("review")) {
            if (args.length < 4) {
                System.out.println("Error: Missing arguments for adding a review.");
                System.out.println("Usage: --add review [id|name] <value>");
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

            int bookId = -1;
            try {
                bookId = Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Error: invalid ID");
            }

            if (bookId < 0) {
                System.out.println("Error: Book Id must be positive");
            }

            System.out.println("Adding a review using " + mode + ": " + value);
            addReview(bookId);
        } else if (type.equals("book")) {
            if (args.length != 2) {
                System.out.println("Error: Adding a book does not accept 'id' or 'name'.");
                System.out.println("Usage: --add book");
                return;
            }

            System.out.println("Adding a book");
            addBook();
        } else if (type.equals("achievement")) {
            if (args.length != 2) {
                System.out.println("Error: Adding a achievement does not accept 'id' or 'name'.");
                System.out.println("Usage: --add achievement");
                return;
            }

            System.out.println("Adding a achievement");
            addAchievement();
        } else {
            System.out.println("Error: Invalid type. Use 'review' or 'book'.");
        }

        scanner.close();
    }

    private static void addReview(int bookId) {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to login before adding a review");
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
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            scanner.close();
            return;
        }

        if (readStartDate == null) {
            System.out.println("Invalid date.");
            scanner.close();
            return;
        }

        System.out.println("> end date (dd/MM/yyyy)");
        System.out.print("> ");
        String readEndDateInput = scanner.nextLine();

        LocalDate readEndDate = null;
        try {
            readEndDate = LocalDate.parse(readEndDateInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            scanner.close();
            return;
        }

        if (readEndDate == null) {
            System.out.println("Invalid date.");
            scanner.close();
            return;
        }

        BookDAO bookDAO = new BookDAO(entityManager);
        Book book = bookDAO.find(bookId);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        Review review = new Review(title, text, readStartDate, readEndDate, book, user);
        ReviewDAO reviewDAO = new ReviewDAO(entityManager);

        reviewDAO.insert(review);

        System.out.println();
        System.out.println("New Review created!");
    }

    private static void addBook() {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to be logged to add a book");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to add a book");
            return;
        }

        System.out.println("> title");
        System.out.print("> ");
        String title = scanner.nextLine();

        System.out.println("> pages");
        System.out.print("> ");
        int pages = scanner.nextInt();
        scanner.nextLine();

        System.out.println("> synopsis");
        System.out.print("> ");
        String synopsis = scanner.nextLine();

        Book book = new Book(title, pages, synopsis);
        BookDAO bookDAO = new BookDAO(entityManager);

        bookDAO.insert(book);

        System.out.println();
        System.out.println("New Book created!");
    }

    private static void addAchievement() {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to login before adding a review");
            return;
        }

        if (!user.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to add a book");
            return;
        }

        System.out.println("> name");
        System.out.print("> ");
        String name = scanner.nextLine();

        System.out.println("> requirements");
        System.out.print("> ");
        String requirements = scanner.nextLine();

        Badge badge = new Badge(name, requirements);
        BadgeDAO badgeDAO = new BadgeDAO(entityManager);

        badgeDAO.insert(badge);

        System.out.println();
        System.out.println("New Achievement created!");
    }
}
