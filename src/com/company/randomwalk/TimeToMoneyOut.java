package com.company.randomwalk;

import com.company.MathOps;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeToMoneyOut {
        /*Determines the probability distribution for how many bets a gambler, making bets with a 2:1 payoff, will
        be able to make before running out of money.
         */

    Integer initMoney; //The starting money, as a multiple of the amount wagered each time. Should be greater than 0.
    Double p; // The probability of winning each round.

    HashMap<Integer, Double> probZeroOutTable;
    Double cumulativeProbOfEntriesPutIntoZeroOutTableSoFar;
    ArrayList<Pair<Integer, Double>> sortedZeroOutTable; // This arguably should not be given a public getter

    public HashMap<Integer, Double> getProbZeroOutTable() {
        return probZeroOutTable;
    }

    public TimeToMoneyOut(int startMoney, double prob) {
            this.initMoney = startMoney;
            this.p = prob;
            this.probZeroOutTable = new HashMap<Integer, Double>();
            cumulativeProbOfEntriesPutIntoZeroOutTableSoFar = 0.0;
        }

        public double[] nextRow(double[][] oldRows) {
            /*Need to input an array of length two, two. Inputting a longer array is possible,
            but other elements will not be used.
             */
            double newFirst = oldRows[0][0] - 1;
            double newProb = (oldRows[0][1]*(1-p) + oldRows[1][1]*p);
            return new double[] {newFirst, newProb};
        }

        public double[][] moneyAfterFirstRound() {
            double[][] afterOne = {{initMoney+1, p}, {initMoney - 1, 1-p}};
            if (initMoney - 1 <= 0) {
                if (!probZeroOutTable.containsKey(1)) {
                    probZeroOutTable.put(1, 1 - p);
                    cumulativeProbOfEntriesPutIntoZeroOutTableSoFar = (1-p);
                }
            }
            return afterOne;
        }

    public double[][] moneyAfterNextRound(double[][]previous) {
        int len = previous.length;
        double lowest = MathOps.round(MathOps.findLowestFirstElementInArrays(previous), 13);
        if (lowest > 0) {
            double[][] ret = new double[len+1][2];
            ret[0] = new double[] {previous[0][0]+1, previous[0][1]*p};
            double finalProb = previous[len-1][1]*(1-p);
            ret[len] = new double[] {previous[len-1][0]-1, finalProb};

            // Determine how many rounds in we are by the difference in maximum possible money from starting value.
            int rounds = (int) (ret[0][0] - initMoney);
            if(previous[len-1][0]-1 <= 0) {
                if (!probZeroOutTable.containsKey(rounds)) {
                    probZeroOutTable.put(rounds, finalProb);
                    cumulativeProbOfEntriesPutIntoZeroOutTableSoFar += finalProb;
                }
            }


            if (len > 1) {
                for (int i=1; i<len; i++) {
                    double[][] oldRows = {previous[i-1], previous[i]};
                    ret[i] = nextRow(oldRows);
                }
            }
            return ret;
        }
        else {
                /*If "lowest" last time was zero, the second-lowest should have been 2,
                so there should not be a zero here since the gambler only loses one money unit per round.
                 */
            double[][] ret = new double[len][2];
            ret[0] = new double[] {previous[0][0]+1, previous[0][1]*p};
            ret[len-1] = new double[] {previous[len-2][0]-1, previous[len-2][1]*(1-p)};
            if (len > 2) {
                for (int i=1; i<len; i++) {
                    double[][] oldRows = {previous[i-1], previous[i]};
                    ret[i] = nextRow(oldRows);
                }
            }
            return ret;
        }
    }

    public double[][] moneyAfterNRounds(int rounds) {
            /*Enter an integer number of rounds, 1 or higher. The output will be in pairs, with the number of
            wagers worth of money first, then the probability.
            Note that the probability of being at zero after n rounds in this function is only the probability of
            zeroing out at that point, not the cumulative probability of reaching zero. Once past the point
            where the gambler could have zeroed out ina  previous round, the probabilities will no longer sum to 1.
             */
        if (rounds == 1) {
            return moneyAfterFirstRound();
        }
        else {
            double[][] previous = moneyAfterNRounds(rounds - 1);
            return moneyAfterNextRound(previous);
        }
    }

    public HashMap getToSpecifiedZeroProb(double p) {
        double[][] previous = moneyAfterFirstRound();
        while (cumulativeProbOfEntriesPutIntoZeroOutTableSoFar < p) {
            previous = moneyAfterNextRound(previous);
        }
        return probZeroOutTable;
    }

        //TODO: Create method for getting to median, using MathOps.sortHashMap to sort probZeroOutTable.

    protected ArrayList<Pair<Integer, Double>> sortZeroOutTable() {
        ArrayList<Pair<Integer, Double>> ret = MathOps.sortHashMap(probZeroOutTable);
        sortedZeroOutTable = (ArrayList<Pair<Integer, Double>>) ret;
        return ret;
    }

    public int findMedian() {
        if (cumulativeProbOfEntriesPutIntoZeroOutTableSoFar < 0.5) {
            getToSpecifiedZeroProb(0.5);
        }
        sortZeroOutTable();
        double sum = 0;
        int i = 0;
        while (sum < 0.5) {
            Pair<Integer, Double> p = sortedZeroOutTable.get(i);
            sum += p.getValue();
            if (sum >= 0.5) {
                return p.getKey();
            }
            i++;
        }
        return sortedZeroOutTable.get((sortedZeroOutTable.size() - 1)).getKey();
    }

    public static int[][] findMedians(double p, int start, int finish) {
        /*Produces the median time until running out of money, given a particular probability, for every starting money
        amount in a range. May tax computing resources if large start/finish numbers are used.
         */
        int[][] ret = new int[finish - start + 1][2];
        for (int i = start; i <= finish; i++) {
            TimeToMoneyOut thisGambler = new TimeToMoneyOut(i, p);
            ret[i - start][0] = i;
            ret[i - start][1] = thisGambler.findMedian();
        }
        return ret;
    }





        //TODO: Find cumulative probability of reaching zero by a certain point
        /*TODO: Create output that only has probabilities of reaching zero after certain numbers of rounds, not all
        probabilities at a particular point.
         */




}
