package ifrs.edu.br.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ifrs.edu.br.models.Book;
import ifrs.edu.br.models.Review;
import ifrs.edu.br.models.User;

/**
 * ReviewTest
 */
public class ReviewTest {
	private Review review;

	@BeforeEach
	public void setup() {
		Book book = mock(Book.class);
		User user = mock(User.class);

		LocalDate start = LocalDate.of(1000, 12, 25);
		LocalDate end = LocalDate.of(1050, 12, 25);

		review = new Review("default test title", "default test text", start, end, book, user);
	}

	@Test
	@DisplayName("Test set review title with valid arguments")
	public void ReviewSetValidTitleTest() {
		review.setTitle("test title");

		assertEquals("test title", review.getTitle());
	}

	@Test
	@DisplayName("Test set review title with invalid arguments, too small")
	public void ReviewSetInvalidSmallTitleTest() {
		assertThrows(RuntimeException.class, () -> {
			review.setTitle("a");
		});
	}

	@Test
	@DisplayName("Test set review title with valid arguments, blank")
	public void ReviewSetInvalidBlankTitleTest() {
		assertThrows(RuntimeException.class, () -> {
			review.setTitle("        ");
		});
	}

	@Test
	@DisplayName("Test set review text with valid arguments")
	public void ReviewSetValidTextTest() {
		review.setText("test text");

		assertEquals("test text", review.getText());
	}

	@Test
	@DisplayName("Test set review text with invalid arguments, too small")
	public void ReviewSetInvalidSmallTextTest() {
		assertThrows(RuntimeException.class, () -> {
			review.setText("a");
		});
	}

	@Test
	@DisplayName("Test set review text with valid arguments, blank")
	public void ReviewSetInvalidBlankTextTest() {
		assertThrows(RuntimeException.class, () -> {
			review.setText("        ");
		});
	}

	@Test
	@DisplayName("Test set review read end date with invalid arguments, can't finish the book before starting it")
	public void ReviewSetInvalidReadEndDateTest() {
		LocalDate start = LocalDate.of(2000, 12, 25);
		LocalDate end = LocalDate.of(1990, 12, 25);

		review.setReadStartDate(start);

		assertThrows(RuntimeException.class, () -> {
			review.setReadEndDate(end);
		});
	}

	@Test
	@DisplayName("Test set review read start date with invalid arguments, can't start the book after finishing it")
	public void ReviewSetInvalidReadStartDateTest() {
		LocalDate start = LocalDate.of(2000, 12, 25);
		LocalDate end = LocalDate.of(1990, 12, 25);

		review.setReadEndDate(end);

		assertThrows(RuntimeException.class, () -> {
			review.setReadStartDate(start);
		});
	}
}
