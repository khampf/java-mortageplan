package com.example.mortageplan.entity;

import com.example.mortageplan.util.NotJavaMath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProspectEntityTest {
    ProspectEntity p;

    @BeforeEach
    void setUp() {
        p = new ProspectEntity(12345.0, 1.5, 5);
    }

    @Test
    void getCustomerName() {
        assertEquals("Unknown", p.getCustomerName());
    }

    @Test
    void setCustomerName() {
        p.setCustomerName("Mickey Mouse");
        assertEquals("Mickey Mouse", p.getCustomerName());
    }

    @Test
    void getLoanTotal() {
        assertEquals(12345.0, p.getLoanTotal());
    }

    @Test
    void setLoanTotal() {
        p.setLoanTotal(200.0);
        assertEquals(200.0, p.getLoanTotal());
    }

    @Test
    void getYearlyInterest() {
        assertEquals(1.5, p.getYearlyInterest());
    }

    @Test
    void setYearlyInterest() {
        p.setYearlyInterest(1.25);
        assertEquals(1.25, p.getYearlyInterest());
        p.setYearlyInterest(12);
        assertEquals(12, p.getYearlyInterest());
        assertEquals(1, p.getMonthlyInterest());
        p.setYearlyInterest(7.5);
        assertEquals(7.5, p.getYearlyInterest());
        assertEquals(0.625, NotJavaMath.round(p.getMonthlyInterest(), 5));
    }

    @Test
    void getTermYears() {
        assertEquals(5, p.getTermYears());
    }

    @Test
    void setTermYears() {
        p.setTermYears(3);
        assertEquals(3, p.getTermYears());
    }

    @Test
    void stringListConstructor() throws InvalidInputException {
        List<String> strings = new ArrayList<>();
        assertThrows(InvalidInputException.class, () -> p = new ProspectEntity(strings));
        strings.add("String User");
        strings.add("1000.45");
        strings.add("00004.0");
        strings.add("Bogus");
        assertThrows(InvalidInputException.class, () -> p = new ProspectEntity(strings));
        strings.remove(strings.size() - 1);
        strings.add("2.0");
        p = new ProspectEntity(strings);
        assertEquals("String User", p.getCustomerName());
        assertEquals(1000.45, p.getLoanTotal());
        assertEquals(4, p.getYearlyInterest());
        assertEquals(2.0, p.getTermYears());
    }

    @Test
    void getYearlyDecimalInterest() {
        assertEquals(0.015, p.getYearlyDecimalInterest());
    }

    @Test
    void getMonthlyInterest() {
        assertEquals(1.5 / 12, p.getMonthlyInterest());
    }

    @Test
    void getMonthlyDecimalInterest() {
        assertEquals(1.5 / 12 / 100.0, p.getMonthlyDecimalInterest());
    }

    @Test
    void setMonthlyInterest() {
        p.setMonthlyInterest(1.0);
        assertEquals(1.0, p.getMonthlyInterest());
        assertEquals(12.0, p.getYearlyInterest());
    }

    @Test
    void getTermMonths() {
        assertEquals(5 * 12, p.getTermMonths());
    }

    @Test
    void setTermMonths() {
        p.setTermMonths(18);
        assertEquals(18, p.getTermMonths());
        assertEquals(1.5, p.getTermYears());
    }

    @Test
    void getMonthlyPayment() {
        // Testing formula
        // Using example from https://www.kasasa.com/blog/how-to-calculate-loan-payments-in-3-easy-steps
        p.setLoanTotal(10000);
        p.setYearlyInterest(7.5);
        p.setTermYears(5);
        assertEquals(200.38, NotJavaMath.round(p.getMonthlyPayment(), 2));

        // Using example from https://wiseloan.com/blog/how-to-calculate-monthly-payments-for-loans/
        p = new ProspectEntity(5000, 9, 3);
        // assertEquals(158.50, p.getMonthlyPayment()); // THE EXAMPLE URL HAD ROUNDING ERRORS!
        assertEquals(158.9987, NotJavaMath.round(p.getMonthlyPayment(), 4));
    }

    @Test
    void testToString() {
        assertEquals("Unknown wants to borrow 12345.0 € for a period of 5 years and pay 213.69 € each month", p.toString());
        p.setCustomerName("Donald Duck");
        p.setTermMonths(18);
        assertEquals("Donald Duck wants to borrow 12345.0 € for a period of 1.5 years and pay 694.01 € each month", p.toString());
    }
}