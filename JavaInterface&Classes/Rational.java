/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SinghSunpreet_lab2;

import java.math.*;
import java.lang.Object;

/**
 *
 * @author Sunpreet Singh
 * C0436626
 */
public class Rational {
    // declaring instance variables

    BigInteger num;
    BigInteger den;

    static final Rational ONE = new Rational(1, 1);
    static final Rational ZERO = new Rational(0, 1);

    //2.Constructor
    /**
     * Creates a Rational number given a numerator and denominator
     *
     * @param n = numerator
     * @param d = denominator throws AithematicException if the denominator is
     * zero
     */
    public Rational(long n, long d) {
        //throws an exception if the given denominator is zero    
        if (d == 0) {
            throw new ArithmeticException("Entered denominator is zero");
        }

        this.num = BigInteger.valueOf(n);
        this.den = BigInteger.valueOf(d);

    }

    //2.Constructor
    /**
     * Creates a Rational number given a numerator and denominator.
     *
     * @param n = numerator
     * @param d = denominator throws a null pointer exception is either of the
     * denominator or numerator is null throws an arithmetic exception is the
     * denominator is zero
     */
    public Rational(BigInteger n, BigInteger d) {
        this.num = n; //numerator value
        this.den = d; //denominator value

        if (d.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Entered denominator is zero");
        }
        if (n == null || d == null) {
            throw new NullPointerException(" object is missing ");
        }
    }

    //3.Methods
    /**
     * Returns the sign of this value 1 if this value is negative, +1 if
     * positive, or 0 if exactly zero.
     *
     * @return
     */
    public int signum() {
        int numSign = this.num.signum();
        int denSign = this.den.signum();
        if (numSign == 0) {
            return 0;
        } else if (numSign < 0 && denSign < 0) {
            return 1;
        } else if (numSign < 0 || denSign < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Get the denominator part of this Rational number's fractional form.
     *
     * @return denominator value
     */
    public BigInteger getDenominator() {
        return this.den;
    }

    /**
     * Get the numerator part of this Rational number's fractional form.
     *
     * @return numerator value
     */
    public BigInteger getNumerator() {
        return this.num;
    }

    /**
     * Returns true if this is a proper fraction.
     *
     * @return true if the absolute value of the numerator is less than the
     * absolute value of the denominator; false otherwise.
     */
    public boolean isProper() {
        int numProp = this.num.intValue();
        int denProp = this.den.intValue();
        if (numProp > 0 && denProp > 0 && numProp < denProp) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * Returns the string representation of this Rational. If the denominator is
     * 1, only the numerator will shown. Otherwise, the value is displayed in
     * the format "numerator/denominator"
     *
     * @return string
     */
    public String toString() {
        int tempNum = this.num.intValue();
        int tempDen = this.den.intValue();
        String str;

        if (tempDen == 1) {
            str = "" + tempNum;
        } else {
            str = "(" + tempNum + "/" + tempDen + ")";
        }
        return str;
    }

    /**
     * Translates the given double into an equivalent Rational value. If source
     * has a value of 0 or 1, returns Rational.ZERO or Rational.ONE
     * respectively.
     *
     * @param source - a value to convert
     * @return the given value as a Rational instance
     */
    public static Rational valueOf(double source) {
        BigDecimal decimal;
        BigInteger tempInt;
        BigInteger tempDen;
        Rational temp2;
        if (source == 0) {
            return Rational.ZERO;
        } else if (source == 1) {
            return Rational.ONE;
        } else {
            decimal = BigDecimal.valueOf(source);
            tempInt = decimal.unscaledValue();
            tempDen = BigInteger.TEN.pow(decimal.scale());
            temp2 = new Rational(tempInt, tempDen);
            return temp2;
        }
    }

    /**
     * Computes the value of this Rational divided by the given other Rational.
     * Returns Rational.ZERO or Rational.ONE if the resulting value is 0 or 1
     * respectively.
     *
     * @param divisor
     * @return this / val
     */
    public Rational divide​(Rational divisor) {
        /**
         * BigInteger tempNum1; BigInteger tempDen1; Rational tempR; tempNum1 =
         * this.num.multiply(divisor.den); tempDen1 =
         * this.den.multiply(divisor.num); tempR = new Rational(tempNum1,
         * tempDen1); return tempR;
         */
        try {
            return new Rational(this.num.multiply(divisor.den), this.den.multiply(divisor.num));
        } catch (ArithmeticException ex) {
            System.out.println("value can't be zero");

        }
        return new Rational(this.num.multiply(divisor.den), this.den.multiply(divisor.num));
    }

    /**
     * Computes the value of this Rational divided by the given number. Returns
     * Rational.ZERO or Rational.ONE if the resulting value is 0 or 1
     * respectively.
     *
     * @param divisor
     * @return this / val ArithmeticException - if val is zero
     */
    public Rational divide​(double divisor) {

        Rational tempR1 = Rational.valueOf(divisor);

        try {
            return this.divide(tempR1);
        } catch (ArithmeticException ex) {
            System.out.println("value can't be Zero");
        }
        return this.divide(tempR1);
    }

    /**
     * Computes the value of this Rational plus the given other Rational.
     * Returns Rational.ZERO or Rational.ONE if the resulting value is 0 or 1
     * respectively.
     *
     * @param addend
     * @return Rational - this + val
     */
    public Rational add​(Rational addend) {
        BigInteger tempNum2;
        BigInteger tempDen2;
        Rational tempR2;
        tempNum2 = (this.num.multiply(addend.den)).add((addend.num.multiply(this.den)));
        tempDen2 = this.den.multiply(addend.den);
        tempR2 = new Rational(tempNum2, tempDen2);

        return tempR2;
    }

    /**
     * Computes the value of this Rational plus the given number. Returns
     * Rational.ZERO or Rational.ONE if the resulting value is 0 or 1
     * respectively.
     *
     * @param addend
     * @return Rational
     */
    public Rational add​(double addend) {
        Rational tempR3 = Rational.valueOf(addend);
        return this.add(tempR3);
    }

    /**
     * Computes the value of this Rational minus the given other Rational.
     * Returns Rational.ZERO or Rational.ONE if the resulting value is 0 or 1
     * respectively.
     *
     * @param subtrahend
     * @return
     */
    public Rational subtract​(Rational subtrahend) {
        return new Rational((this.num.multiply(subtrahend.den)).subtract((subtrahend.num.multiply(this.den))), this.den.multiply(subtrahend.den));
    }

    /**
     * Computes the value of this Rational minus the given number. Returns
     * Rational.ZERO or Rational.ONE if the resulting value is 0 or 1
     * respectively.
     *
     * @param subtrahend
     * @return Rational value
     */
    public Rational subtract​(double subtrahend) {
        Rational tempR4 = Rational.valueOf(subtrahend);

        return this.subtract(tempR4);
    }

    /**
     * Computes the value of this Rational multiplied by the given other
     * Rational. Returns Rational.ZERO or Rational.ONE if the resulting value is
     * 0 or 1 respectively.
     *
     * @param val
     * @return Rational value - this * val
     */
    public Rational multiply​(Rational val) {
        return new Rational(this.num.multiply(val.num), this.den.multiply(val.den));
    }

    /**
     * Computes the value of this Rational multiplied by the given number.
     * Returns Rational.ZERO or Rational.ONE if the resulting value is 0 or 1
     * respectively.
     *
     * @param val - a number to multiply by
     * @return Rational value - this * val
     */
    public Rational multiply​(double val) {
        Rational tempR5 = Rational.valueOf(val);//put the double val to rational tempR5
        return this.multiply(tempR5);//calling the multiply method previouly created 
        //which gives a rational value 
    }

    /**
     * Returns a Rational that is the absolute value of this Rational. Returns
     * Rational.ZERO or Rational.ONE if the resulting value is 0 or 1
     * respectively.
     *
     * @return |this|
     */
    public Rational abs() {
        BigInteger tN;
        BigInteger tD;
        Rational tonyR;
        tN = this.num.abs();
        tD = this.den.abs();
        tonyR = new Rational(tN, tD);

        return tonyR;
    }

    public double doubleValue() {

        double numN = this.num.doubleValue();
        double numD = this.den.doubleValue();
        double d = (double) numN / numD;

        return d;
    }
/**
 * Converts this Rational value to a float.
 * If the value has too great a magnitude to be represented as a float,
 * it will be converted to Float.NEGATIVE_INFINITY or
 * Float.POSITIVE_INFINITY as appropriate.
 * his is a narrowing conversion and may result in loss of precision, 
 * even if the result is finite.
 * @return float value of a rational
 */
    public float floatValue() {

        float tempN6 = this.num.floatValue();
        float tempD6 = this.den.floatValue();
        float f = (float) tempN6 / tempD6;
        return f;
    }
    /**
     * Converts this Rational value to an int. 
     * This is a narrowing conversion and any decimal fractional 
     * part will be lost (rounded down).
     * ArithmeticException - if the integral magnitude of this value is too 
     * great to be represented by an int
     * @return approximately equivalent integral value
     */
public int intValue(){
    int a = this.num.intValue();
    int b = this.den.intValue();
    int c = (Integer) a/b;
    if(c > 2147483647){
        throw new ArithmeticException("The value is too large to be representef by integer");
    }
   return c;
}
public long longValue(){
        long numL = this.num.longValue();
        long denL = this.num.longValue();
        long resL = (Long) numL/denL;
    
    
    return resL;
    
}
}
