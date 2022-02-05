package com.example.mortageplan;

import org.junit.jupiter.api.Test;

import static com.example.mortageplan.NotJavaMath.*;
import static org.junit.jupiter.api.Assertions.*;

class NotJavaMathTest {

    @Test
    void testPow() {
        assertEquals(1, pow(1, 1));
        assertEquals(4, pow(2, 2));
        assertEquals(10, pow(10, 1));
        assertEquals(100, pow(10, 2));
        assertEquals(1000, pow(10, 3));
        assertEquals(10000, pow(10, 4));
        assertEquals(100000, pow(10, 5));
        assertEquals(1000000, pow(10, 6));
    }

    @Test
    void testRound() {
        assertEquals(1.0, NotJavaMath.round(1.0));
        assertEquals(1.0, NotJavaMath.round(1.4));
        assertEquals(2.0, NotJavaMath.round(1.9));
        assertEquals(2.0, NotJavaMath.round(1.99));
    }

    @Test
    void testRoundDecimal() {
        assertEquals(1.50, NotJavaMath.round(1.500, 2));
        assertEquals(1.51, NotJavaMath.round(1.507, 2));
        assertEquals(1.99, NotJavaMath.round(1.994, 2));
        assertEquals(2.00, NotJavaMath.round(1.999, 2));
    }
}