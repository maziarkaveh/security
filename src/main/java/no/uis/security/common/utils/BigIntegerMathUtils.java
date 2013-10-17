package no.uis.security.common.utils;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 17.10.13
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public class BigIntegerMathUtils {
    public static BigInteger[] egcdOfTwoBigIntegers(final BigInteger num1, final BigInteger num2) {
         BigInteger[] ans = new BigInteger[3];
        BigInteger q, a = num1, b = num2;

        if (b.compareTo(BigInteger.ZERO) == 0) {
            ans[0] = a;
            ans[1] = BigInteger.ONE;
            ans[2] = BigInteger.ZERO;
        } else {
            q = a.divide(b);
            ans = egcdOfTwoBigIntegers(b, a.mod(b));
            BigInteger temp = ans[1].subtract(ans[2].multiply(q));
            ans[1] = ans[2];
            ans[2] = temp;
        }

        return ans;

    }


    public static BigInteger modInverseOfTwoPrimeBooleanArrays(final BigInteger num, final BigInteger modulus) {

        BigInteger egdc = egcdOfTwoBigIntegers(num, modulus)[1];
        return egdc.add(modulus).mod(modulus);
    }

}
