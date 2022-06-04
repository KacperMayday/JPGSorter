package pl.wit.projekt;

import java.util.ArrayList;
import java.util.Date;
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

public class JPGScanner {
	
	// Data utworzenia -> Lista nazw plików
	private Map<FileTime, ArrayList<Path>> filesMetadata;

	public JPGScanner(Path path) throws IOException {
		// inicjalizacja mapy
		filesMetadata = null;

		List<Path> sourceFiles = scanDirectory(path);
		getMetadata(sourceFiles);
	}

	/**
	 * Metoda skanująca katalog w poszukiwaniu plików JPG
	 * 
	 * @param sourcePath
	 */
	private List<Path> scanDirectory(Path path) throws IOException {
			
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(Files::isRegularFile) // is a file
                    .filter(p -> p.getFileName().toString().endsWith(".jpg"))
                    .collect(Collectors.toList());
        }
        return result;
	}

	/**
	 * Metoda uzupełniająca mapę na podstawie nazw plików i ich metadanych
	 * 
	 * @param listFilenames
	 */
	private void getMetadata(List<Path> paths) {
		paths.forEach(path -> {
			BasicFileAttributes attributes = null;
	        try
	        {
	            attributes = Files.readAttributes(path, BasicFileAttributes.class); // zczytanie atrybutow pliku 
	        }
	        catch (IOException exception)
	        {
	            System.out.println("Exception handled when trying to get file " +
	                    "attributes: " + exception.getMessage());
	        }
	        
	        FileTime createdAt = attributes.creationTime();
	        
	        ArrayList<Path> inMap = this.filesMetadata.get(createdAt);
	        
	        // Sprawdz czy lista juz istnieje, dodaj element, inaczej stworz i dodaj do mapy
	        if(inMap == null) { 
	        	inMap = new ArrayList<Path>();
	        	inMap.add(path);
	            this.filesMetadata.put(createdAt, inMap);
	       } else {
	           if(!inMap.contains(path)) inMap.add(path);
	       }
		});
	
	}
}
