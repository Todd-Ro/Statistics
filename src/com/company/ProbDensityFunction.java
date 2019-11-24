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
        that may move together.
        Use to find variance of combined distribution that is the sum of two finite discrete distributions with
        equal probability among their possible values
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

    public static double[] findCombinedValues(ProbDensityFunction[] dists, double[] weights) {
        /* Finds the weighted sum of two ProbDensityFunctions with equal numbers of entries in values.
        Assumes that the density at each possible value within each distribution is equal, but allows each
        distribution to be assigned a different weight in the sum.*/
        if (dists.length != weights.length) {
            throw new IllegalArgumentException("dists and weights must have equal lengths");
        }
        int numItems = dists[0].getValues().length;
        for (int i = 0; i<dists.length; i++) {
            if (dists[i].getValues().length != numItems) {
                throw new IllegalArgumentException("The distrubtions in dists " +
                        "must have equal numbers of values in their values array.");
            }
        }
        double[] sumValues = new double[numItems];
        for (int i = 0; i<numItems; i++) {
            double thisValue = 0;
            for (int j = 0; j<dists.length; j++) {
                thisValue += dists[j].getValues()[i]*weights[j];
            }
            sumValues [i] = thisValue;
        }
        return sumValues;

    }

    public static ProbDensityFunction findCombinedValuesDist(ProbDensityFunction[] dists, double[] weights) {
        ProbDensityFunction ret = new ProbDensityFunction(findCombinedValues(dists, weights));
        return ret;
    }

    public double findStDevContribution(double[] sumValues, double thisWeight) {
        /*Finds this distribution's contribution to the standard deviation of a sum distribution which was already
        computed with this distribution as a component. If sumValues was not actually computed from this distribution,
        no error will occur, but the result will not have the intended meaning.*/
        return thisWeight*Variance.popStDevEstimate(this.values)*Variance.correlationEstimate(this.values, sumValues);
    }


}
