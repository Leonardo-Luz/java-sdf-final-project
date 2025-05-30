package ifrs.edu.br.cli;

import java.util.List;

import javax.persistence.EntityManager;

import ifrs.edu.br.dao.BadgeDAO;
import ifrs.edu.br.models.Badge;

/**
 * Achievements
 */
public class Achievements {
    private static EntityManager entityManager;

    public static void command(String args[], EntityManager entityManager) {
        Achievements.entityManager = entityManager;

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
        BadgeDAO badgeDAO = new BadgeDAO(entityManager);

        List<Badge> badges = badgeDAO.list(10, 0); // TODO: Add pagination

        if (badges.size() == 0) {
            System.out.println("There're no registered achievements");
            return;
        }

        System.out.println();
        badges.forEach((badge) -> {
            System.out.println(badge);
            System.out.println();
        });
    }

    public static void unlockAchievement(int id) {
        BadgeDAO badgeDAO = new BadgeDAO(entityManager);

        Badge badge = badgeDAO.find(id);

        if (badge == null) {
            System.out.println("Achievement not found!");
            return;
        }

        System.out.println(badge);
    }
}
