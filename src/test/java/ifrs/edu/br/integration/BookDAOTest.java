package ifrs.edu.br.integration;

import ifrs.edu.br.models.Book;
import ifrs.edu.br.Database;
import ifrs.edu.br.DatabaseEnum;
import ifrs.edu.br.dao.BookDAO;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookDAOTest {
	private EntityManager entityManager;
	private BookDAO bookDAO;

	@BeforeEach
	void setUp() {
		Database.connect(DatabaseEnum.TEST);
		entityManager = Database.getEntityManager();
		bookDAO = new BookDAO(entityManager);
		bookDAO.clear();
	}

	@AfterEach
	void tearDown() {
		bookDAO.clear();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@Test
	void insertValidTest() {
		Book book = new Book("test title", 123, "test synopsis");
		bookDAO.insert(book);

		assertNotNull(book.getId());
	}

	@Test
	void findValidTest() {
		Book book = new Book("test title", 123, "test synopsis");
		bookDAO.insert(book);

		Book found = bookDAO.find(book.getId());

		assertNotNull(found);
	}

	@Test
	void listValidTest() {
		for (int i = 1; i <= 5; i++) {
			bookDAO.insert(new Book("test title " + i, 100 + i, "test synopsis " + i));
		}

		List<Book> books = bookDAO.list(3, 1);

		assertEquals(3, books.size());
	}

	@Test
	void updateValidTest() {
		Book book = new Book("test title", 123, "test synopsis");
		bookDAO.insert(book);

		book.setTitle("new test title");
		book.setPages(456);
		book.setSynopsis("new test synopsis");
		bookDAO.update(book);

		Book updated = bookDAO.find(book.getId());
		assertEquals("new test title", updated.getTitle());
	}

	@Test
	void deleteValidTest() {
		Book book = new Book("test title", 123, "test synopsis");
		bookDAO.insert(book);

		int id = book.getId();
		bookDAO.delete(id);

		Book deleted = bookDAO.find(id);
		assertNull(deleted);
	}
}
