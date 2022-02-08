package com.example.mortageplan.entity;

import com.example.mortageplan.util.NotJavaMath;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table
public class ProspectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    @NotBlank // (message = "Can not be blank")
    private String customerName;

    @Column
    @Min(value = 1) //, message = "Required")
    private double loanTotal;

    @Column
    @Min(value = 0)
    private double yearlyInterest;

    @Column
    @Positive
    private double termYears;

    public ProspectEntity() {

    }

    public ProspectEntity(String customerName, double loanTotal, double yearlyInterest, double termYears) {
        this.customerName = customerName;
        this.loanTotal = loanTotal;
        this.yearlyInterest = yearlyInterest;
        this.termYears = termYears;
    }

    public ProspectEntity(double loanTotal, double yearlyInterest, double termYears) {
        this("Unkown", loanTotal, yearlyInterest, termYears);
    }

    public ProspectEntity(List<String> strings) throws InvalidInputException {
        // [Customer, Total loan, Interest, Years]
        if (strings.size() != 4) {
            throw new InvalidInputException("Incorrect number of columns (must be 4) in " + strings);
        }
        this.customerName = strings.get(0);

        try {
            this.loanTotal = Double.parseDouble(strings.get(1));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Incorrect number format in column 1: " + strings.get(1));
        }

        try {
            this.yearlyInterest = Double.parseDouble(strings.get(2));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Incorrect number format in column 2: " + strings.get(2));
        }

        try {
            this.termYears = Double.parseDouble(strings.get(3));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Incorrect number format in column 3: " + strings.get(3));
        }
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getLoanTotal() {
        return loanTotal;
    }

    public void setLoanTotal(double loanTotal) {
        this.loanTotal = loanTotal;
    }

    public double getYearlyInterest() {
        return yearlyInterest;
    }

    public double getYearlyDecimalInterest() {
        return yearlyInterest / 100.0;
    }

    public double getMonthlyInterest() {
        return yearlyInterest / 12;
    }

    public void setYearlyInterest(double yearlyInterest) {
        this.yearlyInterest = yearlyInterest;
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.yearlyInterest = monthlyInterest * 12;
    }

    public double getTermYears() {
        return termYears;
    }

    public double getTermMonths() {
        return termYears * 12;
    }

    public void setTermYears(double paymentYears) {
        this.termYears = paymentYears;
    }
    public void setTermMonths(double paymentMonths) {
        this.termYears = paymentMonths / 12;
    }

    /**********************************************************************
     * E = Fixed monthly payment        monthlyPayment
     * b = Interest on a monthly basis  monthlyInterest
     * U = Total loan                   loanTotal
     * p = Number of payments           termMonths
     * Formula: E = U[b(1 + b)^p]/[(1 + b)^p - 1]
     *
     *       U[ b * ( 1 + b )^p ]   U * (b * ( 1 + b )^p )
     *  E = --------------------- = ----------------------
     *       [ ( 1 + b )^p - 1 ]    ( ( 1 + b )^p - 1 )
     **********************************************************************/

    public double getMonthlyPayment() {
        double termMonths = getTermMonths();
        double decimalMonthlyInterest = getMonthlyInterest() / 100.0;
        return loanTotal * (decimalMonthlyInterest * NotJavaMath.pow(1 + decimalMonthlyInterest, termMonths))
                / (NotJavaMath.pow(1 + decimalMonthlyInterest, termMonths) - 1);
    }

    @Override
    public String toString() {
        double termMonths = getTermMonths();
        String paymentYearsString =
                (termMonths % 12 == 0 ? Integer.toString((int) getTermYears()) : Double.toString(getTermYears()));
        return customerName + " wants to borrow " + loanTotal + " € for a period of " + paymentYearsString
                + " years and pay " + NotJavaMath.round(getMonthlyPayment(),2) +  " € each month";
    }
}
