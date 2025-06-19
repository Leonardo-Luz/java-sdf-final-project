package ifrs.edu.br.cli;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ifrs.edu.br.dao.ReviewDAO;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.controllers.BookController;
import ifrs.edu.br.controllers.ReviewController;
import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.dao.BookDAO;
import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;
import ifrs.edu.br.utils.FileManager;
import ifrs.edu.br.models.Book;

/**
 * Reviews
 */
public class Reviews {
    private static EntityManager entityManager;

    public static void command(String args[], EntityManager entityManager) {
        Reviews.entityManager = entityManager;

        if (args.length < 3) {
            System.out.println("Error: Missing required arguments.");
            System.out.println("Usage: --reviews [book|user] <ID> [--order=asc|desc] [--order-by=likes|release]");
            return;
        }

        String context = args[1];

        if (!context.equals("book") && !context.equals("user")) {
            System.out.println("Error: First argument must be 'book' or 'user'.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID");
            return;
        }

        // Defaults
        String order = "asc";
        String orderBy = "release";
        int page = 0;

        // Parse optional args
        for (int i = 3; i < args.length; i++) {
            if (args[i].startsWith("--order="))
                order = args[i].substring("--order=".length());
            else if (args[i].startsWith("--order-by="))
                orderBy = args[i].substring("--order-by=".length());
            else if (args[i].startsWith("--page=")) {
                String pageStr = args[i].substring("--page=".length());
                try {
                    page = Integer.parseInt(pageStr) - 1;
                    if (page < 0)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid page number. Must be a positive integer.");
                }
            } else
                System.out.println("Warning: Unrecognized option '" + args[i] + "'");
        }

        System.out.printf(
                "Showing reviews for %s ID %d ordered %s by %s page %d%n",
                context, id, order, orderBy, page + 1);

        logic(context, id, page);
    }

    private static void logic(String context, int id, int page) {
        ReviewController reviewController = new ReviewController(new ReviewDAO(entityManager));

        List<Review> reviews = null;

        switch (context) {
            case "user":
                UserController userController = new UserController(new UserDAO(entityManager), new FileManager(),
                        new BCryptPasswordEncoder());

                User user = userController.findHandler(id);

                reviews = reviewController.listByUserHandler(10, page * 10, user);
                break;
            case "book":
                BookController bookController = new BookController(new BookDAO(entityManager));

                Book book = bookController.findHandler(id);

                reviews = reviewController.listByBookHandler(10, page * 10, book);
                break;
        }

        if (reviews == null) {
            System.out.println("Error: context not found");
        }

        System.out.println();
        reviews.forEach((review) -> {
            System.out.println(review);
            System.out.println();
        });
    }
}
