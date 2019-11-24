package com.company;

public class Variance {

    public static double mean(double[] series) {
        int n = series.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += series[i];
        }
        return (sum / ((double) n));
    }

    public static double weightedMean(double[][] series) {
        // input pairs, where the first entry in each pair is the value and the second is the weight.
        int n = series.length;
        double sum = 0;
        double sumweights = 0;
        for (int i = 0; i < n; i++) {
            sum += (series[i][0] * series[i][1]);
            sumweights += series[i][1];
        }
        return (sum / sumweights);
    }

    public static double variance(double[] series) {
        int n = series.length;
        double mu = mean(series);
        double sumSquaredDeviation = 0;
        for (int i = 0; i < n; i++) {
            sumSquaredDeviation += Math.pow((series[i] - mu), 2);
        }
        return (sumSquaredDeviation / ((double) n));
    }

    public static double varianceOfWeighted(double[][] series) {
        // Input styled like input for weightedMean, with value, then weight
        int n = series.length;
        double mu = weightedMean(series);
        double sumSquaredDeviation = 0;
        double sumWeights = 0;
        for (int i = 0; i < n; i++) {
            sumSquaredDeviation += (series[i][1]* (Math.pow((series[i][0] - mu), 2)) );
            sumWeights += series[i][1];
        }
        return MathOps.round((sumSquaredDeviation / (sumWeights)), 16);
    }


    static double popVarianceEstimate(double[] series) {
        // estimates the variance of an underlying population based on an input that is a random sample
        int n = series.length;
        if (n < 2) {
            throw new IllegalArgumentException("series is not large enough for population variance estimate");
        }
        double mu = mean(series);
        double sumSquaredDeviation = 0;
        for (int i = 0; i < n; i++) {
            sumSquaredDeviation += Math.pow((series[i] - mu), 2);
        }
        return MathOps.round((sumSquaredDeviation / ((double) (n - 1))),16);
    }

    static double stDev(double[] series) {
        return Math.pow(variance(series), 0.5);
    }

    static double popStDevEstimate(double[] series) {
        return Math.pow(popVarianceEstimate(series), 0.5);
    }

    static double stDevOfPopMeanEstimate(double[] series) {
        return (popStDevEstimate(series) / Math.pow(series.length, 0.5));
    }

    static double[] confidenceIntervalPopMeanTwoSigma(double[] series) {
        double mu = mean(series);
        double standardError = stDevOfPopMeanEstimate(series);
        double[] ret = {mu - 2*standardError, mu + 2*standardError};
        return ret;
    }

    static double covariance(double[] series1, double[] series2) {
        int n1 = series1.length;
        int n2 = series2.length;
        if (n1 != n2) {
            throw new IllegalArgumentException("series must match in length");
        }
        double mu1 = mean(series1);
        double mu2 = mean(series2);
        double sumDeviationProducts = 0;
        for (int i = 0; i <n1; i++) {
            sumDeviationProducts += ( (series1[i]-mu1) * (series2[i] - mu2));
        }
        return (sumDeviationProducts / n1);
    }

    static double covarianceEstimate(double[] series1, double[] series2) {
        int n1 = series1.length;
        int n2 = series2.length;
        if (n1 != n2) {
            throw new IllegalArgumentException("series must match in length");
        }
        double mu1 = mean(series1);
        double mu2 = mean(series2);
        double sumDeviationProducts = 0;
        for (int i = 0; i <n1; i++) {
            sumDeviationProducts += ( (series1[i]-mu1) * (series2[i] - mu2));
        }
        return MathOps.round((sumDeviationProducts / (n1 - 1)), 16);
    }

    static double correlationEstimate(double[] series1, double[] series2) {
        double covar = covarianceEstimate(series1, series2);
        double sigma1 = popStDevEstimate(series1);
        double sigma2 = popStDevEstimate(series2);
        return MathOps.round((covar / (sigma1 * sigma2)),15);
    }

    double[] scaleWeights(double[] weights) {
        // Fixes weights that do not add up to 1
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i];
        }
        if (MathOps.round(sum, 15) != 1.0) {
            double[] ret = new double[weights.length];
            for (int i = 0; i < weights.length; i++) {
                ret[i] = MathOps.round(weights[i] / sum, 16);
            }
            return ret;
        }
        else {
            return weights;
        }
    }
}
