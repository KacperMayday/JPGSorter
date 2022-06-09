package pl.wit.projekt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Klasa wątku odpowiedzialna za kopiowanie pojedynczego pliku zdjęciowego
 * 
 * @author Kacper
 *
 */
public class CopyThread implements Runnable {
	// logger
	protected static final Logger log = LogManager.getLogger(CopyThread.class.getName());

	// ścieżka zródłowa do pliku
	private final Path source;
	// docelowy katalog do którego należy skopiować plik
	private final Path targetDirectory;
	// mapa ścieżka -> maksymalny numer pliku w danej ścieżce
	private static final Map<Path, Integer> mapFolders = new HashMap<>();

	public CopyThread(Path source, Path targetDirectory) {
		this.source = source;
		this.targetDirectory = targetDirectory;
	}

	@Override
	public void run() {
		Path targetPath;
		synchronized (mapFolders) {
			targetPath = getFilePath();
		}
		try {
			Files.copy(source, targetPath);
			log.trace("File copied from " + source + " to " + targetPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return "CopyThread from " + source + " to " + targetDirectory;
	}

	/**
	 * Metoda zwracająca docelową lokalizację (wraz z nazwą) pliku zdjęciowego
	 * 
	 * @return ścieżka do pliku docelowego zawierająca pierwszy wolny numer +
	 *         rozszerzenie .jpg
	 */
	private synchronized Path getFilePath() {
		int fileNo = mapFolders.containsKey(targetDirectory) ? mapFolders.get(targetDirectory) + 1 : 1;
		mapFolders.put(targetDirectory, fileNo);
		return targetDirectory.resolve(fileNo + ".jpg");
	}
}