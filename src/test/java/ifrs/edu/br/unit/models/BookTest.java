package ifrs.edu.br.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ifrs.edu.br.models.Book;

/**
 * BookTest
 */
public class BookTest {
	private Book book;

	@BeforeEach
	public void setup() {
		book = new Book("default test title", 10, "default test synopsis");
	}

	@Test
	@DisplayName("Test set book title with valid arguments")
	public void BookSetValidTitleTest() {
		book.setTitle("test title");

		assertEquals("test title", book.getTitle());
	}

	@Test
	@DisplayName("Test set book title with invalid arguments, too small")
	public void BookSetInvalidSmallTitleTest() {
		assertThrows(RuntimeException.class, () -> {
			book.setTitle("a");
		});
	}

	@Test
	@DisplayName("Test set book title with valid arguments, blank")
	public void BookSetInvalidBlankTitleTest() {
		assertThrows(RuntimeException.class, () -> {
			book.setTitle("        ");
		});
	}

	@Test
	@DisplayName("Test set book pages with valid arguments")
	public void BookSetValidPagesTest() {
		book.setPages(100);

		assertEquals(100, book.getPages());
	}

	@Test
	@DisplayName("Test set book pages with invalid arguments, negative")
	public void BookSetInvalidNegativePagesTest() {
		assertThrows(RuntimeException.class, () -> {
			book.setPages(-10);
		});
	}

	@Test
	@DisplayName("Test set book pages with invalid arguments, zero")
	public void BookSetInvalidZeroPagesTest() {
		assertThrows(RuntimeException.class, () -> {
			book.setPages(0);
		});
	}

	@Test
	@DisplayName("Test set book synopsis with valid arguments")
	public void BookSetValidSynopsisTest() {
		book.setSynopsis("test synopsis");

		assertEquals("test synopsis", book.getSynopsis());
	}

	@Test
	@DisplayName("Test set book synopsis with invalid arguments, too small")
	public void BookSetInvalidSmallSynopsisTest() {
		assertThrows(RuntimeException.class, () -> {
			book.setSynopsis("a");
		});
	}

	@Test
	@DisplayName("Test set book synopsis with valid arguments, blank")
	public void BookSetInvalidBlankSynopsisTest() {
		assertThrows(RuntimeException.class, () -> {
			book.setSynopsis("        ");
		});
	}
}
