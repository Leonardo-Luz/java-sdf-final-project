package ifrs.edu.br.cli;

/**
 * Reviews
 */
public class Reviews {
    public static void command(String args[]) {
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
        int page = 1;

        // Parse optional args
        for (int i = 3; i < args.length; i++) {
            if (args[i].startsWith("--order="))
                order = args[i].substring("--order=".length());
            else if (args[i].startsWith("--order-by="))
                orderBy = args[i].substring("--order-by=".length());
            else if (args[i].startsWith("--page=")) {
                String pageStr = args[i].substring("--page=".length());
                try {
                    page = Integer.parseInt(pageStr);
                    if (page < 1)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid page number. Must be a positive integer.");
                }
            } else
                System.out.println("Warning: Unrecognized option '" + args[i] + "'");
        }

        System.out.printf(
                "Showing reviews for %s ID %d ordered %s by %s page %d%n",
                context, id, order, orderBy, page);

        logic();
    }

    private static void logic() {
        // TODO: Implement reviews logic
    }
}
