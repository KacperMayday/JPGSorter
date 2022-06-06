package pl.wit.projekt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class ValidatorTest {

	@Test
	public void correctLengthTest() {

		String[] args = { "test", "test", "test" };

		Validator.validateLength(args.length);
	}

	@Test(expected = RuntimeException.class)
	public void wrongLengthTest() {

		String[] args = { "test", "test" };

		Validator.validateLength(args.length);

	}

	@Test
	public void sourceTest() throws IOException {
		
		Path tempDir = Files.createTempDirectory("sourceTest");
		
		Validator.validateSourcePath(tempDir.toAbsolutePath().toString());
	}

}
