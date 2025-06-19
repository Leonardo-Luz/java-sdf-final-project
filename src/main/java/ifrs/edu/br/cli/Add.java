package ifrs.edu.br.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;
import ifrs.edu.br.utils.FileManager;
import ifrs.edu.br.models.Badge;
import ifrs.edu.br.models.Book;
import ifrs.edu.br.dao.ReviewDAO;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.controllers.BadgeController;
import ifrs.edu.br.controllers.BookController;
import ifrs.edu.br.controllers.ReviewController;
import ifrs.edu.br.controllers.UserController;
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
        User user = new UserController(new UserDAO(entityManager), new FileManager(), new BCryptPasswordEncoder())
                .verify();

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

        try {
            Review review = new Review(title, text, readStartDate, readEndDate, book, user);
            ReviewController reviewController = new ReviewController(new ReviewDAO(entityManager));

            reviewController.insertHandler(review);

            System.out.println();
            System.out.println("New Review created!");
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }

    private static void addBook() {
        User user = new UserController(new UserDAO(entityManager), new FileManager(), new BCryptPasswordEncoder())
                .verify();

        if (user == null) {
            System.out.println("You need to login before adding a book");
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

        try {
            Book book = new Book(title, pages, synopsis);
            BookController bookController = new BookController(new BookDAO(entityManager));

            bookController.insertHandler(book);

            System.out.println();
            System.out.println("New Book created!");
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }

    private static void addAchievement() {
        User user = new UserController(new UserDAO(entityManager), new FileManager(), new BCryptPasswordEncoder())
                .verify();

        if (user == null) {
            System.out.println("You need to login before adding a book");
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

        try {
            Badge badge = new Badge(name, requirements);
            BadgeController badgeController = new BadgeController(new BadgeDAO(entityManager));

            badgeController.insertHandler(badge);

            System.out.println();
            System.out.println("New Achievement created!");
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }
}
