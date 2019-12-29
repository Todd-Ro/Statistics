package com.company;
import com.company.randomwalk.TimeToMoneyOut;
import javafx.print.PageOrientation;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.HashMap;

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
        System.out.println();

        double crPasCh = 244.0 / 495.0; // 49.292929%; Half of 495 is 247.5

        TimeToMoneyOut cGame = new TimeToMoneyOut(5, crPasCh);
        double[][] afterOne = cGame.moneyAfterFirstRound();
        System.out.println(Arrays.toString(afterOne[0]));
        System.out.println(Arrays.toString(afterOne[1]));
        System.out.println();

        System.out.println(MathOps.round(MathOps.findLowestFirstElementInArrays(afterOne), 13));
        double[][] afterTwo = cGame.moneyAfterNRounds(2);
        System.out.println(MathOps.findLowestFirstElementInArrays(afterTwo));
        System.out.println(Arrays.toString(cGame.nextRow(afterOne)));
        System.out.println();
        double[][] afterFour = cGame.moneyAfterNRounds(4);
        MathOps.doubleArrayPrint(afterFour);
        System.out.println();
        double[][] afterFive = cGame.moneyAfterNRounds(5);
        MathOps.doubleArrayPrint(afterFive);
        System.out.println();
        double[][] afterSix = cGame.moneyAfterNRounds(6);
        MathOps.doubleArrayPrint(afterSix);
        System.out.println();
        double[][] afterSeven = cGame.moneyAfterNRounds(7);
        MathOps.doubleArrayPrint(afterSeven);
        System.out.println();



        System.out.println(Arrays.asList(cGame.getProbZeroOutTable()));
        System.out.println(Arrays.asList(cGame.getToSpecifiedZeroProb(.975)));
        /*Results indicate that a gambler who starts with 5 money, with slightly less than even odds, will run out of
        money after an odd number of rounds between 5 and 63 (two-sided 95% probability (not confidence) interval)
        Median is 25.*/
        System.out.println(cGame.findMedian());
        System.out.println(1/(1-2*crPasCh));
        System.out.println();

        int [][] gambleTimes = TimeToMoneyOut.findMedians(crPasCh, 1, 10);
        for (int i = 0; i<10; i++) {
            double ratio = (double)gambleTimes[i][1] / (double)gambleTimes[i][0];
            System.out.println(Arrays.toString(gambleTimes[i]) + " " + (ratio));
        }
        /* The median time until the gambler runs out of money bears some relationship to the expected value of that time
        The medidan tends to be less than the expected value, since the values below the median are less spread out
        than the ones above, even though both the values below and above the median may be skewed toward lower values.
        The expected value is related to the inverse of double the difference between .5 and the probability of winning.
        Starting money divided by this double difference.
         */

        //Commented out because of computing time used
        /*int [][] gambleTimes2 = TimeToMoneyOut.findMedians(crPasCh, 99, 101);
        for (int i = 0; i<3; i++) {
            double ratio = (double)gambleTimes2[i][1] / (double)gambleTimes2[i][0];
            System.out.println(Arrays.toString(gambleTimes2[i]) + " " + (ratio));
        }*/

        /*int [][] gambleTimes3 = TimeToMoneyOut.findMedians(crPasCh, 374, 376);
        for (int i = 0; i<3; i++) {
            double ratio = (double)gambleTimes3[i][1] / (double)gambleTimes3[i][0];
            System.out.println(Arrays.toString(gambleTimes3[i]) + " " + (ratio));
        }*/

        System.out.println();
        System.out.println(MathOps.greatestCommonFactor(100, 110));

        Pair<Integer, Integer> twoFifths = MathOps.reduceFrac(24, 60);
        System.out.println(twoFifths.getKey() + ", " + twoFifths.getValue());

        Die firstD6 = new Die(1, 6, 1);
        Die secondD6 = new Die(1, 6, 1);

        HashMap h = Die.sumDice(firstD6, secondD6);
        System.out.println(h.entrySet());
        Die thirdD6 = new Die(1, 6, 1);
        HashMap h2 = Die.addDieToSum(thirdD6, h);
        System.out.println(h2.entrySet());



        System.out.println();
        int[] divisors = new int[] {6, 10, 15};
        System.out.println(MathOps.findLCM(divisors));


    }
}
