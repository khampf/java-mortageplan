package com.example.mortageplan.util;

import java.util.InputMismatchException;

public class NotJavaMath {
    public static double pow(double number, double power) {
        if (power < 0) {
            throw new InputMismatchException("Can not handle inversions");
        } else if (power == 0) {
            return 1;
        } else if (power == 1) {
            return number;
        } else {
            return number * pow(number, power-1);
        }
    }

    public static double round(double number) {
        int integer = (int) number;
        double fraction = number - integer;
        return (fraction > 0.5 ? integer+1 : integer);
    }

    public static double round(double number, int decimals) {
        return round((number * pow(10,decimals))) / pow(10,decimals);
    }
}
