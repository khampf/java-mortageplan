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
				Prospects prospects = new Prospects(csv);
				System.out.println(prospects);
			}
		} else {
			SpringApplication.run(MortagePlanApplication.class, args);
		}
	}

}
