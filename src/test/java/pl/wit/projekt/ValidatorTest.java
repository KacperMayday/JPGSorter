package pl.wit.projekt;

import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * 
 * Testy dla sprawdzenie poprawności Validator
 * 
 * @author Jakub Chrupek, 19245
 *
 */
public class ValidatorTest {

	/**
	 * Folder tymczasowy używany do testów. Jest odnawiany przed każdym z testów.
	 */
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	/**
	 * Przejście dla poprawnej ilości argumentow
	 */
	@Test
	public void correctLengthTest() {
		String[] args = { "test", "test", "test" };

		Validator.validateLength(args.length);
	}

	/**
	 * Sprawdzenie błedu podczas zlej ilości - za mało
	 */
	@Test(expected = RuntimeException.class)
	public void wrongLengthLessTest() {
		String[] args = { "test", "test" };

		Validator.validateLength(args.length);
	}

	/**
	 * Sprawdzenie błedu podczas zlej ilości - za dużo
	 */
	@Test(expected = RuntimeException.class)
	public void wrongLengthMoreTest() {
		String[] args = { "test", "test", "test", "test" };

		Validator.validateLength(args.length);
	}

	/**
	 * Test błedu dla pustej ścieżki
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void sourcPathExistsTest() {
		Validator.validateSourcePath(folder.getRoot().getAbsolutePath());
	}

	/**
	 * Poprawne zródło kiedy istnieje w nim plik
	 * 
	 * @throws IOException
	 */
	@Test
	public void sourcShouldHaveFileTest() throws IOException {

		folder.newFile("test_one.jpg");

		Validator.validateSourcePath(folder.getRoot().getAbsolutePath());
	}

	/**
	 * Sprawdzenie błędu dla nie istniejacego zródła
	 */
	@Test(expected = IllegalArgumentException.class)
	public void sourcePathDosentExistTest() {
		Validator.validateSourcePath("random/non/existent/source/path");
	}

	/**
	 * Sprawdzenie błędu dla nie istniejacego celu
	 */
	@Test(expected = IllegalArgumentException.class)
	public void targetPathDosentExistTest() {
		Validator.validateTargetPath("random/non/existent/target/path");
	}

	/**
	 * Sprawdzenie błedu kiedy cel nie jest pusty
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void targetPathContainsFilesTest() throws IOException {
		folder.newFile("test_one.jpg");

		Validator.validateTargetPath(folder.getRoot().getAbsolutePath());
	}

	/**
	 * Sprawdzenie przejscia dla poprawnej liczby
	 */
	@Test
	public void threadIsNumTest() {
		String theads = "2";

		Validator.validateThreadNum(theads);
	}

	/**
	 * Sprawdzenie błędu dla liczby jako słowa
	 */
	@Test(expected = IllegalArgumentException.class)
	public void threadIsStringTest() {
		String theads = "two";

		Validator.validateThreadNum(theads);
	}

}
