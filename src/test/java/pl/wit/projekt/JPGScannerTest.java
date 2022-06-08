package pl.wit.projekt;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Class used for testing the functionality of JPGScanner
 * 
 * @author Jakub Chrupek, 19245
 *
 */
public class JPGScannerTest {

	/**
	 * Temp folder for running tests. Gets recreated with each run test.
	 */
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	/**
	 * Ensure that newsly created scanner object has an empty map
	 */
	@Test
	public void testJPGScanner() {
		JPGScanner scanner = new JPGScanner();

		if (scanner != null) {
			assertTrue(scanner.getMetadata().isEmpty());
		}
	}

	/**
	 * Check that scanned files are added to the map
	 * 
	 * @throws IOException
	 */
	@Test
	public void testScanDirectory() throws IOException {
		JPGScanner scanner = new JPGScanner();

		folder.newFile("test_one.jpg");
		folder.newFile("test_two.jpg");
		folder.newFile("test_three.jpg");

		Path tempPath = Paths.get(folder.getRoot().getAbsolutePath());

		scanner.scanDirectory(tempPath);

		if (scanner.getMetadata().isEmpty()) {
			fail("Map should not be empty");
		}
	}
}
