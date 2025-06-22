package ifrs.edu.br.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;
import ifrs.edu.br.models.Book;

/**
 * ReviewDAO
 */
public class ReviewDAO extends DAO<Review> {
    public ReviewDAO(EntityManager entityManager) {
        super(Review.class, entityManager);
    }

    public ReviewDAO() {
        super(Review.class);
    }

    public List<Review> listByBook(int limit, int offset, Book book) {
        TypedQuery<Review> sql = this.entityManager.createQuery("SELECT r FROM reviews r WHERE r.book = :book",
                Review.class);

        sql.setParameter("book", book);

        return sql.setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public List<Review> listByUser(int limit, int offset, User user) {
        TypedQuery<Review> sql = this.entityManager.createQuery("SELECT r FROM reviews r WHERE r.user = :user",
                Review.class);

        sql.setParameter("user", user);

        return sql.setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
