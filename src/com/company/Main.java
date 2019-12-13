package com.company;
import com.company.randomwalk.TimeToMoneyOut;
import javafx.print.PageOrientation;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here

        GeometricDist successCheck = new GeometricDist(.6);
        Double thirdTimeCharm = successCheck.pointProb(2);
        //thirdTimeCharm should be 0.096.
        System.out.println(thirdTimeCharm);

        GeometricDist diceCheck = new GeometricDist(1.0/6.0);
        /*Double sixthFirstSix = diceCheck.pointProb(5);
        System.out.println("The probability that the first 6 comes after exactly 5 failures is " + sixthFirstSix); */
        Double noSixAfterSix = 1 - diceCheck.cumulProb(5);
        //noSixAfterSix should be roughly .335
        System.out.println(noSixAfterSix);

        ExponentialDist baseExpD = new ExponentialDist(1);
        System.out.println(baseExpD.cumulProb(1));
        // Should equal 1 - 1/e, which is .632
        System.out.println(baseExpD.cumulProb(.693));
        // Should be about 0.5

        double[][] SP01to14 = {{1148.08, 0}, {879.82, 14.53}, {1111.92, 20.8}, {1211.92, 20.98}, {1248.29, 23.15},
                {1418.30, 27.16}, {1468.36, 27.86}, {903.25, 21.85}, {1115.10, 27.19}, {1257.64, 25.44},
                {1257.60, 26.59}, {1426.19, 32.67}, {1848.36, 39.75}, {2058.9, 42.46}};
        double[] SP02to14Returns = MathOps.findSumDifferences(SP01to14);
        System.out.println(Arrays.toString(SP02to14Returns));
        System.out.println(Variance.mean(SP02to14Returns));
        System.out.println(Variance.popVarianceEstimate(SP02to14Returns));
        System.out.println(Variance.popStDevEstimate(SP02to14Returns));
        System.out.println(Variance.stDevOfPopMeanEstimate(SP02to14Returns));
        System.out.println(Arrays.toString(Variance.confidenceIntervalPopMeanTwoSigma(SP02to14Returns)));

        double[][] dist = {{0.4, 0.25}, {0.1, 0.5}, {-0.2, 0.25}};
        System.out.println(Variance.varianceOfWeighted(dist));
        System.out.println();

        double[] nrets = {.21, .3, .07, -.05, -.02, .09};
        double[] wrets = {.09, .21, .07, -.02, -.05, .3};
        System.out.println("nrets underlying st dev: " + Variance.popStDevEstimate(nrets));
        System.out.println(Variance.popStDevEstimate(wrets));
        System.out.println("nrets underlying variance: " + Variance.popVarianceEstimate(nrets));
        System.out.println(MathOps.round(Variance.covarianceEstimate(nrets, wrets),5));
        System.out.println("correl of nrets and wrets: " +
                MathOps.round(Variance.correlationEstimate(nrets, wrets), 4));
        ProbDensityFunction nRetsDist = new ProbDensityFunction(nrets);
        ProbDensityFunction wRetsDist = new ProbDensityFunction(wrets);
        double sumvar = nRetsDist.findVarianceOfTwoDistSum(wRetsDist, 0.5, 0.5);
        System.out.println("Variance of sum of nrets and wrets: " + sumvar);
        System.out.println("St Dev of sum of nrets and wrets: " + Math.pow(sumvar, 0.5));
        ProbDensityFunction[] dists = {nRetsDist, wRetsDist};
        double[] halves = {0.5, 0.5};
        System.out.println(Math.pow(ProbDensityFunction.findVarianceOfMultiDistSum(dists, halves),0.5));
        // Should equal previous value
        System.out.println();
        System.out.println(nRetsDist.findUnderlyingVariance());
        System.out.println(Variance.covarianceEstimate(nRetsDist.getValues(), nRetsDist.getValues()));
        //Covariance of nRetsDist with itself should equal variance
        double[] combined = ProbDensityFunction.findCombinedValues(dists, halves);
        System.out.println(Arrays.toString(combined));
        System.out.println(Variance.correlationEstimate(nrets, combined));
        System.out.println(Variance.correlationEstimate(wrets, combined));
        System.out.println(nRetsDist.findStDevContribution(combined, .5));
         // Should be half of combined st dev

        System.out.println(Arrays.toString(MathOps.quadraticSol(5, 6, 1)));
        System.out.println((ProbDensityFunction.findMinVarianceFromSd(.5, .25, 0.0)));
        System.out.println(ProbDensityFunction.findTangentFromStDev(.5, .25, 0,
                .26, .06, .03));
        System.out.println();


        NormalDistribution testDist = new NormalDistribution(0, 3.5);
        System.out.println(testDist.pointProbIfMuZero(0.15));
        //System.out.println(testDist.cumulProb(1.1));

        NormalDistribution testDist2 = new NormalDistribution(.4, 1.7);
        System.out.println(testDist2.pointProb(0.45));
        System.out.println(testDist2.cumulProb(.45));
        System.out.println();

        System.out.println(NormalDistribution.standardNormalPDF(1.1));
        System.out.println(NormalDistribution.standardNormalCDF(1.1));
        System.out.println(NormalDistribution.standardNormalCDF(-1));

        System.out.println(ErrorFunction.erf(1.4));

        LognormalDist quarterLog = new LognormalDist(0, 0.25);
        System.out.println(quarterLog.pointProb(1));
        System.out.println(quarterLog.cumulProb(1.25));

        double crPasCh = 244.0 / 495.0;

        TimeToMoneyOut cGame = new TimeToMoneyOut(5, crPasCh);
        double[][] afterOne = cGame.moneyAfterFirstRound();
        System.out.println(Arrays.toString(afterOne[0]));
        System.out.println(Arrays.toString(afterOne[1]));
        System.out.println();
        System.out.println(MathOps.round(MathOps.findLowestFirstElementInArrays(afterOne), 13));
        double[][] afterTwo = cGame.moneyAfterNRounds(2);
        System.out.println(MathOps.findLowestFirstElementInArrays(afterTwo));
        System.out.println(Arrays.toString(cGame.nextRow(afterOne)));
        double[][] afterFour = cGame.moneyAfterNRounds(4);
        System.out.println(Arrays.toString(afterFour[0]));
        System.out.println(Arrays.toString(afterFour[1]));
        System.out.println(Arrays.toString(afterFour[2]));
        System.out.println(Arrays.toString(afterFour[3]));
        System.out.println(Arrays.toString(afterFour[4]));
        double[][] afterNine = cGame.moneyAfterNRounds(9);
        System.out.println(Arrays.toString(afterNine[3]));
        System.out.println(Arrays.toString(afterNine[4]));
        System.out.println(Arrays.toString(afterNine[5]));
        System.out.println(Arrays.toString(afterNine[6]));
        System.out.println(Arrays.toString(afterNine[7]));


    }
}
