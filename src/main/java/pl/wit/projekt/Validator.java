package pl.wit.projekt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 * Validator jest używany do spawdzenie poprawności danych wejściowych
 * 
 * @author Jakub Chrupek, 19245
 *
 */
public class Validator {

	/**
	 * validate przyjmuje argumenty z stdin
	 * 
	 * Warunki przejscia: 1. Powiny byc 3 argumenty a. Żródło pierwsze b. Cel drugi
	 * c. Ilość wątkow trzeci
	 *
	 * 2. Źródło oraz cel powiny istnieć
	 * 
	 * 3. Żródło oraz cel powiny być ścieżkami
	 * 
	 * 4. Ilośc watków powinna być liczbą
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void validate(String[] args) {
		Validator.validateLength(args.length);
		Validator.validateSourcePath(args[0]);
		Validator.validateTargetPath(args[1]);
		Validator.validateThreadNum(args[2]);
	}

	/**
	 * Sprawdzenie ilości argumentów
	 * 
	 * @param size
	 * @throws RuntimeException
	 */
	public static void validateLength(int size) throws RuntimeException {
		if (size != 3) {
			throw new IllegalArgumentException("Please provide three arguments");
		}
	}

	/**
	 * Sprawdzenie iż zródło istnieje i nie jest puste
	 * 
	 * @param path
	 * @throws RuntimeException
	 */
	public static void validateSourcePath(String path) throws RuntimeException {
		if (!Files.isDirectory(Paths.get(path))) {
			throw new IllegalArgumentException("Source path should exit and should be a directory");
		}

		try {
			// Sprawdzenie czy ścieżka NIE ZAWIERA jakiś plików
			if (!Files.list(Paths.get(path)).findAny().isPresent()) {
				throw new IllegalArgumentException("Source should not be empty");
			}
		} catch (IOException e) {
			System.out.println("error occured: " + e);
			System.exit(1);
		}
	}

	/**
	 * Sprawdze ze cel istnieje i jest pusty
	 * 
	 * @param path
	 * @throws RuntimeException
	 */
	public static void validateTargetPath(String path) throws RuntimeException {
		if (!Files.isDirectory(Paths.get(path))) {
			throw new IllegalArgumentException("Target path should exist");
		}

		try {
			// Sprawdzenie czy ścieżka ZAWIERA jakiś plików
			if (Files.list(Paths.get(path)).findAny().isPresent()) {
				throw new IllegalArgumentException("Target should be a empty directory");
			}
		} catch (IOException e) {
			System.out.println("error occured: " + e);
			System.exit(1);
		}
	}

	/**
	 * Sprawdzenie czy przekazana ilośc wątków do użycia jest liczbą
	 * 
	 * @param num
	 */
	public static void validateThreadNum(String num) {
		if (num == null) {
			throw new NullPointerException();
		}

		try {
			int d = Integer.parseInt(num);
		} catch (NumberFormatException n) {
			throw new IllegalArgumentException("Thread num should be an integer");
		}
	}
}
