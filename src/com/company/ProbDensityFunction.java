package com.company;

public class ProbDensityFunction /*extends Variance*/ {

    double[][] distribution;
    double[] values;

    public double[] getValues() {
        return values;
    }

    public ProbDensityFunction(double[][] densityDistribution) {
        /* Use to create a discrete probability density function with a finite number of possible values.
        Enter a series of pairs, with the value first and then the density.
         */
        this.distribution = densityDistribution;
        double[] vals = new double[densityDistribution.length];
        for (int i=0; i< densityDistribution.length; i++) {
            vals[i] = densityDistribution[i][0];
        }
        this.values = vals;
    }

    public ProbDensityFunction(double[] valueList) {
        this.values = valueList;
    }

    public double findVariance() {
        if (distribution.length > 0) {
            return Variance.varianceOfWeighted(distribution);
        }
        else {
            return Variance.variance(values);
        }
    }

    public double findMean() {
        if (distribution.length > 0) {
            return Variance.weightedMean(distribution);
        }
        else {
            return Variance.mean(values);
        }
    }

    public double findUnderlyingVariance() {
        /* Assumes that the distribution is equally weighted and is actually a sample from an underlying distrubtion,
        and that values[]  is initialized.
         */
        return Variance.popVarianceEstimate(values);
    }

    public double findVarianceOfTwoDistSum(ProbDensityFunction otherDist, double weightThis, double weightThat) {
        /* Assumes that two sets of values of equal length are paired observations of samples from two distributions
        that may move together
         */
        double var1 = this.findUnderlyingVariance();
        double var2 = otherDist.findUnderlyingVariance();
        double cov = Variance.covarianceEstimate(this.values, otherDist.getValues());
        return ((Math.pow(weightThis, 2)*var1) + (Math.pow(weightThat, 2) * var2) + (2*weightThis*weightThat*cov));
    }

    public static double findVarianceOfMultiDistSum(ProbDensityFunction[] dists, double[] weights) {
        if (dists.length != weights.length) {
            throw new IllegalArgumentException("Inputs must have equal lengths");
        }
        double sumVar = 0;
        for (int i = 0; i < dists.length; i++) {
            for (int j = 0; j < dists.length; j++) {
                sumVar += weights[i]*weights[j]*Variance.covarianceEstimate(dists[i].getValues(), dists[j].getValues());
            }
        }
        return sumVar;
    }

    /* public static PropDensityFunction findCombinedReturns(ProbDensityFunction[] dists, double[] weights) */

}
