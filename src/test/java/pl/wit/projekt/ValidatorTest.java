package pl.wit.projekt;

import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * 
 * A class used for testing the Validator class
 * 
 * @author Jakub Chrupek, 19245
 *
 */
public class ValidatorTest {

	/**
	 * Temp folder for running tests. Gets recreated with each run test.
	 */
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	/**
	 * Ensure no error with correct number of arguments.
	 */
	@Test
	public void correctLengthTest() {
		String[] args = { "test", "test", "test" };

		Validator.validateLength(args.length);
	}

	/**
	 * Ensure correct number of arguments.
	 */
	@Test(expected = RuntimeException.class)
	public void wrongLengthTest() {
		String[] args = { "test", "test" };

		Validator.validateLength(args.length);
	}

	/**
	 * Make sure no error is thrown when source exists.
	 * 
	 * @throws IOException
	 */
	@Test
	public void sourcPathExistsTest() throws IOException {
		Validator.validateSourcePath(folder.getRoot().getAbsolutePath());
	}

	/**
	 * Make sure error is thrown when source dosen't exist.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void sourcePathDosentExistTest() {
		Validator.validateSourcePath("random/non/existent/source/path");
	}

	/**
	 * Make sure error is thrown when target dosen't exist.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void targetPathDosentExistTest() {
		Validator.validateTargetPath("random/non/existent/target/path");
	}

	/**
	 * Make sures that errror is thrown when target directory is not empty.
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void targetPathContainsFilesTest() throws IOException {
		folder.newFile("test_one.jpg");

		Validator.validateTargetPath(folder.getRoot().getAbsolutePath());
	}
}
