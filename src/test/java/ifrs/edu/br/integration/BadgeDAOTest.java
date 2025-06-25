package ifrs.edu.br.integration;

import ifrs.edu.br.models.Badge;
import ifrs.edu.br.Database;
import ifrs.edu.br.DatabaseEnum;
import ifrs.edu.br.dao.BadgeDAO;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BadgeDAOTest {
	private EntityManager entityManager;
	private BadgeDAO badgeDAO;

	@BeforeEach
	void setup() {
		Database.connect(DatabaseEnum.TEST);
		entityManager = Database.getEntityManager();
		badgeDAO = new BadgeDAO(entityManager);
		badgeDAO.clear();
	}

	@AfterEach
	void after() {
		badgeDAO.clear();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@Test
	void insertValidTest() {
		Badge badge = new Badge("test name", "test requeriment");
		badgeDAO.insert(badge);

		assertNotNull(badge.getId());
	}

	@Test
	void findValidTest() {
		Badge badge = new Badge("test name", "test requeriment");
		badgeDAO.insert(badge);

		Badge found = badgeDAO.find(badge.getId());

		assertNotNull(found);
		assertEquals("test name", found.getName());
	}

	@Test
	void listValidTest() {
		for (int i = 1; i <= 5; i++) {
			badgeDAO.insert(new Badge("test name " + i, "test requeriment " + i));
		}

		List<Badge> badges = badgeDAO.list(3, 1);

		assertNotNull(badges);
		assertEquals(3, badges.size());
		assertEquals("test name 2", badges.get(0).getName());
	}

	@Test
	void updateValidTest() {
		Badge badge = new Badge("test name", "test requeriment");
		badgeDAO.insert(badge);

		badge.setName("new test name");
		badgeDAO.update(badge);

		Badge updated = badgeDAO.find(badge.getId());
		assertEquals("new test name", updated.getName());
	}

	@Test
	void deleteValidTest() {
		Badge badge = new Badge("test name", "test requeriment");
		badgeDAO.insert(badge);

		int id = badge.getId();
		badgeDAO.delete(id);

		Badge deleted = badgeDAO.find(id);
		assertNull(deleted);
	}
}
