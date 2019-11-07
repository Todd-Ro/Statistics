package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ExponentialDist {

    double lambda;
    double beta;


    public ExponentialDist(double setLambda) {
        this.lambda = setLambda;
        this.beta = 1/setLambda;
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
