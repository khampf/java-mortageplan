package com.example.mortageplan;

import com.example.mortageplan.util.NotJavaMath;
import com.example.mortageplan.entity.ProspectEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProspectEntityTest {
    ProspectEntity p;

    @BeforeEach
    void setUp() {
        p = new ProspectEntity(100.0, 0.10, 12);
    }

    // Testing getters and setters

    @Test
    void getLoanTotal() {
        assertEquals(100.0, p.getLoanTotal());
    }

    @Test
    void setLoanTotal() {
        p.setLoanTotal(200.0);
        assertEquals(200.0, p.getLoanTotal());
    }

    @Test
    void getYearlyInterest() {
        assertEquals(0.10, p.getYearlyInterest());
    }

    @Test
    void getMonthlyInterest() {
        assertEquals(0.10/12, p.getMonthlyInterest());
    }

    @Test
    void setYearlyInterest() {
        p.setYearlyInterest(0.12);
        assertEquals(0.12, p.getYearlyInterest());
        assertEquals(0.01, p.getMonthlyInterest());
        p.setYearlyInterest(0.075);
        assertEquals(0.075, p.getYearlyInterest());
        assertEquals(0.00625, NotJavaMath.round(p.getMonthlyInterest(), 5));
    }

    @Test
    void setMonthlyInterest() {
        p.setMonthlyInterest(1.0);
        assertEquals(1.0, p.getMonthlyInterest());
        assertEquals(12.0, p.getYearlyInterest());
    }

    @Test
    void getMonthlyPayments() {
        assertEquals(12, p.getPaymentMonths());
    }

    @Test
    void setMonthlyPayments() {
        p.setPaymentMonths(24);
        assertEquals(24, p.getPaymentMonths());
    }

    // Testing formula
    @Test
    void getMonthlyPayment() {
        // Using example from https://www.kasasa.com/blog/how-to-calculate-loan-payments-in-3-easy-steps
        p.setLoanTotal(10000);
        p.setYearlyInterest(0.075);
        p.setPaymentMonths(5*12);
        assertEquals(200.38, NotJavaMath.round(p.getMonthlyPayment(), 2));

        // Using example from https://wiseloan.com/blog/how-to-calculate-monthly-payments-for-loans/
        p = new ProspectEntity(5000, .09, 3*12);
        // assertEquals(158.50, p.getMonthlyPayment()); // THE EXAMPLE URL HAD ROUNDING ERRORS!
        assertEquals(158.9987, NotJavaMath.round(p.getMonthlyPayment(),4));
    }

    @Test
    void testToString() {
        assertEquals("Unkown wants to borrow 100.0 € for a period of 1 years and pay 8.79 € each month", p.toString());
        p.setCustomerName("Donald Duck");
        p.setPaymentMonths(18);
        assertEquals("Donald Duck wants to borrow 100.0 € for a period of 1.5 years and pay 6.01 € each month", p.toString());
    }
}