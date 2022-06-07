package pl.wit.projekt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JPGScanner {

	/**
	 * 
	 */
	private Map<LocalDate, List<Path>> filesMetadata;

	/**
	 * 
	 * @param path
	 * @throws IOException
	 */
	public JPGScanner() {
		filesMetadata =  new HashMap<>();
	}

	/**
	 * Scans the passed in directory for .jpg files and saves thier creation time to
	 * a instance Map.
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void scanDirectory(Path path) throws IOException {
		List<Path> result;

		try (Stream<Path> walk = Files.walk(path)) {
			result = walk.filter(Files::isRegularFile) // is a file
					.filter(p -> p.getFileName().toString().endsWith(".jpg")).collect(Collectors.toList());
		}

		// Loop through each path

		if (!result.isEmpty()) {
			result.forEach(p -> {
				BasicFileAttributes attributes = null;
				try {
					// Created basic file attributes
					attributes = Files.readAttributes(p, BasicFileAttributes.class);
				} catch (IOException exception) {
					System.out.println(
							"Exception handled when trying to get file " + "attributes: " + exception.getMessage());
				}

				LocalDate createdAt = LocalDateTime
						.ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault()).toLocalDate();
				

				List<Path> inMap = this.filesMetadata.get(createdAt);

				// Check if the elements already exists, otherwise create it and add it to the
				// map
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
	 * Returns the map containing FileTime associated with file paths
	 * 
	 * @return filesMeta instance variable
	 */
	public Map<LocalDate, List<Path>> getMetadata() {
		return this.filesMetadata;
	}
}
