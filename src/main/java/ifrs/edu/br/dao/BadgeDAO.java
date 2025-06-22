package ifrs.edu.br.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ifrs.edu.br.models.Badge;

/**
 * BadgeDAO
 */
public class BadgeDAO extends DAO<Badge> {
    public BadgeDAO(EntityManager entityManager) {
        super(Badge.class, entityManager);
    }

    public BadgeDAO() {
        super(Badge.class);
    }

    public List<Badge> list(int limit, int offset) {
        TypedQuery<Badge> sql = this.entityManager.createQuery("SELECT b FROM badges b", Badge.class);

        return sql.setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
