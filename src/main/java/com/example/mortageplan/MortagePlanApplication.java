package com.example.mortageplan;

import com.example.mortageplan.entity.InvalidInputException;
import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.util.CSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@EnableJpaRepositories
@SpringBootApplication
public class MortagePlanApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(MortagePlanApplication.class);

    /**
     * Used when running as WAR within a web container
     *
     * @param builder SpringBoot application builder
     * @return Web Application
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MortagePlanApplication.class);
    }

    /**
     * Used when running as a commandline JAR
     *
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            String fileName = args[0];
            logger.info("Loading " + fileName + " in console session.");
            consoleOutputFromFile(fileName);
        } else {
            SpringApplication.run(MortagePlanApplication.class, args);
        }
    }

    /**
     * Reads a CSV file by name and outputs prospects in console
     *
     * @param fileName Filename
     */
    public static void consoleOutputFromFile(String fileName) {
        File inputFile = new File(fileName);
        try {
            CSV csv = new CSV(new FileInputStream(inputFile));
            StringBuilder output = new StringBuilder();
            output.append("\n****************************************************************************************************\n\n");
            int i = 0;
            for (List<String> strings : csv.getStrings()) {
                try {
                    ProspectEntity prospectEntity = new ProspectEntity(strings);
                    output.append("Prospect ").append(i).append(": ").append(prospectEntity).append("\n");
                } catch (InvalidInputException e) {
                    if (i > 0) {  // Skip first line header row error
                        logger.warn("Invalid CSV input: " + e.getMessage());
                    }
                }
                i++;
            }
            output.append("\n****************************************************************************************************\n");
            System.out.print(output);
        } catch (FileNotFoundException e) {
            logger.error("File " + fileName + " read error: " + e.getMessage());
        }
    }
}