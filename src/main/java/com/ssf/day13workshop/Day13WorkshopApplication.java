package com.ssf.day13workshop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day13WorkshopApplication {

	private static String addressBookDirPath;

	public static void main(String[] args) {
		if (args.length == 2 && args[0].equals("--dataDir")) {
			addressBookDirPath = args[1];

			Path addressBookDir = Paths.get(addressBookDirPath);
			if (!Files.exists(addressBookDir)) {
				try {
					Files.createDirectories(addressBookDir);
					System.out.println("File directory created: " + addressBookDir);
				} catch (IOException e) {
					System.err.println("Error in creating directory");
				}
			}

		} else if (args.length == 0 || !args[0].equals("--dataDir")) {
			System.out.println("Please input the --dataDir option to continue");
			return;
		}

		SpringApplication.run(Day13WorkshopApplication.class, args);

	}

	public static String getAddressBookDirPath() {
		return addressBookDirPath;
	}
}