package pl.wit.projekt;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class JPGScannerTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testJPGScanner() {
		JPGScanner scanner = new JPGScanner();
		
		if(scanner != null) {
			assertTrue(scanner.getMetadata().isEmpty());
		}
	}

	@Test
	public void testScanDirectory() throws IOException {
		JPGScanner scanner = new JPGScanner();
		
		folder.newFile("test_one.jpg");
		folder.newFile("test_two.jpg");
		folder.newFile("test_three.jpg");
		
		Path tempPath = Paths.get(folder.getRoot().getAbsolutePath());
		
		scanner.scanDirectory(tempPath);
		
		if(scanner.getMetadata().isEmpty()) {
			fail("Map should not be empty");
		}
	}
}
