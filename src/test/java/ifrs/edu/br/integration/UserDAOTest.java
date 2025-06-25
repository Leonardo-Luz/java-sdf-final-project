package ifrs.edu.br.integration;

import ifrs.edu.br.models.User;
import ifrs.edu.br.Database;
import ifrs.edu.br.DatabaseEnum;
import ifrs.edu.br.dao.UserDAO;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

	private EntityManager entityManager;
	private UserDAO userDAO;

	@BeforeEach
	void setup() {
		Database.connect(DatabaseEnum.TEST);
		entityManager = Database.getEntityManager();
		userDAO = new UserDAO(entityManager);
		userDAO.clear();
	}

	@AfterEach
	void after() {
		userDAO.clear();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@Test
	void insertValidTest() {
		User user = new User("default@test.com", "test name", "test password", LocalDate.of(2000, 12, 25));
		userDAO.insert(user);

		assertNotNull(user.getId());
	}

	@Test
	void findValidTest() {
		User user = new User("default@test.com", "test name", "test password", LocalDate.of(2000, 12, 25));
		userDAO.insert(user);

		User found = userDAO.find(user.getId());

		assertNotNull(found);
	}

	@Test
	void findByEmailValidTest() {
		User user = new User("default@test.com", "test name", "test password", LocalDate.of(2000, 12, 25));
		userDAO.insert(user);

		User found = userDAO.findByEmail("default@test.com");

		assertNotNull(found);
	}

	@Test
	void loginValidTest() {
		User user = new User("default@test.com", "test name", "test password", LocalDate.of(2000, 12, 25));
		userDAO.insert(user);

		User loggedIn = userDAO.login("default@test.com", "test password");

		assertNotNull(loggedIn);
	}

	@Test
	void updateValidTest() {
		User user = new User("default@test.com", "test name", "test password", LocalDate.of(2000, 12, 25));
		userDAO.insert(user);

		user.setName("new test name");
		userDAO.update(user);

		User updated = userDAO.find(user.getId());
		assertEquals("new test name", updated.getName());
	}

	@Test
	void deleteValidTest() {
		User user = new User("default@test.com", "test name", "test password", LocalDate.of(2000, 12, 25));
		userDAO.insert(user);

		int id = user.getId();
		userDAO.delete(id);

		User deleted = userDAO.find(id);
		assertNull(deleted);
	}
}
