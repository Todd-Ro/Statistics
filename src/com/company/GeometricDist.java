package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class GeometricDist extends ProbDensityFunction {

    double p;
    double q;
    ArrayList<Double> probDist;
    ArrayList<Double> probCumulDist;
    int highestFilled;

    public GeometricDist(double pSuccess) {
        this.p = pSuccess;
        this.q = 1 - p;
        this.highestFilled = -1;
    }



    public Double pointProb(int nFailures) {
        if (highestFilled >= nFailures) {
            return probDist.get(nFailures);
        }
        else {
            if (highestFilled < 0) {
                probDist = new ArrayList<Double>();
                probDist.add(p);
                probCumulDist = new ArrayList<Double>();
                probCumulDist.add(p);
                highestFilled = 0;
            }
            while (highestFilled < nFailures) {
                Double latestCumul = probCumulDist.get(highestFilled);
                Double remainingProb = 1 - latestCumul;
                Double newProb = MathOps.round(remainingProb * p,16);
                probDist.add(newProb);
                probCumulDist.add(latestCumul + newProb);
                highestFilled++;
            }


        }
        return probDist.get(nFailures);
    }

    public Double cumulProb(int nFailures) {
        pointProb(nFailures);
        return probCumulDist.get(nFailures);
    }

}
