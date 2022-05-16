package pl.wit.projekt;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class JPGScanner {
	// Data utworzenia -> Lista nazw plików
	private Map<Date, List<String>> mapMetaFilenames;

	public JPGScanner(String sourcePath) {
		// inicjalizacja mapy
		mapMetaFilenames = null;

		List<String> listFilenames = scanDirectory(sourcePath);
		getMetadata(listFilenames);
	}

	/**
	 * Metoda skanująca katalog w poszukiwaniu plików JPG
	 * 
	 * @param sourcePath
	 */
	private List<String> scanDirectory(String sourcePath) {
		return null;
	}

	/**
	 * Metoda uzupełniająca mapę na podstawie nazw plików i ich metadanych
	 * 
	 * @param listFilenames
	 */
	private void getMetadata(List<String> listFilenames) {

	}

	public Map<Date, List<String>> getMapMetaFilenames() {
		return mapMetaFilenames;
	}
}
