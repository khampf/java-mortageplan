package com.example.mortageplan;

public class NotJavaMath {
    static double pow(double number, double power) {
        if (power == 1) {
            return number;
        } else {
            return number * pow(number, power-1);
        }
    }

    static double round(double number) {
        int integer = (int) number;
        double fraction = number - integer;
        return (fraction > 0.5 ? integer+1 : integer);
    }

    static double round(double number, int decimals) {
        return round((number * pow(10,decimals))) / pow(10,decimals);
    }
}
