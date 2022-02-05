package com.example.mortageplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MortagePlanApplication {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			String fileName = args[0];
			File inputFile = new File(fileName);
			if (!inputFile.exists()) {
				System.err.println("File " + fileName + " does not exist!");
			} else {
				System.out.println("Loading " + fileName + " in console session.");
				CSV csv = new CSV(inputFile);
				// System.out.println(csv);

				List<Prospect> prospects = new ArrayList<>();
				for (List<String> strings : csv.getStrings()) {
					try {
						Prospect p = new Prospect(strings);
						prospects.add(p);
					} catch (InvalidInputException e) {
						e.printStackTrace();
					}
				}

				int i=0;
				for (Prospect p : prospects) {
					System.out.println("****************************************************************************************************\n");
					System.out.println("Prospect " + ++i + ": " + p);
					System.out.println("\n****************************************************************************************************");
				}
			}
		} else {
			SpringApplication.run(MortagePlanApplication.class, args);
		}
	}

}
