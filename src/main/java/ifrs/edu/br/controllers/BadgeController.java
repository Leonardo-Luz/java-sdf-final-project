package ifrs.edu.br.controllers;

import java.util.List;

import ifrs.edu.br.dao.BadgeDAO;
import ifrs.edu.br.models.Badge;

/**
 * BadgeController
 */
public class BadgeController implements Controller<Badge> {
    private BadgeDAO badgeDAO;

    public BadgeController(BadgeDAO badgeDAO) {
        this.badgeDAO = badgeDAO;
    }

    public List<Badge> listHandler(int limit, int offset) {
        try {
            if (limit < 0)
                throw new RuntimeException("Limit can't be negative");
            if (offset < 0)
                throw new RuntimeException("Offset can't be negative");

            if (limit > 100)
                throw new RuntimeException("Limit can't exceed 100");

            List<Badge> list = badgeDAO.list(limit, offset);

            if (list.isEmpty())
                throw new RuntimeException("Badge list is empty!");

            return list;
        } catch (RuntimeException err) {
            System.out.println(err);
            return null;
        }
    }

    @Override
    public void insertHandler(Badge object) {
        try {
            if (object == null)
                throw new RuntimeException("Badge can't be null");

            badgeDAO.insert(object);
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }

    @Override
    public Badge findHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            Badge badge = badgeDAO.find(id);

            if (badge == null)
                throw new RuntimeException("Badge not found!");

            return badge;
        } catch (RuntimeException err) {
            System.out.println(err);
            return null;
        }
    }

    @Override
    public void updateHandler(Badge object) {
        try {
            if (object == null)
                throw new RuntimeException("Badge can't be null");

            badgeDAO.update(object);
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }

    @Override
    public void deleteHandler(int id) {
        try {
            if (id < 0)
                throw new RuntimeException("Id can't be negative");

            badgeDAO.delete(id);
        } catch (RuntimeException err) {
            System.out.println(err);
        }
    }
}
