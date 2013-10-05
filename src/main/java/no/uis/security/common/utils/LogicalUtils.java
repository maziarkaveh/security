package no.uis.security.common.utils;

import no.uis.security.des.service.exceptions.IllegalMethodParameterException;
import no.uis.security.rsa.model.UnsignedBigNumber;
import no.uis.security.rsa.service.RandomGenerator;
import org.apache.commons.lang.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LogicalUtils {
    public static final String HEX_16_DIGITS_PATTERN = "^[0-9A-Fa-f]{16}$";
    public static final String HEX_PATTERN = "^[0-9A-Fa-f]+$";
    public static final boolean[] ONE = {true};
    public static final boolean[] ZERO = {false};
    public static final boolean[] TWO = {true, false};

    public static byte[] exclusiveOr(byte[] p1, byte[] p2) {
        if (p1.length != p2.length) {
            throw new IllegalMethodParameterException("p1 and p2 do not have same length");
        }
        byte[] r = new byte[p1.length];

        for (int i = 0; i < p1.length; i++) {
            r[i] = (byte) (p1[i] ^ p2[i]);
        }
        return r;
    }


    public static String byteArrayEachByteToOneStringDecimal(byte[] bytes) {
        StringBuffer desString = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            desString.append(bytes[i]).append(" ");
        }

        return desString.toString();
    }

    public static String byteArrayToStringHex(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String byteArrayToStringBits(byte[] bytes) {
        StringBuffer s = new StringBuffer();


        for (int i = 0; i < bytes.length; i++) {

            s.append(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0') + " ");
        }

        return s.toString();
    }

    public static long byteArrayToLong(byte[] bytes) {
        if (bytes.length > 8) {
            throw new IllegalMethodParameterException("byte should not be more than 8 bytes");

        }
        long r = 0;
        for (int i = 0; i < bytes.length; i++) {
            r = r << 8;
            r += bytes[i];
        }

        return r;
    }

    public static byte[] longToByteArray(long l) {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        while (l != 0) {
            bytes.add((byte) (l % (0xff + 1)));
            l = l >> 8;
        }
        byte[] bytesp = new byte[bytes.size()];
        for (int i = bytes.size() - 1, j = 0; i >= 0; i--, j++) {
            bytesp[j] = bytes.get(i);
        }
        return bytesp;
    }

    public static boolean[] longToBooleanArray(long l) {
        boolean[] result = new boolean[64];
        for (int i = 0; i < 64; i++) {
            result[63 - i] = (l & (1l << (i))) != 0;
        }
        return result;
    }

    public static boolean[] intToBooleanArray(long number) {
        final boolean[] ret = new boolean[32];
        for (int i = 0; i < 32; i++) {
            ret[32 - 1 - i] = (1 << i & number) != 0;
        }
        return ret;
    }

    public static int byteArrayToInt(byte[] bytes) {
        if (bytes.length > 4) {
            throw new IllegalMethodParameterException("byte should not be more than 4 bytes");

        }
        int r = 0;
        for (int i = 0; i < bytes.length; i++) {
            r = r << 8;
            r += bytes[i];
        }

        return r;
    }


    public static boolean[] byteArrayToBooleanArray(byte... bytes) {
        int bitsSize = bytes.length * 8;
        boolean[] result = new boolean[bitsSize];
        for (int i = 0, j = 0; i < bitsSize; i++, j++) {
            int intBytesIndex = i / 8;
            int intByteIndex = 7 - i % 8;
            result[j] = (bytes[intBytesIndex] & 1 << intByteIndex) != 0;

        }
        return result;

    }

    public static String booleanArrayToStringHex(final boolean[] value) {
        return byteArrayToStringHex(booleanArrayToByteArray(value));
    }

    public static byte[] booleanArrayToByteArray(boolean... booleans) {
        int bytesSize = (int) Math.ceil(booleans.length / 8f);
        byte[] result = new byte[bytesSize];
        for (int i = booleans.length - 1; i >= 0; i--) {
            int intBytesIndex = bytesSize - 1 - i / 8;
            int intByteIndex = i % 8;
            if (booleans[booleans.length - i - 1]) {
                result[intBytesIndex] = (byte) (result[intBytesIndex] | (1 << intByteIndex));
            }
        }
        return result;
    }


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        if (len % 2 == 1) {
            s = "0" + s;
            len++;
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


    public static String byteArrayToString(byte[] code) {
        try {
            return new String(code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new String(code);
        }
    }

    public static boolean[] circularShiftArrayToLeft(final boolean... arr) {
        boolean[] booleans = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int shiftedIndex = (i + 1) % (arr.length);
            booleans[i] = arr[shiftedIndex];
        }
        return booleans;
    }

    public static boolean[] circularShiftArrayToRight(final boolean... arr) {
        boolean[] booleans = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int shiftedIndex = (i + 1) % (arr.length);
            booleans[shiftedIndex] = arr[i];
        }
        return booleans;
    }

    public static boolean[] modInverseOfTwoPrimeBooleanArrays(final boolean[] num, final boolean[] modulus) {
        return modPowOfTwoBooleanArrays(num.clone(), subtractOfTwoBooleanArrays(modulus.clone(), TWO), modulus.clone());
    }


    public static boolean[] modPowOfTwoBooleanArrays(final boolean[] base, final boolean[] exponent, final boolean[] modulus) {
        boolean[] b = base.clone();
        boolean[] e = exponent.clone();
        boolean[] m = modulus.clone();

        boolean[] result = ONE.clone();
        while (compareTwoBooleanArrays(e, ZERO) == 1) {
            if (isOddArrayBoolean(e)) {
                result = modOfTwoBooleanArrays(multiplyOfTwoBooleanArrays(b, result), m);
            }
            e = ArrayUtils.subarray(e, 0, e.length - 1);
            b = modOfTwoBooleanArrays(multiplyOfTwoBooleanArrays(b, b), m);
        }
        return result;
    }

    public static boolean isOddArrayBoolean(final boolean[] exponent) {
        return exponent[exponent.length - 1];
    }

    public static boolean[] modOfTwoBooleanArrays(final boolean[] dividend, final boolean[] divisor) {
        boolean[] dv = removeLeftFalsesFromBooleanArray(dividend);
        boolean[] dr = removeLeftFalsesFromBooleanArray(divisor);
        if (compareTwoBooleanArrays(dv, dr) == -1) {
            return dv;
        }
        if (compareTwoBooleanArrays(dv, dr) == 0) {
            return ONE;
        }
        boolean[] quotient = new boolean[dividend.length];
        boolean[] reminder = ArrayUtils.subarray(dv, 0, dr.length - 1);
        int j = 0;
        for (int i = dr.length - 1; i < dv.length; i++) {
            reminder = ArrayUtils.add(reminder, dv[i]);
            if (compareTwoBooleanArrays(reminder, dr) == -1) {
                quotient[j++] = false;
                continue;
            }
            quotient[j++] = true;
            reminder = subtractOfTwoBooleanArrays(reminder, dr);

        }
        return removeLeftFalsesFromBooleanArray(reminder);
    }

    public static boolean[] multiplyOfTwoBooleanArrays(final boolean[] num1, final boolean[] num2) {

        boolean[] minArray = compareTwoBooleanArrays(num1, num2) != 1 ? num1 : num2;
        boolean[] maxArray = compareTwoBooleanArrays(num1, num2) == 1 ? num1 : num2;
        boolean[] result = ZERO;
        for (int i = 0; i < minArray.length; i++) {
            if (minArray[minArray.length - i - 1]) {
                boolean[] num21 = ArrayUtils.addAll(maxArray, new boolean[i]);
                result = addOfTwoBooleanArrays(result, num21);
            }
        }
        if (result.length % 2 == 0) {
            return result;
        }
        return ArrayUtils.addAll(ZERO, result);
    }

    public static boolean[] subtractOfTwoBooleanArrays(final boolean[] num1, final boolean[] num2) {
        boolean[] n1 = num1.clone();
        boolean[] n2 = num2.clone();

        if (n1.length > n2.length) {
            n2 = ArrayUtils.addAll(new boolean[n1.length - n2.length], n2);
        }
        n2 = complementBooleanArray(n2);
        boolean[] add = addOfTwoBooleanArrays(n1, n2);

        boolean[] sub = addOfTwoBooleanArrays(add, ONE);

        return ArrayUtils.subarray(sub, 1, sub.length);
    }

    public static boolean[] complementBooleanArray(final boolean[] num) {
        boolean[] result = new boolean[num.length];
        for (int i = 0; i < num.length; i++) {
            result[i] = !num[i];
        }
        return result;
    }

    public static boolean[] addOfTwoBooleanArrays(final boolean[] num1, final boolean[] num2) {
        int n1L = num1.length;
        int n2L = num2.length;
        int maxLength = Math.max(n1L, n2L);
        boolean[] result = new boolean[maxLength + 1];
        int carry = 0;
        for (int i = maxLength; i > 0; i--) {
            int n1 = --n1L >= 0 ? num1[n1L] ? 1 : 0 : 0;
            int n2 = --n2L >= 0 ? num2[n2L] ? 1 : 0 : 0;
            int sum = carry + n1 + n2;
            result[i] = sum % 2 == 1;
            carry = sum / 2;
        }
        if (carry == 1) {
            result[0] = true;
        } else {
            result = ArrayUtils.subarray(result, 1, result.length);
        }
        return result;
    }

    public static byte[] checkIfByteArrayLengthIsLessThanExpected(byte[] plain, int length) {
        if (plain.length == length) {
            return plain;
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < plain.length; i++) {
            bytes[i] = plain[i];
        }
        return bytes;
    }

    public static boolean stringMatches16DigitsHex(String text) {
        return text.matches(HEX_16_DIGITS_PATTERN);
    }

    public static boolean stringMatchesHex(String text) {
        return text.matches(HEX_PATTERN);
    }

    public static boolean getNthBit(byte[] bytes, int index) {
        if (bytes.length * 8 < index) {
            throw new IllegalMethodParameterException();
        }
        if (bytes.length * 8 < index) {
            throw new IllegalMethodParameterException();
        }
        int indexInBytes = bytes.length - 1 - (index / 8);
        int indexInByte = index % 8;
        return (bytes[indexInBytes] & (1 << indexInByte)) != 0;
    }

    public static boolean getNthBit(int i, int n) {
        return getNthBit((long) i, n);
    }

    public static boolean getNthBit(long l, int n) {
        return (l >> n) % 2 == 1 ? true : false;
    }


    public static byte[] setBitToNthBytes(final byte[] bytes, int index, boolean value) {
        if (bytes.length * 8 < index) {
            throw new IllegalMethodParameterException();
        }
        int indexInBytes = bytes.length - 1 - (index / 8);
        int indexInByte = index % 8;
        byte[] clone = Arrays.copyOf(bytes, bytes.length);
        if (value) {
            clone[indexInBytes] = (byte) (clone[indexInBytes] | (1 << indexInByte));
        } else {
            clone[indexInBytes] = (byte) (clone[indexInBytes] & ~(1 << indexInByte));

        }
        return clone;
    }

    public static byte[] ignoreNthBitsInByteArray(byte[] array, Integer... nth) {
        boolean[] booleans = ignoreNthBitsInByteBooleanArray(byteArrayToBooleanArray(array), nth);
        return booleanArrayToByteArray(booleans);
    }


    public static boolean[] ignoreNthBitsInByteBooleanArray(final boolean[] array, Integer... nth) {
        List<Integer> nthl = Arrays.asList(nth);
        List<Boolean> result = new ArrayList<Boolean>();
        for (int i = 0; i < array.length; i++) {
            int intByteIndex = 7 - i % 8;
            if (!nthl.contains(intByteIndex)) {
                result.add(array[i]);
            }
        }
        final boolean[] resultPrimitive = new boolean[result.size()];
        int i = 0;
        for (Boolean aBoolean : result) {
            resultPrimitive[i++] = aBoolean;
        }
        return resultPrimitive;

    }

    public static boolean[] hexStringToBooleanArray(String value) {
        return byteArrayToBooleanArray(hexStringToByteArray(value));
    }


    public static boolean[] divideOfTwoBooleanArrays(final boolean[] dividend, final
    boolean[] divisor) {
        boolean[] dv = removeLeftFalsesFromBooleanArray(dividend);
        boolean[] dr = removeLeftFalsesFromBooleanArray(divisor);
        if (compareTwoBooleanArrays(dv, dr) == -1) {
            return ZERO;
        }
        if (compareTwoBooleanArrays(dv, dr) == 0) {
            return ONE;
        }
        boolean[] quotient = new boolean[dividend.length];
        boolean[] reminder = ArrayUtils.subarray(dv, 0, dr.length - 1);
        int j = 0;
        for (int i = dr.length - 1; i < dv.length; i++) {
            reminder = ArrayUtils.add(reminder, dv[i]);
            if (compareTwoBooleanArrays(reminder, dr) == -1) {
                quotient[j++] = false;
                continue;
            }
            quotient[j++] = true;
            reminder = subtractOfTwoBooleanArrays(reminder, dr);

        }
        return ArrayUtils.subarray(quotient, 0, j);
    }


    public static int compareTwoBooleanArrays(final boolean[] n1, final boolean[] n2) {

        boolean[] num1 = removeLeftFalsesFromBooleanArray(n1);
        boolean[] num2 = removeLeftFalsesFromBooleanArray(n2);
        if (num1.length > num2.length) {
            return 1;
        }
        if (num1.length < num2.length) {
            return -1;
        }
        for (int i = 0; i < num1.length; i++) {
            if (num1[i] == num2[i]) {
                continue;
            }
            return num1[i] ? 1 : -1;
        }
        return 0;
    }

    public static boolean[] removeLeftFalsesFromBooleanArray(final boolean[] n1) {
        if (n1.length == 0) {
            return n1;
        }
        int n1i = -1;
        while (++n1i < n1.length && !n1[n1i]) ;
        return ArrayUtils.subarray(n1, n1i, n1.length);
    }

    public static boolean[] shiftRight(final boolean[] n1) {
        if (n1.length == 0) {
            return n1;
        }

        return ArrayUtils.addAll(ZERO, ArrayUtils.subarray(n1, 0, n1.length - 1));
    }

    public static boolean[] shiftLeft(final boolean[] n1) {
        if (n1.length == 0) {
            return n1;
        }
        return ArrayUtils.add(ArrayUtils.subarray(n1, 1, n1.length), false);
    }

    public static boolean[] powOfBooleanArray(final boolean[] num, int e) {
        boolean[] result = ONE;
        boolean[] t = num.clone();
        while (e > 1) {
            if (e % 2 == 1) {
                result = multiplyOfTwoBooleanArrays(result, t);
            }
            e = (int) Math.floor(e / 2);
            t = multiplyOfTwoBooleanArrays(t, t);
        }
        result = multiplyOfTwoBooleanArrays(result, t);
        return removeLeftFalsesFromBooleanArray(result);

    }


    public static boolean[] longArrayToBooleanArray(long[] value) {
        if (value == null || value.length == 0) {
            return ZERO;
        }
        boolean[] result = longToBooleanArray(value[0]);
        for (int i = 1; i < value.length; i++) {
            result = ArrayUtils.addAll(result, longToBooleanArray(value[i]));
        }
        return result;
    }


    public static boolean isPrime(final boolean[] value, int iterations, RandomGenerator<boolean[]> rnd) {

        if (!isOddArrayBoolean(value)) {
            return false;
        }
        if (iterations < 1) {
            return true;
        }
        boolean[] n = value.clone();
        boolean[] n_1 = value.clone();
        //make n-1
        n_1[n_1.length - 1] = false;
        boolean[] q = n_1.clone();
        boolean[] a = ZERO;
        int k = 0;
        while (!q[q.length - 1]) {
            k++;
            q = shiftRight(q);
        }
        for (int i = 0; i < iterations; i++) {
            do {
                a = modOfTwoBooleanArrays(rnd.next(n.length), n_1);
            } while (compareTwoBooleanArrays(a, ONE) != 1);
            boolean[] z = modPowOfTwoBooleanArrays(a, q, n);
            int j = 0;
            while (!((j == 0 && compareTwoBooleanArrays(z, ONE) == 0) || compareTwoBooleanArrays(z, n_1) == 0)) {
                if (j > 0 && compareTwoBooleanArrays(z, ONE) == 0 || ++j == k) {
                    return false;
                }
                z = modPowOfTwoBooleanArrays(z, TWO, n);
            }
        }
        return true;
    }

    public static boolean[] probablePrime(int numBits, RandomGenerator<boolean[]> rnd) {
        for (; ; ) {
            boolean[] next = rnd.next(numBits);
            if (isPrime(next, 10, rnd)) {
                return next;
            }
        }
    }

}
