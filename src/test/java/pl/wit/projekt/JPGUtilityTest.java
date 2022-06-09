package pl.wit.projekt;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Klasa testów jednostkowych dla klasy JPGUtility
 * 
 * @author Kacper
 *
 */
public class JPGUtilityTest {
	// instancja logera
	protected static final Logger log = LogManager.getLogger(JPGUtilityTest.class.getName());
	// ścieżka docelowa do kopiowania plików
	private static Path targetPath;
	// mapa dat utworzenia -> listy ścieżek do plików zdjęciowych
	private static Map<LocalDate, List<Path>> mapMetaFilenames;

	/**
	 * Metoda usuwająca rekursywnie całą zawartość podanego katalogu
	 * 
	 * @param directory katalog do wyczyszczenia
	 */
	private static void cleanDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					cleanDirectory(f);
				}
				f.delete();
			}
		}
	}

	/**
	 * Metoda przygotowująca strukturę katalogów pod testy jednostkowe w tym:
	 * konfiguruje ustawienia logera, ustawia domyślne wartości zmiennych, czyści
	 * katalog docelowy
	 *
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		// konfiguracja logera
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.TRACE);

		// przypisanie lokalizacji katalogu zródłowego i docelowego
		targetPath = Path.of(System.getProperty("user.dir")).resolve("src/test/resources/JPGUtilityTest/target");
		// ścieżka zródłowa do kopiowania plików
		Path sourcePath = Path.of(System.getProperty("user.dir")).resolve("src/test/resources/source");

		// czyszczenie katalogu docelowego
		cleanDirectory(new File(targetPath.toString()));

		// mapa plików do skopiowania
		mapMetaFilenames = Map.of(
				LocalDate.parse("2022-06-08"), List.of(sourcePath.resolve("test_image1.jpg"), sourcePath.resolve("test_image2.jpg")),
				LocalDate.parse("2022-06-09"), List.of(sourcePath.resolve("test_image2.jpg")));
	}

	/**
	 * Metoda mapująca zawartość podanego katalogu
	 * 
	 * @param path ścieżka katalogu do zmapowania
	 * @return zwraca mapę wartości data -> lista nazw plików
	 */
	private Map<LocalDate, List<String>> createDirectoryMap(Path path) {
		Map<LocalDate, List<String>> result = new HashMap<>();
		File[] files = new File(path.toString()).listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					result.put(LocalDate.parse(f.getName()),
							Arrays.stream(f.listFiles()).map(File::getName).collect(Collectors.toList()));
				}
			}
		}
		return result;
	}

	@Test
	public void copyFilesTest() {
		JPGUtility utility = new JPGUtility(10, targetPath);

		Map<LocalDate, List<String>> expectedResult = new HashMap<>();
		for (LocalDate key : mapMetaFilenames.keySet()) {

			List<String> fileList = IntStream.range(0, mapMetaFilenames.get(key).size()).mapToObj(n -> (n + 1) + ".jpg")
					.collect(Collectors.toList());
			expectedResult.put(key, fileList);
		}

		try {
			utility.copyFiles(mapMetaFilenames);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Error!");
		}
		Map<LocalDate, List<String>> result = createDirectoryMap(targetPath);

		// sprawdzenie stworzonych katalogów
		assertEquals(expectedResult, result);
		result.keySet().forEach(e -> log.trace(e.toString() + " " + result.get(e)));
	}
}
