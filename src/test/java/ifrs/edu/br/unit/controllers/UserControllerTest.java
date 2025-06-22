package ifrs.edu.br.unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ifrs.edu.br.controllers.UserController;
import ifrs.edu.br.dao.UserDAO;
import ifrs.edu.br.models.User;
import ifrs.edu.br.utils.FileManager;

/**
 * UserControllerTest
 */
public class UserControllerTest {
	private UserController userController;
	private UserDAO userDAOMock;
	private BCryptPasswordEncoder passwordEncoderMock;
	private FileManager fileManager;

	@BeforeEach
	public void setup() {
		userDAOMock = mock(UserDAO.class);
		fileManager = mock(FileManager.class);
		passwordEncoderMock = mock(BCryptPasswordEncoder.class);

		userController = new UserController(userDAOMock, fileManager, passwordEncoderMock);
	}

	@Test
	@DisplayName("Login handler test with valid arguments")
	public void loginHandlerValidTest() {
		String email = "default@test.com";

		User target = new User(
				email,
				"Roberto",
				"654321",
				LocalDate.of(2000, 12, 25));

		when(userDAOMock.login(email, "654321")).thenReturn(target);
		when(passwordEncoderMock.encode("123456")).thenReturn("654321");

		User response = userController.loginHandler(email, "123456");

		verify(userDAOMock, times(1)).login(email, "654321");
		assertEquals(target, response);
	}

	@Test
	@DisplayName("Find by email handler test with valid arguments")
	public void findByEmailHandlerValidTest() {
		String email = "default@test.com";

		User target = new User(
				email,
				"Roberto",
				"654321",
				LocalDate.of(2000, 12, 25));

		when(userDAOMock.findByEmail(email)).thenReturn(target);

		User response = userController.findByEmailHandler(email);

		verify(userDAOMock, times(1)).findByEmail(email);
		assertEquals(target, response);
	}
}
