package ifrs.edu.br.cli;

/**
 * Achievements
 */
public class Achievements {
    public static void command(String[] args) {
        if (args.length == 2) {
            String secondArg = args[1];
            if (secondArg.equals("list")) {
                System.out.println("Listing all available achievements and how to obtain them");
                achievementList();
            } else {
                System.out.println("Unknown achievement subcommand: " + secondArg);
            }
        } else if (args.length == 3) {
            String subCommand = args[1];
            if (subCommand.equals("unlock")) {
                try {
                    int achievementId = Integer.parseInt(args[2]);
                    System.out.println("Showing how to unlock achievement ID " + achievementId);
                    unlockAchievement(achievementId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid achievement ID: " + args[2]);
                }
            } else {
                System.out.println("Unknown achievement subcommand: " + subCommand);
            }
        } else {
            System.out.println("Invalid usage of --achievements.");
            System.out.println("Usage:");
            System.out.println("  --achievements list           List all achievements and how to obtain them");
            System.out.println("  --achievements unlock <ID>    Show how to unlock a specific achievement");
        }
    }

    public static void achievementList() {
    }

    public static void unlockAchievement(int id) {
    }
}
