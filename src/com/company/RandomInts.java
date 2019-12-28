package com.company;

import java.util.Random;

public class RandomInts {
    /* A probably unnecessary class that generates random integers by calling on Java classes that already do so.
    Loosely based on the way random number generation would work on an 8-bit system, while still providing the
    equality and accuracy of probability (1/3 does not equal 85/256) that a modern computing system allows.
     */

    /*Some methods contain both static and non-static methods. For static methods, an instance of a random number
    generator from another class is instantiated within the method; for non-static methods, a random number generator
    that was instantiated with this class is used.
     */

    /* This class likely uses a tad more computing power than the methods it calls, since it will sometimes call a
    random integer generator twice to generate a single integer. This could conceivably either increase or decrease the
    disadvantages of the underlying generator not being truly random.
     */

    Random rand;

    public RandomInts() {
        this.rand = new Random();
    }

    public int rand3() {
        //Generates a random integer between 0 and 2
        int twoBit = rand.nextInt(4);
        if (twoBit < 3) {
            return twoBit;
        }
        else {
            return rand.nextInt(3);
        }
        /* Essentially, this method attempts to use a solution based on 2^n possibilities, but resorts to just having
        the Random class provide an answer if this does not work.
        We could generate new ints until we get one in an acceptable range, but then
        there would technically be no guaranteed maximum computation time.
         */
    }

    public int rand9() {
        //Generates a random integer between 0 and 8
        int fourBit = rand.nextInt(16);
        if (fourBit < 9) {
            return fourBit;
        }
        else if (fourBit < 15) {
            int tercile = fourBit % 3;
            return (rand3() + tercile*3);
        }
        else {
            return rand.nextInt(9);
        }
    }

    public static int rando27() {
        //Generates a random integer between 0 and 26
        Random rando = new Random();
        int fiveBit = rando.nextInt(32);
        if (fiveBit < 27) {
            return fiveBit;
        }
        if ((fiveBit >= 27) && (fiveBit<30)) {
            return rando.nextInt(9) + (fiveBit -27)*9;
        }
        else {
            return rando.nextInt(27);
        }
    }

    public int rand27() {
        //Generates a random integer between 0 and 26
        int fiveBit = rand.nextInt(32);
        if (fiveBit < 27) {
            return fiveBit;
        }
        else if (fiveBit<30) {
            return rand9() + (fiveBit -27)*9;
        }
        else {
            return rand.nextInt(27);
        }
    }

    /*public static int rando216() {
        Random rando = new Random();
        int eightBit = rando.nextInt(256);
        if (eightBit < 176) {
            return eightBit;
        }
    }*/
}
