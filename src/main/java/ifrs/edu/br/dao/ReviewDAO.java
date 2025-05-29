package ifrs.edu.br.dao;

import javax.persistence.EntityManager;

import ifrs.edu.br.models.Review;

/**
 * ReviewDAO
 */
public class ReviewDAO extends DAO<Review> {
    public ReviewDAO(EntityManager entityManager) {
        super(Review.class, entityManager);
    }
}
