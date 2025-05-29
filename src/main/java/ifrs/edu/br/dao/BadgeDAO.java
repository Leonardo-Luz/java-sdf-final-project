package ifrs.edu.br.dao;

import javax.persistence.EntityManager;

import ifrs.edu.br.models.Badge;

/**
 * BadgeDAO
 */
public class BadgeDAO extends DAO<Badge> {
    public BadgeDAO(EntityManager entityManager) {
        super(Badge.class, entityManager);
    }
}
