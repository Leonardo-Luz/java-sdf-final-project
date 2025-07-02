package ifrs.edu.br.cli;

import java.util.List;

import ifrs.edu.br.controllers.BadgeController;
import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.models.Badge;
import ifrs.edu.br.models.User;

/**
 * Achievements
 */
public class Achievements {
    public static void command(String args[]) {

        switch (args.length) {
            case 2:
                String secondArg = args[1];
                if (secondArg.equals("list")) {
                    System.out.println("Listing all available achievements and how to obtain them");
                    achievementList();
                } else {
                    System.out.println("Unknown achievement subcommand: " + secondArg);
                }
                break;

            case 3:
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
                break;
            case 4:
                String giveCommand = args[1];
                if (giveCommand.equals("give")) {
                    try {
                        int achievementId = Integer.parseInt(args[2]);
                        int userId = Integer.parseInt(args[3]);
                        System.out.println("Unlocking achievement ID " + achievementId + " To user ID " + userId);
                        giveAchievement(achievementId, userId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid achievement ID: " + args[2]);
                    }
                } else {
                    System.out.println("Unknown achievement subcommand: " + giveCommand);
                }
                break;

            default:
                System.out.println("Invalid usage of --achievements.");
                System.out.println("Usage:");
                System.out.println("  --achievements list           List all achievements and how to obtain them");
                System.out.println("  --achievements unlock <ID>    Show how to unlock a specific achievement");
                System.out.println(
                        "  --achievements give <Achievement ID> <User ID>    Unlocks an specific achievement to an specific User");
                break;
        }
        if (args.length == 2) {
        } else if (args.length == 3) {
        } else {
        }
    }

    public static void achievementList() {
        BadgeController badgeController = new BadgeController();

        List<Badge> badges = badgeController.listHandler(10, 0);

        if (badges == null)
            return;

        System.out.println();
        badges.forEach((badge) -> {
            System.out.println(badge);
            System.out.println();
        });
    }

    public static void unlockAchievement(int id) {
        BadgeController badgeController = new BadgeController();

        Badge badge = badgeController.findHandler(id);

        if(badge == null) return;

        System.out.println(badge);
    }

    public static void giveAchievement(int id, int userId) {
        UserController userController = new UserController();

        User admin = userController.verify();

        if (admin == null) {
            System.out.println("You need to login before giving an achievement");
            return;
        }

        if (!admin.getRole().equals("ADMIN")) {
            System.out.println("You need to be an ADMIN to give an achievement");
            return;
        }

        BadgeController badgeController = new BadgeController();

        Badge badge = badgeController.findHandler(id);

        if(badge == null) return;

        User user = userController.findHandler(userId);

        if(user == null) return;

        badge.addUsers(user);

        badgeController.updateHandler(badge);
    }
}
