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

import ifrs.edu.br.controllers.BadgeController;
import ifrs.edu.br.dao.BadgeDAO;
import ifrs.edu.br.models.Badge;

/**
 * BadgeControllerTest
 */
public class BadgeControllerTest {
	private BadgeController badgeController;
	private BadgeDAO badgeDAOMock;

	@BeforeEach
	public void setup() {
		badgeDAOMock = mock(BadgeDAO.class);

		badgeController = new BadgeController(badgeDAOMock);
	}

	@Test
	@DisplayName("List handler valid test")
	public void listHandlerValidTest() {
		ArrayList<Badge> list = new ArrayList<>();
		list.add(new Badge("test name 1", "test requirements 1"));
		list.add(new Badge("test name 2", "test requirements 2"));
		list.add(new Badge("test name 3", "test requirements 3"));

		when(badgeDAOMock.list(3, 0)).thenReturn(list);

		List<Badge> badges = badgeController.listHandler(3, 0);

		verify(badgeDAOMock, times(1)).list(3, 0);
		assertEquals(list, badges);
	}

	@Test
	@DisplayName("Insert handler valid test")
	public void insertHandlerValidTest() {
		Badge insert = new Badge("test name", "test requirements");

		badgeController.insertHandler(insert);

		verify(badgeDAOMock, times(1)).insert(insert);
	}

	@Test
	@DisplayName("Find handler valid test")
	public void findHandlerValidTest() {
		Badge find = new Badge("test name", "test requirements");

		when(badgeDAOMock.find(1)).thenReturn(find);

		Badge badge = badgeController.findHandler(1);

		verify(badgeDAOMock, times(1)).find(1);

		assertEquals(find, badge);
	}

	@Test
	@DisplayName("Update handler valid test")
	public void updateHandlerValidTest() {
		Badge update = new Badge("test name", "test requirements");

		badgeController.updateHandler(update);

		verify(badgeDAOMock, times(1)).update(update);
	}

	@Test
	@DisplayName("Delete handler valid test")
	public void deleteHandlerValidTest() {
		badgeController.deleteHandler(1);

		verify(badgeDAOMock, times(1)).delete(1);
	}
}
