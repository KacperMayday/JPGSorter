package pl.wit.projekt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Klasa odpowiedzialna za uruchamianie wątków kopiujących pliki zdjęciowe
 * 
 * @author Kacper
 *
 */
public class JPGUtility {
	// logger
	protected static final Logger log = LogManager.getLogger(JPGUtility.class.getName());

	// limit wątków aktywnych w danym momencie czasu
	private final int threadPoolLimit;
	// ścieżka do katalogu docelowego
	private final Path targetPath;

	public JPGUtility(int threadPoolLimit, Path targetPath) {
		this.threadPoolLimit = threadPoolLimit;
		this.targetPath = targetPath;
	}

	/**
	 * Metoda tworząca katalogi na podstawie kluczy mapy oraz kopiująca do nich
	 * pliki wielowątkowo, nazywając je kolejnymi liczbami całkowitymi
	 *
	 * @param mapMetaFilenames mapa data utworzenia -> lista ścieżek do plików
	 */
	public void copyFiles(Map<LocalDate, List<Path>> mapMetaFilenames) throws IOException {
		List<Runnable> tasks = new ArrayList<>();

		log.info("Creating directories in " + targetPath);
		for (LocalDate createdAt : mapMetaFilenames.keySet()) {
			Path directoryTargetPath = Files.createDirectories(targetPath.resolve(Path.of(createdAt.toString())));
			log.trace("Created empty directory " + directoryTargetPath.toString());

			for (Path filePath : mapMetaFilenames.get(createdAt)) {
				tasks.add(new CopyThread(filePath, directoryTargetPath));
			}
		}

		log.debug("Total files to copy: " + tasks.size());
		tasks.forEach(log::trace);
		executeTasks(tasks);
	}

	/**
	 * Metoda odpowiedzialna za wykonanie wielowątkowo podanej listy zadań z limitem
	 * podanej liczbie aktywnych wątków
	 * 
	 * @param tasks lista zadań do wykonania
	 */
	private void executeTasks(List<Runnable> tasks) {
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolLimit);
		for (Runnable task : tasks) {
			executor.execute(task);
		}

		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
