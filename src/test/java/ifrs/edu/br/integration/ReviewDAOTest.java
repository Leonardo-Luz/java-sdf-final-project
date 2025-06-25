package ifrs.edu.br.integration;

import ifrs.edu.br.models.Book;
import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;
import ifrs.edu.br.Database;
import ifrs.edu.br.DatabaseEnum;
import ifrs.edu.br.dao.BookDAO;
import ifrs.edu.br.dao.ReviewDAO;
import ifrs.edu.br.dao.UserDAO;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewDAOTest {
	private EntityManager entityManager;
	private ReviewDAO reviewDAO;
	private UserDAO userDAO;
	private BookDAO bookDAO;

	private User testUser;
	private Book testBook;

	@BeforeEach
	void setup() {
		Database.connect(DatabaseEnum.TEST);
		entityManager = Database.getEntityManager();

		userDAO = new UserDAO(entityManager);
		bookDAO = new BookDAO(entityManager);
		reviewDAO = new ReviewDAO(entityManager);

		reviewDAO.clear();
		userDAO.clear();
		bookDAO.clear();

		testUser = new User("testuser@test.com", "test user", "test password", LocalDate.of(1990, 1, 1));
		userDAO.insert(testUser);

		testBook = new Book("test book", 123, "test synopsis");
		bookDAO.insert(testBook);
	}

	@AfterEach
	void after() {
		reviewDAO.clear();
		userDAO.clear();
		bookDAO.clear();

		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@Test
	void insertValidTest() {
		Review review = new Review("test title", "test text content", LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 1, 10), testBook, testUser);
		reviewDAO.insert(review);

		assertNotNull(review.getId());
		assertTrue(review.getId() > 0);
	}

	@Test
	void findValidTest() {
		Review review = new Review("test title", "test text content", LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 1, 10), testBook, testUser);
		reviewDAO.insert(review);

		Review found = reviewDAO.find(review.getId());

		assertNotNull(found);
		assertEquals("test title", found.getTitle());
	}

	@Test
	void listByBookValidTest() {
		for (int i = 1; i <= 5; i++) {
			Review r = new Review("test title " + i, "test text " + i, LocalDate.of(2022, 1, i),
					LocalDate.of(2022, 1, i + 1), testBook, testUser);
			reviewDAO.insert(r);
		}

		List<Review> reviews = reviewDAO.listByBook(3, 1, testBook);

		assertNotNull(reviews);
		assertEquals(3, reviews.size());
	}

	@Test
	void listByUserValidTest() {
		Review r1 = new Review("test title 1", "test text 1", LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 1, 2), testBook, testUser);
		Review r2 = new Review("test title 2", "test text 2", LocalDate.of(2022, 2, 1),
				LocalDate.of(2022, 2, 3), testBook, testUser);

		reviewDAO.insert(r1);
		reviewDAO.insert(r2);

		List<Review> reviews = reviewDAO.listByUser(5, 0, testUser);

		assertEquals(2, reviews.size());
	}

	@Test
	void updateValidTest() {
		Review review = new Review("test title", "test text content", LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 1, 10), testBook, testUser);
		reviewDAO.insert(review);

		review.setTitle("new test title");
		review.setText("new test text content");
		reviewDAO.update(review);

		Review updated = reviewDAO.find(review.getId());
		assertEquals("new test title", updated.getTitle());
		assertEquals("new test text content", updated.getText());
	}

	@Test
	void deleteValidTest() {
		Review review = new Review("test title", "test text content", LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 1, 10), testBook, testUser);
		reviewDAO.insert(review);

		int id = review.getId();
		reviewDAO.delete(id);

		Review deleted = reviewDAO.find(id);
		assertNull(deleted);
	}
}
