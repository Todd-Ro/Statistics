package com.company.randomwalk;

import com.company.MathOps;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class BinomialRandomWalk extends RandomWalk {
    //In this type of random walk, the value will increase or decrease by a fixed percentage of its current value
    //Compare binomial class in my Finance project
    /*Decision to be made whether this class should extend RandomWalk or GeometricDist; there is a certain "filling-in"
    that can occur here that resembles GeometricDist
    Could also make RandomWalk extend GeometricDist, which is a sublclass of ProbDensityFunction, rather than extending
    ProbDensityFunction directly, but any random walk that occurs continuously rather than in discrete steps will not
    share BinomialRandomWalk's similarities with GeometricDist.
     */


    double upMult; //Amount previous value gets multiplied by if increase
    double downMult; //Amount previous value gets multiplied by if decrease
    double upProb; //Probability of increase



}
