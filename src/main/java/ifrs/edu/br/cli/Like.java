package ifrs.edu.br.cli;

import ifrs.edu.br.controllers.ReviewController;
import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;

/**
 * Like
 */
public class Like {
    public static void command(String args[]) {
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
        User user = new UserController().verify();

        if (user == null) {
            System.out.println("You need to login before adding a book");
            return;
        }

        ReviewController reviewController = new ReviewController();
        Review review = reviewController.findHandler(id);

        if (review == null)
            return;

        try {
            review.addLike(user);
            reviewController.updateHandler(review);
            System.out.println("Review liked!");
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
    }
}
