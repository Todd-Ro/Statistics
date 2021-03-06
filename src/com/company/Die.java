package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Die  {
    //Used to simulate dice. Has some similarities to the ProbDensityFunction and RandomInts classes.
    //In many ways, an equally weighted probability density function

    ArrayList<Integer> values;
    public int numFaces;

    public int getNumFaces() {
        return numFaces;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public Die(int min, int max, int step) {
        //To get a standard 6-sided die, construct with min 1, max 6, step 1
        ArrayList<Integer> v = new ArrayList<Integer>();
        int i = min;
        while (i <= max) {
            v.add(i);
            i += step;
        }
        this.values = v;
        this.numFaces = v.size();
    }

    public static HashMap<Integer, Pair<Integer, Integer>> sumDice (Die die1, Die die2) {
        //Returns the value, then a pair representing the numerator and denominator of the probability of that value
        int denominator = die1.getNumFaces() * die2.getNumFaces();
        HashMap<MutaKey, Integer> values = new HashMap<MutaKey, Integer>();
        // values has sum first (as the x in the MutaKey), then the number of pairs that add up to it
        for (Integer thisInt : die1.getValues()) {
            for (Integer thatInt : die2.getValues()) {
                int sum = thisInt + thatInt;
                MutaKey m = new MutaKey(sum);
                if (values.keySet().contains(m)) {
                    Integer oldCount = values.get(m);
                    values.replace(m, oldCount + 1);
                }
                else {
                    values.put(m, 1);
                }
            }
        }
        HashMap<Integer, Pair<Integer, Integer>> ret = new HashMap<Integer, Pair<Integer, Integer>>();
        for (MutaKey key : values.keySet()) {
            Pair<Integer, Integer> prob = MathOps.reduceFrac(values.get(key), denominator);
            ret.put(key.getX(), prob);
        }
        return ret;
    }


    static int findLCMDenom(HashMap<Integer, Pair<Integer, Integer>> input) {
        int len = input.size();
        int[] denoms = new int[len];
        int i = 0;
        for (Pair<Integer, Integer> thisFrac : input.values()) {
            denoms[i] = thisFrac.getValue();
            i++;
        }
        return MathOps.findLCM(denoms);
    }

    //TODO: Create method for adding more than two dice and/or a method for adding an additional die to a sum result

    public static HashMap<Integer, Pair<Integer, Integer>> addDieToSum
            (Die newDie, HashMap<Integer, Pair<Integer, Integer>> oldSums) {
        //Returns the value, then a pair representing the numerator and denominator of the probability of that value
        int lcmOldDenom = findLCMDenom(oldSums);
        int newDenom = lcmOldDenom * newDie.getNumFaces();

        HashMap<MutaKey, Integer> values = new HashMap<MutaKey, Integer>();
        // values has sum first (as the x in the MutaKey), then the number of pairs that add up to it
        for (Integer thisInt : newDie.getValues()) {
            for (Integer oldSum : oldSums.keySet()) {
                Pair<Integer, Integer> thisFrac = oldSums.get(oldSum);
                Integer thisOldDenom = thisFrac.getValue();
                int sum = thisInt + oldSum;
                MutaKey m = new MutaKey(sum);
                if (values.keySet().contains(m)) {
                    Integer oldCount = values.get(m);
                    values.replace(m, oldCount + 1 * thisFrac.getKey() * (lcmOldDenom / thisOldDenom));
                }
                else {
                    values.put(m, 1 * thisFrac.getKey() * (lcmOldDenom / thisOldDenom));
                }
            }
        }
        HashMap<Integer, Pair<Integer, Integer>> ret = new HashMap<Integer, Pair<Integer, Integer>>();
        for (MutaKey key : values.keySet()) {
            Pair<Integer, Integer> prob = MathOps.reduceFrac(values.get(key), newDenom);
            ret.put(key.getX(), prob);
        }
        return ret;
    }


    //TODO: Create a way to turn the die sum result into a cumulative probability







}
