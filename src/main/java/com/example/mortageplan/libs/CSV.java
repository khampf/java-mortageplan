package com.example.mortageplan.libs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSV {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static char defaultDelimiter = ',';
    private char delimiter = defaultDelimiter;
    List<List<String>> strings = new ArrayList<>();

    public CSV(File inputFile, char delimiter) throws FileNotFoundException {
        this.delimiter = delimiter;
        Scanner scanner = new Scanner(inputFile, "UTF-8");
        CSVParse(scanner);
    }

    public CSV(File inputFile) throws FileNotFoundException {
        this(inputFile, defaultDelimiter);
    }

    public CSV(String inputString, char delimiter) throws UnsupportedEncodingException {
        this.delimiter = delimiter;
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        CSVParse(scanner);
    }

    public CSV(String inputString) throws UnsupportedEncodingException {
        this(inputString, defaultDelimiter);
    }

    public CSV(InputStream inputStream, char delimiter) {
        this.delimiter = delimiter;
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        CSVParse(scanner);
    }

    public CSV(InputStream inputStream) {
        this(inputStream, defaultDelimiter);
    }

    public void CSVParse(Scanner scanner) {
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(String.valueOf(delimiter));
            List<String> lineStrings = new ArrayList<>();

            while (lineScanner.hasNext()) {
                String s = lineScanner.next().trim();
                if (s.startsWith("\"")) {
                    while (lineScanner.hasNext() && !s.endsWith("\"")) {
                        s = s + " " + lineScanner.next();
                    }
                    if (!s.endsWith("\"")) {
                        logger.warn("Malformed CSV (unterminated quotes)");
                    } else {
                        s = s.substring(1, s.length() - 1).trim();
                    }
                }
                if (!s.isBlank()) {
                    lineStrings.add(s);
                }
            }
            if (lineStrings.isEmpty()) {
                logger.warn("Malformed CSV (empty line)");
            } else {
                strings.add(lineStrings);
            }
        }
    }

    public List<List<String>> getStrings() {
        return strings;
    }

    @Override
    public String toString() {
        return strings.toString();
    }
}
