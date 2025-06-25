package ifrs.edu.br.unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ifrs.edu.br.controllers.BookController;
import ifrs.edu.br.dao.BookDAO;
import ifrs.edu.br.models.Book;

/**
 * BookControllerTest
 */
public class BookControllerTest {
	private BookController bookController;
	private BookDAO bookDAOMock;

	@BeforeEach
	public void setup() {
		bookDAOMock = mock(BookDAO.class);

		bookController = new BookController(bookDAOMock);
	}

	@Test
	@DisplayName("List handler valid test")
	public void listHandlerValidTest() {
		ArrayList<Book> list = new ArrayList<>();
		list.add(new Book("test title 1", 100, "test synopsis 1"));
		list.add(new Book("test title 2", 100, "test synopsis 2"));
		list.add(new Book("test title 3", 100, "test synopsis 3"));

		when(bookDAOMock.list(3, 0)).thenReturn(list);

		List<Book> books = bookController.listHandler(3, 0);

		verify(bookDAOMock, times(1)).list(3, 0);
		assertEquals(list, books);
	}

	@Test
	@DisplayName("Insert handler valid test")
	public void insertHandlerValidTest() {
		Book insert = new Book("test title", 100, "test synopsis");

		bookController.insertHandler(insert);

		verify(bookDAOMock, times(1)).insert(insert);
	}

	@Test
	@DisplayName("Find handler valid test")
	public void findHandlerValidTest() {
		Book find = new Book("test title", 100, "test synopsis");

		when(bookDAOMock.find(1)).thenReturn(find);

		Book book = bookController.findHandler(1);

		verify(bookDAOMock, times(1)).find(1);

		assertEquals(find, book);
	}

	@Test
	@DisplayName("Update handler valid test")
	public void updateHandlerValidTest() {
		Book update = new Book("test title", 100, "test synopsis");

		bookController.updateHandler(update);

		verify(bookDAOMock, times(1)).update(update);
	}

	@Test
	@DisplayName("Delete handler valid test")
	public void deleteHandlerValidTest() {
		bookController.deleteHandler(1);

		verify(bookDAOMock, times(1)).delete(1);
	}
}
