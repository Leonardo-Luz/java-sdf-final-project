package ifrs.edu.br.controllers;

import java.util.List;

import ifrs.edu.br.dao.BookDAO;
import ifrs.edu.br.models.Book;

/**
 * BookController
 */
public class BookController implements Controller<Book> {
    private BookDAO bookDAO;

    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public BookController() {
        this.bookDAO = new BookDAO();
    }

    public List<Book> listHandler(int limit, int offset) {
        try {
            if (limit < 0)
                throw new RuntimeException("Limit can't be negative");
            if (offset < 0)
                throw new RuntimeException("Offset can't be negative");

            if (limit > 100)
                throw new RuntimeException("Limit can't exceed 100");

            List<Book> list = bookDAO.list(limit, offset);

            if (list.isEmpty())
                throw new RuntimeException("Book list is empty!");

            return list;
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    @Override
    public void insertHandler(Book object) {
        try {
            if (object == null)
                throw new RuntimeException("Book can't be null");

            bookDAO.insert(object);
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }

    @Override
    public Book findHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            Book book = bookDAO.find(id);

            if (book == null)
                throw new RuntimeException("Book not found!");

            return book;
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    @Override
    public void updateHandler(Book object) {
        try {
            if (object == null)
                throw new RuntimeException("Book can't be null");

            bookDAO.update(object);
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }

    @Override
    public void deleteHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            bookDAO.delete(id);
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }

}
