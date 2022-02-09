package com.example.mortageplan.entity;

import com.example.mortageplan.util.NotJavaMath;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Prospect JPA entity
 */
@Entity
@Table
public class ProspectEntity {
    /**
     * JPA entity id
     */
    @lombok.Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column
    private int id;

    /**
     * Customer name
     */
    @lombok.Setter
    @lombok.Getter
    @Column
    @NotBlank // (message = "Can not be blank")
    private String customerName;

    /**
     * Sum total of prospect
     */
    @lombok.Setter
    @lombok.Getter
    @Column
    @Min(value = 1) //, message = "Required")
    private double loanTotal;

    /**
     * Prospect interest in annual percent
     */
    @lombok.Setter
    @lombok.Getter
    @Column
    @Min(value = 0)
    private double yearlyInterest;

    /**
     * Prospect duration in decimal years
     */
    @lombok.Setter
    @lombok.Getter
    @Column
    @Positive
    private double termYears;

    /**
     * Public no-arg constructor for JPA
     */
    public ProspectEntity() {
    }

    /**
     * Constructor using arguments
     *
     * @param customerName   Customer name
     * @param loanTotal      Prospect total sum
     * @param yearlyInterest Annual interest in percents
     * @param termYears      Prospect terms in years
     */
    public ProspectEntity(String customerName, double loanTotal, double yearlyInterest, double termYears) {
        this.customerName = customerName;
        this.loanTotal = loanTotal;
        this.yearlyInterest = yearlyInterest;
        this.termYears = termYears;
    }

    /**
     * Constructor without a name overload
     *
     * @param loanTotal      Prospect total sum
     * @param yearlyInterest Annual interest in percents
     * @param termYears      Prospect terms in years
     */
    public ProspectEntity(double loanTotal, double yearlyInterest, double termYears) {
        this("Unknown", loanTotal, yearlyInterest, termYears);
    }

    /**
     * Constructor parsing values as list of 4 strings used after having parsed CSV input
     *
     * @param strings Input as list of strings
     * @throws InvalidInputException on bad input
     */
    public ProspectEntity(List<String> strings) throws InvalidInputException {
        // [Customer, Total loan, Interest, Years]
        if (strings.size() != 4) {
            throw new InvalidInputException("Incorrect number of columns (must be 4) in " + strings);
        }
        this.customerName = strings.get(0);

        for (int i = 1; i < 4; i++) {
            try {
                switch (i) {
                    case 1:
                        this.loanTotal = Double.parseDouble(strings.get(i));
                        break;
                    case 2:
                        this.yearlyInterest = Double.parseDouble(strings.get(i));
                        break;
                    case 3:
                        this.termYears = Double.parseDouble(strings.get(i));
                }
            } catch (NumberFormatException e) {
                throw new InvalidInputException("Incorrect number format in column "
                        + (i + 1) + ": " + strings.get(i));
            }
        }
    }

    /**
     * Convenience function to get annual interest as a decimal number
     *
     * @return Annual interest as decimal number
     */
    public double getYearlyDecimalInterest() {
        return yearlyInterest / 100.0;
    }

    /**
     * Convenience function to get interest calculated per monthly percents
     *
     * @return Monthly interest as percents
     */
    public double getMonthlyInterest() {
        return yearlyInterest / 12;
    }

    /**
     * Convenience function to get decimal interest calculated per month
     *
     * @return Monthly interest as percents
     */
    public double getMonthlyDecimalInterest() {
        return getMonthlyInterest() / 100.0;
    }

    /**
     * Convenience function to set annual interest providing monthly interest
     *
     * @param monthlyInterest Monthly interest in percents
     */
    public void setMonthlyInterest(double monthlyInterest) {
        this.yearlyInterest = monthlyInterest * 12;
    }

    /**
     * Convenience function to get prospect terms in number of months
     *
     * @return Prospect terms in months
     */
    public double getTermMonths() {
        return termYears * 12;
    }

    /**
     * Convenience function to set prospect term in number of months
     *
     * @param paymentMonths Prospect term in months
     */
    public void setTermMonths(double paymentMonths) {
        this.termYears = paymentMonths / 12;
    }

    /**
     * Calculates monthly payment
     *
     * @return Payment per month
     */
    public double getMonthlyPayment() {
        double termMonths = getTermMonths();
        /*
         * E = Fixed monthly payment        return value
         * b = Interest on a monthly basis  getMonthlyDecimalInterest()
         * U = Total loan                   loanTotal
         * p = Number of payments           getTermMonths()
         *
         * Formula: E = U[b(1 + b)^p]/[(1 + b)^p - 1]
         *
         *      U[ b * ( 1 + b )^p ]   U * (b * ( 1 + b )^p )
         * E = --------------------- = ----------------------
         *      [ ( 1 + b )^p - 1 ]    ( ( 1 + b )^p - 1 )
         */

        double numerator = loanTotal * (getMonthlyDecimalInterest()
                * NotJavaMath.pow(1 + getMonthlyDecimalInterest(), termMonths));
        double denominator = (NotJavaMath.pow(1 + getMonthlyDecimalInterest(), termMonths) - 1);
        return numerator / denominator;
    }

    /**
     * @return String representation of entity
     */
    @Override
    public String toString() {
        double termMonths = getTermMonths();
        String paymentYearsString =
                (termMonths % 12 == 0 ? Integer.toString((int) getTermYears()) : Double.toString(getTermYears()));
        return customerName + " wants to borrow " + loanTotal + " € for a period of " + paymentYearsString
                + " years and pay " + NotJavaMath.round(getMonthlyPayment(), 2) + " € each month";
    }
}
