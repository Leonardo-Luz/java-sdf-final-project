package ifrs.edu.br.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ifrs.edu.br.models.Book;

/**
 * BookDAO
 */
public class BookDAO extends DAO<Book> {
    public BookDAO(EntityManager entityManager) {
        super(Book.class, entityManager);
    }

    public List<Book> list(int limit, int offset) {
        TypedQuery<Book> sql = this.entityManager.createQuery("SELECT b FROM books b", Book.class);

        return sql.setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
