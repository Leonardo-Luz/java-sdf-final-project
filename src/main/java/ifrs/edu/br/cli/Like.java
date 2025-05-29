package ifrs.edu.br.cli;

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
            logic();
        } catch (NumberFormatException e) {
            System.out.println("Invalid review ID: " + args[1]);
        }
    }

    private static void logic() {
        // TODO: Implement like logic
    }
}
