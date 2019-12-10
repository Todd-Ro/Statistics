package com.company;

public class ErrorFunction {
    //Abramowitz and Stegun approximation
    static double a1 =  0.254829592;
    static double a2 = -0.284496736;
    static double a3 = 1.421413741;
    static double a4 = -1.453152027;
    static double a5 = 1.061405429;
    static double p = 0.3275911;
    public static Double erf(double x) {
        if (x < 0) {
            return (-1 * erf(-x));
        }
        double t = (1 / (1 + p*x));
        Double ans = (1 -
                ((a1*t + a2*Math.pow(t, 2) + a3*Math.pow(t, 3) + a4*Math.pow(t, 4) + a5*Math.pow(t, 5))
                        *Math.exp(-1*Math.pow(x, 2))) );
        return MathOps.round(ans, 6);
    }
}
