package pl.wit.projekt;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Klasa główna aplikacji
 *
 * @author Jakub Chrupek, 19245
 */
public class App {
	/**
	 * parsowanie argumentów (wraz z ich walidacją), skanowanie folderu docelowego
	 * i współbieżne kopiowanie plików .jpg
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
				System.out.println("error occurred while scanning for source files: " + e);
				System.exit(1);
			}
		}

		JPGUtility utility = new JPGUtility(threadPool, targetPath);
		try {
			utility.copyFiles(scanner.getMetadata());
		} catch (IOException e) {
			System.out.println("error occurred while copying files from source to target: " + e);
			System.exit(1);
		}
	}
}