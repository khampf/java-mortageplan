package com.example.mortageplan;

import com.example.mortageplan.libs.CSV;
import com.example.mortageplan.prospects.Prospects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.FileNotFoundException;

@SpringBootApplication
public class MortagePlanApplication {
	private static final Logger logger = LoggerFactory.getLogger(MortagePlanApplication.class);

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			String fileName = args[0];
			File inputFile = new File(fileName);
			if (!inputFile.exists()) {
				logger.error("File " + fileName + " does not exist!");
			} else {
				logger.info("Loading " + fileName + " in console session.");
				CSV csv = new CSV(inputFile);
				Prospects prospects = new Prospects(csv);
				System.out.println(prospects);
			}
		} else {
			SpringApplication.run(MortagePlanApplication.class, args);
		}
	}
}
