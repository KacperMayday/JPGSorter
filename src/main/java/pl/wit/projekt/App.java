package pl.wit.projekt;

public class App {
	/**
	 * parsowanie argumentów (wraz z ich walidacją) i uruchomienie głównej klasy
	 * JPGUtility
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// robocza inicjalizacja parametrów
		int threadPool = 0;
		String sourcePath = null;
		String targetPath = null;

		JPGUtility project = new JPGUtility(threadPool, sourcePath, targetPath);
	}
}
