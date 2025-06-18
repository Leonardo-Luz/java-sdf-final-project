package ifrs.edu.br.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ifrs.edu.br.models.User;

/**
 * UserTest
 */
public class UserTest {
	private User user;

	@BeforeEach
	public void setup() {
		LocalDate birthday = LocalDate.of(2000, 12, 25);

		user = new User("default@test.com", "default test name", "default test password", birthday);
	}

	@Test
	@DisplayName("Test set user email with valid arguments")
	public void UserSetValidEmailTest() {
		user.setEmail("roberto@test.com");

		assertEquals("roberto@test.com", user.getEmail());
	}

	@Test
	@DisplayName("Test set user email with invalid arguments, invalid format")
	public void UserSetInvalidEmailFormatTest() {
		assertThrows(RuntimeException.class, () -> {
			user.setEmail("a");
		});
	}

	@Test
	@DisplayName("Test set user name with valid arguments")
	public void UserSetValidNameTest() {
		user.setName("Roberto");

		assertEquals("Roberto", user.getName());
	}

	@Test
	@DisplayName("Test set user name with invalid arguments, too small")
	public void UserSetInvalidSmallNameTest() {
		assertThrows(RuntimeException.class, () -> {
			user.setName("a");
		});
	}

	@Test
	@DisplayName("Test set user name with invalid arguments, blank")
	public void UserSetInvalidBlankNameTest() {
		assertThrows(RuntimeException.class, () -> {
			user.setName("        ");
		});
	}

	@Test
	@DisplayName("Test set user password with valid arguments")
	public void UserSetValidPasswordTest() {
		user.setPassword("test password");

		assertEquals("test password", user.getPassword());
	}

	@Test
	@DisplayName("Test set user password with invalid arguments, too small")
	public void UserSetInvalidSmallPasswordTest() {
		assertThrows(RuntimeException.class, () -> {
			user.setPassword("a");
		});
	}

	@Test
	@DisplayName("Test set user password with valid arguments, blank")
	public void UserSetInvalidBlankPasswordTest() {
		assertThrows(RuntimeException.class, () -> {
			user.setPassword("        ");
		});
	}

	@Test
	@DisplayName("Test set user birthday with invalid arguments, can't be born in the future")
	public void UserSetInvalidFutureBirthdayTest() {
		LocalDate birthday = LocalDate.of(9999, 12, 25);

		assertThrows(RuntimeException.class, () -> {
			user.setBirthday(birthday);
		});
	}

	@Test
	@DisplayName("Test set user reader role with valid arguments")
	public void UserSetValidReaderRoleTest() {
		user.setRole("READER");

		assertEquals("READER", user.getRole());
	}

	@Test
	@DisplayName("Test set user admin role with valid arguments")
	public void UserSetValidAdminRoleTest() {
		user.setRole("ADMIN");

		assertEquals("ADMIN", user.getRole());
	}

	@Test
	@DisplayName("Test set user role with invalid arguments, invalid option")
	public void UserSetInvalidRoleTest() {
		assertThrows(RuntimeException.class, () -> {
			user.setRole("a");
		});
	}
}
