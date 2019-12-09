package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ExponentialDist extends ContinuousProbDensityFunction {

    double lambda;
    double beta;


    public ExponentialDist() {

    }

    public ExponentialDist(double initLambda) {
        this.lambda = initLambda;
        this.beta = 1/initLambda;
    }

    public static ExponentialDist exponentialDistFromBeta(double beta) {
        ExponentialDist newDist = new ExponentialDist(1/beta);
        return newDist;
    }

    public Double pointProb(double x) {
        if (x >= 0) {
            return (lambda * Math.exp(-1*lambda*x));
        }
        else {
            return 0.0;
        }
    }

    public Double cumulProb(double x) {
        if (x >= 0) {
            return (1 - Math.exp(-1*lambda*x));
        }
        else {
            return 0.0;
        }
    }


}
