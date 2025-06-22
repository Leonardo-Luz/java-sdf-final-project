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

    public UserDAO() {
        super(User.class);
    }

    @Override
    public User find(int id) {
        User user = entityManager.find(User.class, id);
        user.setAge();

        return user;
    }

    public User login(String email, String password) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM users u WHERE u.email = :email AND u.password = :password", User.class);

            query.setParameter("email", email);
            query.setParameter("password", password);

            User user = query.getSingleResult();
            user.setAge();

            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM users u WHERE u.email = :email",
                    User.class);

            query.setParameter("email", email);

            User user = query.getSingleResult();
            user.setAge();

            return user;
        } catch (NoResultException e) {
            return null;
        }
    }
}
