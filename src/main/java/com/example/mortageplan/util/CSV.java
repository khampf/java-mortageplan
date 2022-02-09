package com.example.mortageplan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for parsing CSV input to list of string
 */
public class CSV {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Default delimiter if not explicitly set
     */
    private static final char defaultColumnDelimiter = ',';
    private final char columnDelimiter;

    /**
     * Strings are stored as list of lists
     */
    @lombok.Getter
    List<List<String>> strings = new ArrayList<>();

    /**
     * Constructor which parses CSV strings from files
     *
     * @param inputFile       Text file in CSV-format
     * @param columnDelimiter Column delimiter
     */
    public CSV(File inputFile, char columnDelimiter) {
        this.columnDelimiter = columnDelimiter;
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile, StandardCharsets.UTF_8);
            CSVParse(scanner);
        } catch (IOException e) {
            logger.error("Unable to parse input: " + e.getMessage());
        }
    }

    /**
     * Constructor which parses CSV strings from files using default delimiter
     *
     * @param inputFile Text file in CSV-format
     */
    public CSV(File inputFile) {
        this(inputFile, defaultColumnDelimiter);
    }

    /**
     * Constructor parsing CSV data from inputStream
     *
     * @param inputStream     Stream of CSV input text
     * @param columnDelimiter Column delimiter
     */
    public CSV(InputStream inputStream, char columnDelimiter) {
        this.columnDelimiter = columnDelimiter;
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        CSVParse(scanner);
    }

    /**
     * Constructor parsing CSV data from inputStream
     *
     * @param inputStream Stream of CSV input text
     */
    public CSV(InputStream inputStream) {
        this(inputStream, defaultColumnDelimiter);
    }

    /**
     * Parses CSV to strings from input data line scanner
     *
     * @param scanner Line scanner
     */
    public void CSVParse(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(String.valueOf(columnDelimiter));
            List<String> lineStrings = new ArrayList<>();

            while (lineScanner.hasNext()) {
                StringBuilder s = new StringBuilder(lineScanner.next().trim());
                if (s.toString().startsWith("\"")) {
                    while (lineScanner.hasNext() && !s.toString().endsWith("\"")) {
                        s.append(" ").append(lineScanner.next());
                    }
                    if (!s.toString().endsWith("\"")) {
                        logger.warn("Malformed CSV (unterminated quotes)");
                    } else {
                        s = new StringBuilder(s.substring(1, s.length() - 1).trim());
                    }
                }
                if (!s.toString().isBlank()) {
                    lineStrings.add(s.toString());
                }
            }
            if (lineStrings.isEmpty()) {
                logger.warn("Malformed CSV (empty line)");
            } else {
                strings.add(lineStrings);
            }
        }
    }

    /**
     * @return String representation of lists
     */
    @Override
    public String toString() {
        return strings.toString();
    }
}
