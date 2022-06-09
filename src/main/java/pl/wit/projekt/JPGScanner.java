package pl.wit.projekt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 
 * Klasa skanująca katalog zródłowy mapując pliki .jpg
 *
 * @author Jakub Chrupek, 19245
 *
 */
public class JPGScanner {

	// mapa przechowująca listę ścieżek do plików dla poszczególnych dat utworzenia
	private final Map<LocalDate, List<Path>> filesMetadata;

	/**
	 * Inicjalizacja mapy
	 */
	public JPGScanner() {
		filesMetadata = new HashMap<>();
	}

	/**
	 * Metoda skanująca podany katalog w poszukiwaniu plików .jpg i mapuje je z podziałem na daty utworzenia
	 * 
	 * @param path ścieżka katalogu do skanowania
	 * @throws IOException
	 */
	public void scanDirectory(Path path) throws IOException {
		List<Path> result;

		// Scan the passed in directory and filter out files that end with .jpg
		try (Stream<Path> walk = Files.walk(path)) {
			result = walk.filter(Files::isRegularFile).filter(p -> p.getFileName().toString().endsWith(".jpg"))
					.collect(Collectors.toList());
		}

		// Loop through each resulted path
		if (!result.isEmpty()) {
			result.forEach(p -> {
				BasicFileAttributes attributes = null;
				try {
					// Read file attributes
					attributes = Files.readAttributes(p, BasicFileAttributes.class);
				} catch (IOException exception) {
					System.out.println(
							"Exception handled when trying to get file " + "attributes: " + exception.getMessage());
				}

				// Convert file create time to LocalDate
				LocalDate createdAt = LocalDateTime
						.ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault()).toLocalDate();

				// Check if the elements already exists, otherwise create it and add it to the
				// map
				List<Path> inMap = this.filesMetadata.get(createdAt);

				if (inMap == null) {
					inMap = new ArrayList<Path>();
					inMap.add(p);
					this.filesMetadata.put(createdAt, inMap);
				} else {
					if (!inMap.contains(p))
						inMap.add(p);
				}
			});

			return;
		}

		throw new IOException();
	}

	/**
	 * Getter filesMetadata
	 * @return
	 */
	public Map<LocalDate, List<Path>> getMetadata() {
		return this.filesMetadata;
	}
}
