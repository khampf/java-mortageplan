package com.example.mortageplan.util;

/**
 * Utility class to avoid using math library
 */
public class NotJavaMath {
    /**
     * Exponentiation
     *
     * @param base     Base number
     * @param exponent Exponent
     * @return Result of base^exponent
     */
    public static double pow(double base, double exponent) {
        if (exponent < 0) {
            return 1 / pow(base, -exponent);
        }
        double result = 1;
        while (exponent-- > 0) {
            result *= base;
        }
        return result;
    }

    /**
     * Round a decimal number to the nearest integer
     *
     * @param number Decimal to round up/down
     * @return Rounded number
     */
    public static double round(double number) {
        int integer = (int) number;
        double fraction = number - integer;
        return (fraction > 0.5 ? integer + 1 : integer);
    }

    /**
     * Round a decimal number to the nearest number with specified number of decimals
     *
     * @param number   Decimal number to round up/down
     * @param decimals Number of decimals in answer
     * @return Rounded number
     */
    public static double round(double number, int decimals) {
        return round((number * pow(10, decimals))) / pow(10, decimals);
    }
}
