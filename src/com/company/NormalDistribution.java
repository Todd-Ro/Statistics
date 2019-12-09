package com.company;

public class NormalDistribution extends ExponentialDist {

    double mu;
    double sigma;
    ExponentialDist correspondingExpon;

    public NormalDistribution(double initmu, double initsigma) {
        this.mu = initmu;
        this.sigma = initsigma;
    }

    public ExponentialDist findCorrespondingExponential() {
        double beta = sigma * Math.sqrt(2*Math.PI);
        ExponentialDist adjustedDist = ExponentialDist.exponentialDistFromBeta(beta);
        correspondingExpon = adjustedDist;
        return adjustedDist;
    }

    public NormalDistribution normalDistributionFromVariance(double initmu, double initvariance) {
        NormalDistribution ret = new NormalDistribution(initmu, Math.sqrt(initvariance));
        return ret;
    }

    private static Double scaledSquare(double v, double mu, double theta) {
        double xForExponential = (Math.pow(v-mu,2) * Math.pow(Math.PI/2.0, 0.5) / theta);
        return xForExponential;
    }


    public Double pointProbIfMuZero(double x) {
        double xForExponential = scaledSquare(x, mu, sigma);
        ExponentialDist adjustedDist = findCorrespondingExponential();
        return MathOps.round(adjustedDist.pointProb(xForExponential),14);
    }

    @Override
    public Double pointProb(double x) {
        double xForExponential = scaledSquare(x, mu, sigma);
        ExponentialDist adjustedDist = findCorrespondingExponential();
        return MathOps.round(adjustedDist.pointProb(xForExponential),14);
    }

    public static Double standardNormalPDF(double x) {
        NormalDistribution standard = new NormalDistribution(0, 1);
        return standard.pointProbIfMuZero(x);
    }

    public static Double standardNormalCDF(double x) {
        // Based on the Zelen & Severo approximation
        if (x < 0) {
            return MathOps.round((1 - standardNormalCDF(-x)),7);
        }
        double b0 = 0.2316419;
        double b1 = 0.31938153;
        double b2 = -0.356563782;
        double b3 = 1.781477937;
        double b4 = -1.821255978;
        double b5 = 1.330274429;
        double t = 1/(1+b0*x);
        double phiX = standardNormalPDF(x);
        return MathOps.round((1 - phiX*(b1*t + b2*Math.pow(t, 2) +
                b3*Math.pow(t,3) + b4*Math.pow(t, 4) + b5*Math.pow(t, 5))),7);
    }

    @Override
    public Double cumulProb(double x) {
        return standardNormalCDF((x-mu)/sigma);
    }



}
