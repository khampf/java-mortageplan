package com.example.mortageplan.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CSVTest {
    Resource prospectsFile = new ClassPathResource("prospects.txt");
    CSV csv;

    @BeforeEach
    void setUp() {
        String testLine = "A,B,C,D\n1,2,3,4\n";
        InputStream testStream = new ByteArrayInputStream(testLine.getBytes());
        csv = new CSV(testStream);
    }

    @Test
    void fileExists() {
        assertTrue(prospectsFile.exists());
    }

    @Test
    void fileConstructor() throws IOException {
        csv = new CSV(prospectsFile.getFile());
        assertEquals("[Customer, Total loan, Interest, Years]", csv.getStrings().get(0).toString());
    }

    @Test
    void inputStreamConstructor() {
        assertEquals("[A, B, C, D]", csv.getStrings().get(0).toString());
    }

    @Test
    void CSVParse() {
        String testLine = "A,B,C,D\n1,2,3,4\n";
        InputStream testStream = new ByteArrayInputStream(testLine.getBytes());
        CSV csv = new CSV(testStream);
        assertEquals(2, csv.getStrings().size());
        assertEquals(4, csv.getStrings().get(0).size());
        assertEquals(4, csv.getStrings().get(1).size());
        assertEquals("B", csv.getStrings().get(0).get(1));
        assertEquals("4", csv.getStrings().get(1).get(3));
    }

    @Test
    void getStrings() {
        assertEquals("[A, B, C, D]", csv.getStrings().get(0).toString());
    }

    @Test
    void testToString() {
        String testLine = "A,B,C,D\n1,2,3,4\n";
        InputStream testStream = new ByteArrayInputStream(testLine.getBytes());
        CSV csv = new CSV(testStream);
        assertEquals("[[A, B, C, D], [1, 2, 3, 4]]", csv.toString());
    }
}