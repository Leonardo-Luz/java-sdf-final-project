package ifrs.edu.br.unit.dao;

import static org.mockito.Mockito.mock;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ifrs.edu.br.dao.UserDAO;

/**
 * UserDAOTest
 */
public class UserDAOTest {
	private UserDAO userDAO;

	@BeforeEach
	public void setup() {
		EntityManager entityManager = mock(EntityManager.class);

		userDAO = new UserDAO(entityManager);
	}

	@Test
	@DisplayName("")
	public void insertTest() {

	}
}
