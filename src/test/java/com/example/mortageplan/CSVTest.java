package com.example.mortageplan;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class CSVTest {
    Resource prospectsFile = new ClassPathResource("static/prospects.txt");

    @Test
    void testFileExists() {
        assertTrue(prospectsFile.exists());
    }

    @Test
    void testFileConstructor() throws IOException {
        CSV csv = new CSV(prospectsFile.getFile());
        assertEquals("[Customer, Total loan, Interest, Years]", csv.getStrings().get(0).toString());
    }

    @Test
    void testStringConstructor() throws IOException {
        String inputString = Files.readString(prospectsFile.getFile().toPath());
        CSV csv = new CSV(inputString);
        assertEquals("[Customer, Total loan, Interest, Years]", csv.getStrings().get(0).toString());
    }
}