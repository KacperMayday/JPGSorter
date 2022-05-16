package pl.wit.projekt;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class JPGUtility {
	// limit wątków aktywnych w danym momencie czasu
	private int threadPoolLimit;
	// ścieżka do katalogu zródłowego
	private String sourcePath;
	// ścieżka do katalogu docelowego
	private String targetPath;

	public JPGUtility(int threadPoolLimit, String sourcePath, String targetPath) {
		this.threadPoolLimit = threadPoolLimit;
		this.sourcePath = sourcePath;
		this.targetPath = targetPath;

		JPGScanner scanner = new JPGScanner(sourcePath);

		copyFiles(scanner.getMapMetaFilenames());
	}

	/**
	 * Metoda tworząca katalogi na podstawie kluczy mapy oraz kopiująca do nich
	 * pliki wielowątkowo, nazywając je kolejnymi liczbami całkowitymi iterate over
	 * 
	 * @param mapMetaFilenames
	 */
	private void copyFiles(Map<Date, List<String>> mapMetaFilenames) {

	}
}
