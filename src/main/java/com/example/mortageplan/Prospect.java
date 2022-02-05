package com.example.mortageplan;

public class Prospect {

    private String customerName;
    private double loanTotal;
    private double yearlyInterest;
    private double paymentMonths;

    public Prospect(String customerName, double loanTotal, double yearlyInterest, double paymentMonths) {
        this.customerName = customerName;
        this.loanTotal = loanTotal;
        this.yearlyInterest = yearlyInterest;
        this.paymentMonths = paymentMonths;
    }

    public Prospect(double loanTotal, double yearlyInterest, double paymentMonths) {
        this("Unkown", loanTotal, yearlyInterest, paymentMonths);
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

    public double getMonthlyInterest() {
        return yearlyInterest / 12;
    }

    public void setYearlyInterest(double yearlyInterest) {
        this.yearlyInterest = yearlyInterest;
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.yearlyInterest = monthlyInterest * 12;
    }

    public double getPaymentMonths() {
        return paymentMonths;
    }
    public void setPaymentMonths(double paymentMonths) {
        this.paymentMonths = paymentMonths;
    }

    /**********************************************************************
     * E = Fixed monthly payment        monthlyPayment
     * b = Interest on a monthly basis  monthlyInterest
     * U = Total loan                   loanTotal
     * p = Number of payments           numberOfMonthlyPayments
     * Formula: E = U[b(1 + b)^p]/[(1 + b)^p - 1]
     *
     *       U[ b * ( 1 + b )^p ]   U * (b * ( 1 + b )^p )
     *  E = --------------------- = ----------------------
     *       [ ( 1 + b )^p - 1 ]    ( ( 1 + b )^p - 1 )
     **********************************************************************/

    public double getMonthlyPayment() {
        double monthlyInterest = getMonthlyInterest();
        return loanTotal * (monthlyInterest * NotJavaMath.pow(1 + monthlyInterest, paymentMonths))
                / (NotJavaMath.pow(1 + monthlyInterest, paymentMonths) - 1);
    }

    @Override
    public String toString() {
        double paymentYears = paymentMonths / 12;
        String paymentYearsString =
                (paymentMonths % 12 == 0 ? Integer.toString((int) paymentYears) : Double.toString(paymentYears));
        return customerName + " wants to borrow " + loanTotal + " € for a period of " + paymentYearsString
                + " years and pay " + NotJavaMath.round(getMonthlyPayment(),2) +  " € each month";
    }
}
