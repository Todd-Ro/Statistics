package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathOps {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double[] findSumDifferences(double[][] series) {
        /* Takes in pairs of values, then returns a series that is one shorter showing the proportion by which the sum
        of each pair exceeds the first entry in the previous pair.
         */
        int n = series.length;
        double[] ret = new double[n-1];
        for (int i = 1; i<n; i++) {
            double previous = (series[i-1][0]);
            double latest = (series[i][0] + series[i][1]);
            ret[i-1] = MathOps.round(((latest / previous) - 1),9);
        }
        return ret;
    }

    public static double[] quadraticSol(double a, double b, double c) {
        //Returns the two solutions based on the quadratic equation
        double[] ret = new double[2];
        ret[0] = (-1*b + Math.pow(Math.pow(b, 2) - 4*a*c, 0.5)  ) / (2*a);
        ret[1] = (-1*b - Math.pow(Math.pow(b, 2) - 4*a*c, 0.5)  ) / (2*a);
        return ret;
    }

    public static double findLowestFirstElementInArrays(double[][] checkArray) {
        int len = checkArray.length;
        double lowest = checkArray[0][0];
        if (len == 1) {
            return lowest;
        }
        else {
            for (int i = 1; i<len; i++) {
                if (checkArray[i][0] < lowest)
                    lowest = checkArray[i][0];
            }
        }
        return lowest;
    }

}
