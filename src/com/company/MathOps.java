package com.company;

import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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


    public static String[] doubleArrayPrint(double[][] printArray) {
        int len = printArray.length;
        String[] ret = new String[len];
        for (int i=0; i<len; i++) {
            String thisLine = Arrays.toString(printArray[i]);
            ret[i] = thisLine;
            System.out.println(thisLine);
        }
        return ret;
    }

    public static ArrayList<Pair<Integer, Double>> sortHashMap(HashMap<Integer, Double> toSort) {
        //Sorts pairs by first value in pair
        ArrayList<Pair<Integer, Double>> sorting = new ArrayList<Pair<Integer, Double>>();
        for (Map.Entry<Integer, Double> entry : toSort.entrySet()) {
            Pair<Integer, Double> p = new Pair(entry.getKey(), entry.getValue());
            sorting.add(p);
        }
        Collections.sort(sorting, new SortByIntegerInPair());
        return sorting;
    }

    public static int greatestCommonFactor (int in1, int in2) {
        int smaller = Math.abs(in1);
        if (Math.abs(in2) < Math.abs(in1)) {
            smaller = in2;
        }
        int i = 1;
        int gcf = 1;
        while (i <= smaller) {
            if (in1 % i == 0) {
                if (in2 % i == 0) {
                    gcf = i;
                }
            }
            i++;
        }
        return gcf;
    }

    public static int[] reduceFraction(int numerator, int denominator) {
        //returns numerator, then denominator of reduced fraction
        int gcf = greatestCommonFactor(numerator, denominator);
        int[] ret = {numerator/gcf, denominator/gcf};
        return ret;
    }

    public static Pair<Integer, Integer> reduceFrac(int numerator, int denominator) {
        int[] frac = reduceFraction(numerator, denominator);
        Pair<Integer, Integer> ret = new Pair<Integer, Integer> (frac[0], frac[1]);
        return ret;
    }

    public static Pair<Integer, Integer> fracAdd(Pair<Integer, Integer> frac1, Pair<Integer, Integer> frac2) {
        //Adds two fractions
        int num1 = frac1.getKey();
        int denom1 = frac1.getValue();
        int num2 = frac2.getKey();
        int denom2 = frac2.getValue();
        int sumDenom = denom1*denom2;
        int sumNum = num1*denom2 + num2*denom1;
        return reduceFrac(sumNum, sumDenom);
    }

    public static int findLCM (int[] theseInts) {
        //Returns the lowest number that is an integer multiple of any integer in the input
        int lcm = theseInts[0];
        for (int thisInt : theseInts) {
            if (lcm % thisInt == 0) {
                continue;
            }
            else {
                lcm = lcm*thisInt / greatestCommonFactor(lcm, thisInt);
            }
        }
        return lcm;
    }





}
