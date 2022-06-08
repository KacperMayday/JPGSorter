package pl.wit.projekt;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
	/**
	 * parsowanie argumentów (wraz z ich walidacją) i uruchomienie głównej klasy
	 * JPGUtility
	 *
	 */
	public static void main(String[] args) {

		Validator.validate(args);

		Path sourcePath = Paths.get(args[0]);
		Path targetPath = Paths.get(args[1]);
		int threadPool = Integer.parseInt(args[2]);

		JPGScanner scanner = new JPGScanner();

		if (scanner != null) {
			try {
				scanner.scanDirectory(sourcePath);
			} catch (IOException e) {
				System.out.println("error occured while scanning for source files: " + e);
				System.exit(1);
			}
		}

		JPGUtility utility = new JPGUtility(threadPool, targetPath);
		try {
			utility.copyFiles(scanner.getMetadata());
		} catch (IOException e) {
			System.out.println("error occured while copying files from source to target: " + e);
			System.exit(1);
		}
	}
}