package com.company;

public class LognormalDist extends NormalDistribution {

    double mu;
    double sigma;

    NormalDistribution correspondingNormal;

    public LognormalDist(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
        this.correspondingNormal = new NormalDistribution(mu, sigma);
    }


    protected double[] logAdjust(double rho, double theta, double y) {
        //Returns mu, sigma, x. Theta looks like sigma and y is related to x.
        double x = y * Math.log(y);
        double sigma = y * theta;
        double mu = y*rho;
        double[] ret = {mu, sigma, x};
        return ret;
    }


    @Override
    public Double pointProb(double x) {
        double[] values = logAdjust(mu, sigma, x);
        double finderMu = values[0];
        double finderSigma = values[1];
        double finderX = values[2];
        NormalDistribution densityFinder = new NormalDistribution(finderMu, finderSigma);
        return densityFinder.pointProb(finderX);
    }

    @Override
    public Double pointProbIfMuZero(double x) {
        double[] values = logAdjust(0, sigma, x);
        double finderSigma = values[1];
        double finderX = values[2];
        NormalDistribution densityFinder = new NormalDistribution(0, finderSigma);
        return densityFinder.pointProb(finderX);
    }


    @Override
    public Double cumulProb(double x) {
        return correspondingNormal.cumulProb(Math.log(x));
    }


}
