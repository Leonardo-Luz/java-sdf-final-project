package ifrs.edu.br.unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ifrs.edu.br.controllers.ReviewController;
import ifrs.edu.br.dao.ReviewDAO;
import ifrs.edu.br.models.Book;
import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;

/**
 * ReviewControllerTest
 */
public class ReviewControllerTest {
	private ReviewController reviewController;
	private ReviewDAO reviewDAOMock;

	@BeforeEach
	public void setup() {
		reviewDAOMock = mock(ReviewDAO.class);

		reviewController = new ReviewController(reviewDAOMock);
	}

	@Test
	@DisplayName("List by book handler valid test")
	public void listByBookHandlerValidTest() {
		Book book = mock(Book.class);
		User user = mock(User.class);

		ArrayList<Review> list = new ArrayList<>();
		list.add(new Review("test title 1", "test text 1", LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25), book, user));
		list.add(new Review("test title 2", "test text 2", LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25), book, user));
		list.add(new Review("test title 3", "test text 3", LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25), book, user));

		when(reviewDAOMock.listByBook(3, 0, book)).thenReturn(list);

		List<Review> reviews = reviewController.listByBookHandler(3, 0, book);

		verify(reviewDAOMock, times(1)).listByBook(3, 0, book);
		assertEquals(list, reviews);
	}

	@Test
	@DisplayName("List by user handler valid test")
	public void listByUserHandlerValidTest() {
		Book book = mock(Book.class);
		User user = mock(User.class);

		ArrayList<Review> list = new ArrayList<>();
		list.add(new Review("test title 1", "test text 1", LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25), book, user));
		list.add(new Review("test title 2", "test text 2", LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25), book, user));
		list.add(new Review("test title 3", "test text 3", LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25), book, user));

		when(reviewDAOMock.listByUser(3, 0, user)).thenReturn(list);

		List<Review> reviews = reviewController.listByUserHandler(3, 0, user);

		verify(reviewDAOMock, times(1)).listByUser(3, 0, user);
		assertEquals(list, reviews);
	}

	@Test
	@DisplayName("Insert handler valid test")
	public void insertHandlerValidTest() {
		Book book = mock(Book.class);
		User user = mock(User.class);

		Review insert = new Review(
				"test title",
				"test text",
				LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25),
				book,
				user);

		reviewController.insertHandler(insert);

		verify(reviewDAOMock, times(1)).insert(insert);
	}

	@Test
	@DisplayName("Find handler valid test")
	public void findHandlerValidTest() {
		Book book = mock(Book.class);
		User user = mock(User.class);

		Review find = new Review(
				"test title",
				"test text",
				LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25),
				book,
				user);

		when(reviewDAOMock.find(1)).thenReturn(find);

		Review review = reviewController.findHandler(1);

		verify(reviewDAOMock, times(1)).find(1);

		assertEquals(find, review);
	}

	@Test
	@DisplayName("Update handler valid test")
	public void updateHandlerValidTest() {
		Book book = mock(Book.class);
		User user = mock(User.class);

		Review update = new Review(
				1,
				"test title",
				"test text",
				LocalDate.of(2000, 12, 25),
				LocalDate.of(2012, 12, 25),
				book,
				user);

		reviewController.updateHandler(update);

		verify(reviewDAOMock, times(1)).update(update);
	}

	@Test
	@DisplayName("Delete handler valid test")
	public void deleteHandlerValidTest() {
		reviewController.deleteHandler(1);

		verify(reviewDAOMock, times(1)).delete(1);
	}
}
