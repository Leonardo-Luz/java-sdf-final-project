package ifrs.edu.br.cli;

import java.util.List;

import ifrs.edu.br.controllers.BookController;
import ifrs.edu.br.models.Book;

/**
 * Books
 */
public class Books {
    public static void command(String args[]) {
        int page = 0;

        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("--page=")) {
                String pageStr = args[i].substring("--page=".length());
                try {
                    page = Integer.parseInt(pageStr);
                    if (page < 1)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid page number. Must be a positive integer.");
                }
            } else {
                System.out.println("Warning: Unrecognized option '" + args[i] + "'");
            }
        }

        System.out.println("Showing books from page " + (page + 1));
        logic(page);
    }

    private static void logic(int page) {
        BookController bookController = new BookController();

        List<Book> books = bookController.listHandler(10, page * 10);

        System.out.println();
        books.forEach((book) -> {
            System.out.println(book);
            System.out.println();
        });
    }
}
