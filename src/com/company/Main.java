package com.company;

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
    }
}
