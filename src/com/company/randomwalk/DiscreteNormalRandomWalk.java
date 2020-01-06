package com.company.randomwalk;

import com.company.NormalDistribution;

public class DiscreteNormalRandomWalk extends RandomWalk {

    /* The sum of two independent normally distributed random variables is normal,
    with its mean being the sum of the two means, and its variance being the sum of the two variances.
     */

    double mu;
    double sigma;
    int nDists;
    NormalDistribution sumDist;

    public DiscreteNormalRandomWalk(double mu, double sigma, int nDists) {
        this.mu = mu;
        this.sigma = sigma;
        this.nDists = nDists;
        this.sumDist = new NormalDistribution(mu*nDists, Math.sqrt(nDists * Math.pow(sigma, 2)));;
    }

    public DiscreteNormalRandomWalk(){}

    public static DiscreteNormalRandomWalk randomWalkFromVar(double mu, double var, int nDists) {
        DiscreteNormalRandomWalk theNewRandomWalk = new DiscreteNormalRandomWalk();
        theNewRandomWalk.mu = mu;
        theNewRandomWalk.sigma = Math.sqrt(var);
        theNewRandomWalk.nDists = nDists;
        theNewRandomWalk.sumDist = NormalDistribution.normalDistributionFromVariance(
                mu*nDists, nDists*var);
        return theNewRandomWalk;
    }

    public double pointProb(double x) {
        return sumDist.pointProb(x);
    }

    public double cumulProb(double x) {
        return sumDist.cumulProb(x);
    }


    public static double sumIdenticalNormalsFromVarPDF(double mu, double var, int nDists, double x) {
        NormalDistribution sumDist = NormalDistribution.normalDistributionFromVariance(
                mu*nDists, nDists*var);
        return sumDist.pointProb(x);
    }

    public static double sumIdenticalNormalsPDF(double mu, double sigma, int nDists, double x) {
        NormalDistribution sumDist = new NormalDistribution(mu*nDists, Math.sqrt(nDists * Math.pow(sigma, 2)));
        return sumDist.pointProb(x);
    }

    public static double sumIdenticalNormalsFromVarCDF(double mu, double var, int nDists, double x) {
        NormalDistribution sumDist = NormalDistribution.normalDistributionFromVariance(
                mu*nDists, nDists*var);
        return sumDist.cumulProb(x);
    }

    public static double sumIdenticalNormalsCDF(double mu, double sigma, int nDists, double x) {
        NormalDistribution sumDist = new NormalDistribution(mu*nDists, Math.sqrt(nDists * Math.pow(sigma, 2)));
        return sumDist.cumulProb(x);
    }



}
