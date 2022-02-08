package com.example.mortageplan;

import com.example.mortageplan.util.CSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.FileNotFoundException;

@EnableJpaRepositories
@SpringBootApplication
public class MortagePlanApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(MortagePlanApplication.class);

    /**
     * Used when running as a jar
     *
     * @param args
     */
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

    /**
     * Used when running as a WAR within a web container
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MortagePlanApplication.class);
    }
}

// TODO cleanup
// TODO README file
// TODO JavaDoc?