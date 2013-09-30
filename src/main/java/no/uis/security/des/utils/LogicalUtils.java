package no.uis.security.des.utils;

import no.uis.security.des.service.exceptions.IllegalMethodParameterException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogicalUtils {
    public static final String HEX_16_DIGITS_PATTERN = "^[0-9A-Fa-f]{16}$";
    public static final String HEX_PATTERN = "^[0-9A-Fa-f]+$";

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


    public static String convertBytesToStringDecimal(byte[] bytes) {
        String s = "";

        for (int i = 0; i < bytes.length; i++) {
            s += bytes[i] + " ";
        }

        return s;
    }

    public static String convertBytesToStringHex(byte[] bytes) {
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

    public static String convertBytesToStringBits(byte[] bytes) {
        String s = "";


        for (int i = 0; i < bytes.length; i++) {
            String b = "";

            s += String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0') + " ";
        }

        return s;
    }

    public static long bytesToLong(byte[] bytes) {
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

    public static byte[] longToBytes(long l) {
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

    public static boolean[] longToBits(long l) {
        return byteArrayToBooleanArray(longToBytes(l));
    }

    public static int bytesToInt(byte[] bytes) {
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


    public static boolean[] ignoreNthBitsInByteBooleanArray(boolean[] array, Integer... nth) {
        List<Integer> nthl = Arrays.asList(nth);
        List<Boolean> result = new ArrayList<Boolean>();
        for (int i = 0; i < array.length; i++) {
            int intByteIndex = 7 - i % 8;
            if (!nthl.contains(intByteIndex)) {
                result.add(array[i]);
            }
        }
        boolean[] resultPrimitive = new boolean[result.size()];
        int i = 0;
        for (Boolean aBoolean : result) {
            resultPrimitive[i++] = aBoolean;
        }
        return resultPrimitive;

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

    public static boolean[] shiftArrayToLeft(final boolean... arr) {
        boolean[] booleans = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int shiftedIndex = (i + 1) % (arr.length);
            booleans[i] = arr[shiftedIndex];
        }
        return booleans;
    }

    public static boolean[] shiftArrayToRight(final boolean... arr) {
        boolean[] booleans = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int shiftedIndex = (i + 1) % (arr.length);
            booleans[shiftedIndex] = arr[i];
        }
        return booleans;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
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

    public static String convertBytesToString(byte[] code) {
        try {
            return new String(code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new String(code);
        }
    }

    public static boolean stringMatches16DigitsHex(String text) {
        return text.matches(HEX_16_DIGITS_PATTERN);
    }

    public static boolean stringMatchesHex(String text) {
        return text.matches(HEX_PATTERN);
    }
}
