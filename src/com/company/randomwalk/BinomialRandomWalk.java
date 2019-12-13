package com.company.randomwalk;

public class BinomialRandomWalk extends RandomWalk {
    //In this type of random walk, the value will increase or decrease by a fixed percentage of its current value
    //Compare binomial class in my Finance project

    double upMult; //Amount previous value gets multiplied by if increase
    double downMult; //Amount previous value gets multiplied by if decrease
    double upProb; //Probability of increase

}
