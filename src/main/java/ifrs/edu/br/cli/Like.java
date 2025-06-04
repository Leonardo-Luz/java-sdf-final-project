package ifrs.edu.br.cli;

import javax.persistence.EntityManager;

import ifrs.edu.br.context.Auth;
import ifrs.edu.br.dao.ReviewDAO;
import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;

/**
 * Like
 */
public class Like {
    private static EntityManager entityManager;

    public static void command(String args[], EntityManager entityManager) {
        Like.entityManager = entityManager;

        if (args.length < 2) {
            System.out.println("Error: Missing review ID.");
            System.out.println("Usage: --like <Review ID>");
            return;
        }

        try {
            int reviewId = Integer.parseInt(args[1]);
            System.out.println("Liking review with ID: " + reviewId);
            logic(reviewId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid review ID: " + args[1]);
        }
    }

    private static void logic(int id) {
        User user = Auth.verify(entityManager);
        if (user == null) {
            System.out.println("You need to be logged to like a review");
            return;
        }

        ReviewDAO reviewDAO = new ReviewDAO(entityManager);

        Review review = reviewDAO.find(id);

        if (review == null) {
            System.out.println("Review not found!");
            return;
        }

        if (review.addLike(user)) {
            reviewDAO.update(review);
            System.out.println("Review liked!");
        } else {
            System.out.println("You alredy liked this review.");
        }
    }
}
