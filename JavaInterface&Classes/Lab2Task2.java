/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SinghSunpreet_lab2;

/**
 *
 * @author Sunpreet Singh C0436626
 */
public class Lab2Task2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
 /**
  * try changing the denominator value below to zero and the system will throw an exception
  * if you type the numerator value to zero in the second rational it will throw exception
  * 
  */
        Rational first = new Rational(-3, 2);
        System.out.println(first);// prints the first rational

        Rational second = new Rational(44, 3);
         System.out.println(second); //prints the second rational

        // Rational result = first.abs();
        System.out.println(first.divide(second));//testing divide method type rational
        System.out.println(first.add(second));//testing add method type rational
        System.out.println(first.getDenominator());// gets the denominator of first rational
        System.out.println("|" + first.abs() + "|");//gives the absolute value of first rational
        System.out.println(second.intValue());// returns an int value of second rational
        System.out.println(second.getNumerator());// gets the numerator of second rational
        System.out.println(first.signum());// returns -1 because the sign of first rational is negative
        System.out.println(second.isProper());// returns false because second rational is not a proper fraction
        System.out.println(first.multiply(second));//multiplies first rational and second rational
        System.out.println(second.doubleValue());// returns double value of second rational
        System.out.println(first.floatValue());//returns the float value of first rational

    }

}
