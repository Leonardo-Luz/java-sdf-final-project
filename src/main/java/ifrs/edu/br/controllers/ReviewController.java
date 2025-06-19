package ifrs.edu.br.controllers;

import java.util.List;

import ifrs.edu.br.dao.ReviewDAO;
import ifrs.edu.br.models.Book;
import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;

/**
 * ReviewController
 */
public class ReviewController implements Controller<Review> {
    private ReviewDAO reviewDAO;

    public ReviewController(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public List<Review> listByBookHandler(int limit, int offset, Book book) {
        try {
            if (limit < 0)
                throw new RuntimeException("Limit can't be negative");
            if (offset < 0)
                throw new RuntimeException("Offset can't be negative");

            if (limit > 100)
                throw new RuntimeException("Limit can't exceed 100");

            List<Review> list = reviewDAO.listByBook(limit, offset, book);

            if (list.isEmpty())
                throw new RuntimeException("Book review list is empty!");

            return list;
        } catch (RuntimeException err) {
            System.out.println(err);
            return null;
        }
    }

    public List<Review> listByUserHandler(int limit, int offset, User user) {
        try {
            if (limit < 0)
                throw new RuntimeException("Limit can't be negative");
            if (offset < 0)
                throw new RuntimeException("Offset can't be negative");

            if (limit > 100)
                throw new RuntimeException("Limit can't exceed 100");

            List<Review> list = reviewDAO.listByUser(limit, offset, user);

            if (list.isEmpty())
                throw new RuntimeException("User review list is empty!");

            return list;
        } catch (RuntimeException err) {
            System.out.println(err);
            return null;
        }
    }

    @Override
    public void insertHandler(Review object) {
        try {
            if (object == null)
                throw new RuntimeException("Review can't be null");

            reviewDAO.insert(object);
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }

    @Override
    public Review findHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            Review review = reviewDAO.find(id);

            if (review == null)
                throw new RuntimeException("Review not found!");

            return review;
        } catch (RuntimeException err) {
            System.out.println(err);
            return null;
        }
    }

    @Override
    public void updateHandler(Review object) {
        try {
            if (object == null)
                throw new RuntimeException("Review can't be null");

            reviewDAO.update(object);
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }

    @Override
    public void deleteHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            reviewDAO.delete(id);
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }

}
