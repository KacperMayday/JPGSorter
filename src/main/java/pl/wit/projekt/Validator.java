package pl.wit.projekt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Validator {
	
	/**
	 * validate accepts arguments from the command line
	 * 
	 * Passing conditions: 1. There should be 2 arguments passed a. the source path
	 * as the first argument b. the target path as the second argument
	 * 
	 * 2. Check if the source path exits
	 * 
	 * @param args
	 * @throws IOException 
	 */
	
	public static void validate(String[] args) {
		Validator.validateLength(args.length);
		Validator.validateSourcePath(args[0]);
		Validator.validateTarget(args[1]);
	}
	
	public static void validateLength(int size) throws RuntimeException {
		if (size != 3) {
			throw new IllegalArgumentException("Please provide three arguments");
		}
	}
	
	public static void validateSourcePath(String path) throws RuntimeException {
		if (!Files.isDirectory(Paths.get(path))) {
			throw new IllegalArgumentException("Source path should exit and should be a directory");
		}
	}
	
	public static void validateTarget(String path) throws RuntimeException {
		if ( !Files.isDirectory(Paths.get(path))) {
			throw new IllegalArgumentException("Target path should exit");
		}
		
		try {
			if (!Files.list(Paths.get(path)).findAny().isPresent()) {
				throw new IllegalArgumentException("Target should be a empty directory");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
