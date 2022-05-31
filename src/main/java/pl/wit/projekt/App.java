package pl.wit.projekt;

import java.nio.file.Path;

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

		JPGUtility utility = new JPGUtility(threadPool, Path.of(sourcePath), Path.of(targetPath));
	}
}
