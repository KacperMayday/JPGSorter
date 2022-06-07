package pl.wit.projekt;

import java.io.File;
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
	public void sourcPathExistsTest() throws IOException {
		Path tempDir = Files.createTempDirectory("sourceTest");

		Validator.validateSourcePath(tempDir.toAbsolutePath().toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void sourcePathDosentExistTest() {
		Validator.validateSourcePath("random/non/existent/source/path");
	}

	@Test(expected = IllegalArgumentException.class)
	public void targetPathDosentExistTest() {
		Validator.validateTargetPath("random/non/existent/target/path");
	}

	@Test(expected = IllegalArgumentException.class)
	public void targetPathContainsFilesTest() throws IOException {
		Path tempDir = Files.createTempDirectory("targetPath");

		Path filename = Path.of(tempDir.toAbsolutePath().toString(), "temp_file.txt");

		@SuppressWarnings("unused")
		File tempFile = new File(filename.toString());

		Validator.validateTargetPath(tempDir.toAbsolutePath().toString());
	}
}
