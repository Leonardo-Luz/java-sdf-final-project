package ifrs.edu.br.unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

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

		when(userDAOMock.findByEmail(email)).thenReturn(target);
		when(passwordEncoderMock.encode("123456")).thenReturn("654321");
		when(passwordEncoderMock.matches("123456", "654321")).thenReturn(true);

		User response = userController.loginHandler(email, "123456");

		verify(userDAOMock, times(1)).findByEmail(email);
		assertEquals(target, response);
	}

	@Test
	@DisplayName("Signup handler test with valid arguments")
	public void signupHandlerValidTest() {
		String email = "default@test.com";

		User insert = new User(
				email,
				"Roberto",
				"123456",
				LocalDate.of(2000, 12, 25));

		User target = new User(
				email,
				"Roberto",
				"654321",
				LocalDate.of(2000, 12, 25));

		when(passwordEncoderMock.encode("123456")).thenReturn("654321");

		userController.signup(insert);

		verify(passwordEncoderMock, times(1)).encode("123456");
		verify(userDAOMock, times(1)).insert(target);
	}

	@Test
	@DisplayName("Logout handler valid test")
	public void logoutHandlerValidTest() {
		userController.logout();

		verify(fileManager, times(1)).delete();
	}

	@Test
	@DisplayName("Verify handler valid test")
	public void verifyHandlerValidTest() {
		ArrayList<String> list = new ArrayList<>();
		list.add("default@test.com");
		list.add("123456");

		User target = new User(
				"default@test.com",
				"Roberto",
				"654321",
				LocalDate.of(2000, 12, 25));

		when(fileManager.get()).thenReturn(list);
		when(fileManager.exists()).thenReturn(true);
		when(userDAOMock.findByEmail("default@test.com")).thenReturn(target);
		when(passwordEncoderMock.matches("123456", "654321")).thenReturn(true);

		User user = userController.verify();

		verify(fileManager, times(1)).get();
		verify(userDAOMock, times(1)).findByEmail("default@test.com");
		verify(passwordEncoderMock, times(1)).matches("123456", "654321");

		assertEquals(target, user);
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

	@Test
	@DisplayName("Insert handler valid test")
	public void insertHandlerValidTest() {
		String email = "default@test.com";

		User insert = new User(
				email,
				"Roberto",
				"123456",
				LocalDate.of(2000, 12, 25));

		userController.insertHandler(insert);

		verify(userDAOMock, times(1)).insert(insert);
	}

	@Test
	@DisplayName("Find handler valid test")
	public void findHandlerValidTest() {
		String email = "default@test.com";

		User find = new User(
				email,
				"Roberto",
				"654321",
				LocalDate.of(2000, 12, 25));

		when(userDAOMock.find(1)).thenReturn(find);

		User user = userController.findHandler(1);

		verify(userDAOMock, times(1)).find(1);

		assertEquals(find, user);
	}

	@Test
	@DisplayName("Update handler valid test")
	public void updateHandlerValidTest() {
		String email = "default@test.com";

		User update = new User(
				1,
				email,
				"Roberto",
				"654321",
				LocalDate.of(2000, 12, 25));

		userController.updateHandler(update);

		verify(userDAOMock, times(1)).update(update);
	}

	@Test
	@DisplayName("Delete handler valid test")
	public void deleteHandlerValidTest() {
		userController.deleteHandler(1);

		verify(userDAOMock, times(1)).delete(1);
	}
}
