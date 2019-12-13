package com.company.randomwalk;

import com.company.MathOps;

public class TimeToMoneyOut {
        /*Determines the probability distribution for how many bets a gambler, making bets with a 2:1 payoff, will
        be able to make before running out of money.
         */
        int initMoney; //The starting money, as a multiple of the amount wagered each time. Should be greater than 0.
        double p; // The probability of winning each round.

        public TimeToMoneyOut(int startMoney, double prob) {
            this.initMoney = startMoney;
            this.p = prob;
        }

        public double[] nextRow(double[][] oldRows) {
            /*Need to input an array of length two, two. Inputting a longer array is possible,
            but other elements will not be used.
             */
            double newFirst = oldRows[0][0] - 1;
            double newProb = (oldRows[0][1]*(1-p) + oldRows[1][1]*p);
            return new double[] {newFirst, newProb};
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
                int len = previous.length;
                double lowest = MathOps.round(MathOps.findLowestFirstElementInArrays(previous), 13);
                if (lowest > 0) {
                    double[][] ret = new double[len+1][2];
                    ret[0] = new double[] {previous[0][0]+1, previous[0][1]*p};
                    ret[len] = new double[] {previous[len-1][0]-1, previous[len-1][1]*(1-p)};
                    if (len > 1) {
                        for (int i=1; i<len; i++) {
                            double[][] oldRows = {previous[i-1], previous[i]};
                            ret[i] = nextRow(oldRows);
                        }
                    }
                    return ret;
                }
                else {
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
        }

        public double[][] moneyAfterFirstRound() {
            double[][] afterOne = {{initMoney+1, p}, {initMoney - 1, 1-p}};
            return afterOne;
        }


        //TODO: Find cumulative probability of reaching zero by a certain point
        /*TODO: Create output that only has probabilities of reaching zero after certain numbers of rounds, not all
        probabilities at a particular point.
         */

}
