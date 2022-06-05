package pl.wit.projekt;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Map;

public class App {
	/**
	 * parsowanie argumentów (wraz z ich walidacją) i uruchomienie głównej klasy
	 * JPGUtility
	 *
	 */
	public static void main(String[] args) {
		// robocza inicjalizacja parametrów
		int threadPool = 0;
		String sourcePath = "";
		String targetPath = "";

		// flow
		JPGScanner scanner = new JPGScanner(sourcePath);
		Map<FileTime, List<Path>> mapa = scanner.getMetadata();

		JPGUtility utility = new JPGUtility(threadPool, Path.of(targetPath));
		utility.copyFiles(mapa);
	}
}
