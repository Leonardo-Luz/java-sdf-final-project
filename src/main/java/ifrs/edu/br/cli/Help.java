package ifrs.edu.br.cli;

/**
 * Help
 */
public class Help {
    public static void command() {
        System.out.println("Usage: review [options]");
        System.out.println("Options:");
        System.out.println("  -v, --version                              Show version information");
        System.out.println("  -s, --signup                               Sign up a new user");
        System.out.println("  -l, --login                                Log in an existing user");
        System.out.println("    , --logout                               Logout from the current account");
        System.out.println("  -p, --profile [<User ID>]                  Show user profile");
        System.out.println("      --add [review|book] [id|name] <value>  Add a review or a book");
        System.out.println("  -r, --remove [review|book] [id|name] <value>  remove a review or a book");
        System.out.println("  -b, --books [--page=<number>]              List all books");
        System.out.println(
                "  -r, --reviews [book | user] <Book ID|User ID> [--order=asc|desc] [--order-by=likes|release] [--page=<number>]  Show reviews");
        System.out.println("      --like <Review ID>                     Like a review");
        System.out.println("  -a, --achievements [<User ID>] [list|unlock] [<Achievement ID>]  Manage achievements");
        System.out.println("  -h, --help                                 Show this help message");
    }
}
