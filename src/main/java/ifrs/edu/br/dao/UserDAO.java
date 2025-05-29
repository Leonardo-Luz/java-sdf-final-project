package ifrs.edu.br.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ifrs.edu.br.models.User;

/**
 * UserDAO
 */
public class UserDAO extends DAO<User> {
    public UserDAO(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM users u WHERE u.email = :email",
                    User.class);

            query.setParameter("email", email);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
