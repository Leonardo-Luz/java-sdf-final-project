package ifrs.edu.br.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ifrs.edu.br.models.Badge;

/**
 * BadgeTest
 */
public class BadgeTest {
	private Badge badge;

	@BeforeEach
	public void setup() {
		badge = new Badge("default test name", "default test requirement");
	}

	@Test
	@DisplayName("Test set badge name with valid arguments")
	public void badgeSetValidNameTest() {
		badge.setName("test name");

		assertEquals("test name", badge.getName());
	}

	@Test
	@DisplayName("Test set badge name with invalid arguments, too small")
	public void badgeSetInvalidSmallNameTest() {
		assertThrows(RuntimeException.class, () -> {
			badge.setName("a");
		});
	}

	@Test
	@DisplayName("Test set badge name with invalid arguments, blank")
	public void badgeSetInvalidNullNameTest() {
		assertThrows(RuntimeException.class, () -> {
			badge.setName("        ");
		});
	}

	@Test
	@DisplayName("Test set badge requirement with valid arguments")
	public void badgeSetValidRequirementTest() {
		badge.setRequirements("test requirement");

		assertEquals("test requirement", badge.getRequirements());
	}

	@Test
	@DisplayName("Test set badge requirement with invalid arguments, too small")
	public void badgeSetInvalidSmallRequirementTest() {
		assertThrows(RuntimeException.class, () -> {
			badge.setRequirements("a");
		});
	}

	@Test
	@DisplayName("Test set badge requirement with invalid arguments, null")
	public void badgeSetInvalidNullRequirementTest() {
		assertThrows(RuntimeException.class, () -> {
			badge.setRequirements("        ");
		});
	}
}
