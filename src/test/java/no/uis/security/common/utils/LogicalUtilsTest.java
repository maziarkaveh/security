package no.uis.security.common.utils;

import no.uis.security.rsa.service.impl.BooleanArrayLinearRandomGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;


public class LogicalUtilsTest {
    @Test
    public void testExclusiveOr() throws Exception {
        byte[] p1 = {0x6, 0x0, 0xf};
        byte[] p2 = {0x6, 0x8, 0x7};

        byte[] result = LogicalUtils.exclusiveOr(p1, p2);
        byte[] expected = {0x0, 0x8, 0x8};
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void testGetNthBit() {
        Assert.assertEquals(false, LogicalUtils.getNthBit(434224567754443l, 8));
        Assert.assertEquals(true, LogicalUtils.getNthBit(434224567754443l, 9));
        Assert.assertEquals(true, LogicalUtils.getNthBit(434224567754443l, 10));
        Assert.assertEquals(false, LogicalUtils.getNthBit(434224567754443l, 46));
        Assert.assertEquals(true, LogicalUtils.getNthBit(434224567754443l, 47));
        Assert.assertEquals(true, LogicalUtils.getNthBit(345, 6));
        Assert.assertEquals(false, LogicalUtils.getNthBit(345, 7));
        Assert.assertEquals(true, LogicalUtils.getNthBit(345, 8));
        Assert.assertEquals(true, LogicalUtils.getNthBit(new byte[]{0x60, 0x51}, 0));
        Assert.assertEquals(false, LogicalUtils.getNthBit(new byte[]{0x60, 0x51}, 5));
        Assert.assertEquals(true, LogicalUtils.getNthBit(new byte[]{0x60, 0x51}, 6));
        Assert.assertEquals(false, LogicalUtils.getNthBit(new byte[]{0x60, 0x51}, 7));
    }


    @Test
    public void convertBytesToStringBytes() {
        Assert.assertEquals("22 34 12 ", LogicalUtils.byteArrayEachByteToOneStringDecimal(new byte[]{22, 34, 12}));
    }

    @Test
    public void convertBytesToStringBits() {
        Assert.assertEquals("00000110 ", LogicalUtils.byteArrayToStringBits(new byte[]{6}));
        Assert.assertEquals("00000110 00011111 ", LogicalUtils.byteArrayToStringBits(new byte[]{6, 31}));
    }

    @Test
    public void bytesToLong() {
        Assert.assertEquals(6, LogicalUtils.byteArrayToLong(new byte[]{6}));
        Assert.assertEquals(1567, LogicalUtils.byteArrayToLong(new byte[]{6, 31}));
        Assert.assertEquals(24657, LogicalUtils.byteArrayToLong(new byte[]{0x60, 0x51}));

    }

    @Test
    public void longToBytes() {
        Assert.assertArrayEquals(new byte[]{6}, LogicalUtils.longToByteArray(6));
        Assert.assertArrayEquals(new byte[]{6, 0x1f}, LogicalUtils.longToByteArray(1567));
        Assert.assertArrayEquals(new byte[]{0x60, 0x51}, LogicalUtils.longToByteArray(24657));

    }

    @Test
    public void bytesToInt() {
        Assert.assertEquals(6, LogicalUtils.byteArrayToInt(new byte[]{6}));
        Assert.assertEquals(1567, LogicalUtils.byteArrayToInt(new byte[]{6, 31}));
        Assert.assertEquals(24657, LogicalUtils.byteArrayToInt(new byte[]{0x60, 0x51}));

    }

    @Test
    public void setBitToNthBytes() {
        Assert.assertArrayEquals(new byte[]{7}, LogicalUtils.setBitToNthBytes(new byte[]{6}, 0, true));
        Assert.assertArrayEquals(new byte[]{0x16, 0x1f}, LogicalUtils.setBitToNthBytes(new byte[]{6, 0x1f}, 12, true));
        Assert.assertArrayEquals(new byte[]{0x60, 0x11}, LogicalUtils.setBitToNthBytes(new byte[]{0x60, 0x51}, 6, false));

    }

    @Test
    public void ignoreNthBitsInByteArray() {
        byte[] bytes = {0b00100001, 0b01010001, 0b00011110};
        byte[] expected1 = {0b00000100, 0b01010000, (byte) 0b10001110};
        byte[] expected2 = {0b00001000, 0b01000111};
        byte[] actuals1 = LogicalUtils.ignoreNthBitsInByteArray(bytes, 4);
        Assert.assertArrayEquals(expected1, actuals1);
        Assert.assertArrayEquals(new byte[]{0b00100001, 0b01010001, 0b00011110}, bytes);
        Assert.assertArrayEquals(expected2, LogicalUtils.ignoreNthBitsInByteArray(bytes, 0, 1, 6, 7));


    }

    @Test
    public void byteArrayToBooleanArray() {
        byte[] bytes = {0b00100001, 0b01010001};
        boolean[] expected1 = {false, false, true, false, false, false, false, true, false, true, false, true, false, false, false, true};
        boolean[] booleans = LogicalUtils.byteArrayToBooleanArray(bytes);
        Assert.assertEquals(expected1.length, booleans.length);
        assertArrayEquals(expected1, booleans);

    }

    @Test
    public void booleanArrayToByteArray() {
        byte[] expected1 = {0b00100001, 0b01010001};
        boolean[] booleans = {true, false, false, false, false, true, false, true, false, true, false, false, false, true};
        byte[] bytes = LogicalUtils.booleanArrayToByteArray(booleans);
        Assert.assertArrayEquals(expected1, bytes);
        boolean[] booleans2 = {false, false, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false};
        byte[] expected2 = {0b00000100, 0b01010000, (byte) 0b10001110};
        byte[] bytes2 = LogicalUtils.booleanArrayToByteArray(booleans2);

        Assert.assertArrayEquals(expected2, bytes2);


    }

    @Test
    public void circularShiftArrayToLeft() {
        boolean[] in = {false, true, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false};
        boolean[] expected1 = {true, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false, false};
        boolean[] booleans = LogicalUtils.circularShiftArrayToLeft(in);
        Assert.assertEquals(expected1.length, booleans.length);
        assertArrayEquals(expected1, booleans);

    }

    @Test
    public void circularShiftArrayToRight() {
        boolean[] in = {false, false, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false};
        boolean[] expected1 = {false, false, false, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true};
        boolean[] booleans = LogicalUtils.circularShiftArrayToRight(in);
        Assert.assertEquals(expected1.length, booleans.length);
        assertArrayEquals(expected1, booleans);

    }

    @Test
    public void shiftArrayToLeft() {
        boolean[] in = {true, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false};
        boolean[] expected1 = {true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false, false};
        boolean[] booleans = LogicalUtils.shiftLeft(in);
        Assert.assertEquals(expected1.length, booleans.length);
        assertArrayEquals(expected1, booleans);

    }

    @Test
    public void shiftArrayToRight() {
        boolean[] in = {false, false, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false};
        boolean[] expected1 = {false, false, false, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true};
        boolean[] booleans = LogicalUtils.shiftRight(in);
        Assert.assertEquals(expected1.length, booleans.length);
        assertArrayEquals(expected1, booleans);

    }


    @Test
    public void hexStringToByteArray() {
        byte[] expected = {0x15, 0x71, (byte) 0xc9, 0x47, (byte) 0xd9, (byte) 0xe8, 0x59};
        Assert.assertArrayEquals(expected, LogicalUtils.hexStringToByteArray("1571c947d9e859"));

    }

    @Test
    public void hexStringToIntegerArray() {
        int[] expected = {0x0f1571c9, 0x47d9e859};

        int[] actuals = LogicalUtils.hexStringToIntegerArray("0f1571c947d9e859");
        Assert.assertArrayEquals(expected, actuals);

        expected = new int[]{0xf15, 0x71c947d9, 0xe8590f15, 0x71c947d9, 0xe8592423, 0x76482364};
        actuals = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482364");
        Assert.assertArrayEquals(expected, actuals);

    }

    @Test
    public void hexStringToBooleanArray() {
        byte[] expected = {0x0f, 0x15, 0x71, (byte) 0xc9, 0x47, (byte) 0xd9, (byte) 0xe8, 0x59};
        assertArrayEquals(LogicalUtils.byteArrayToBooleanArray(expected), LogicalUtils.hexStringToBooleanArray("0f1571c947d9e859"));
    }

    @Test
    public void modInverseOfTwoBooleanArrays() {
        BigInteger bigInteger = BigInteger.probablePrime(123, new SecureRandom());


        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("4e7452b6755a969c5d8eafe01506395");
        boolean[] n2 = LogicalUtils.hexStringToBooleanArray("1c7469da219f1c8bab429a489068245e2f069");
        boolean[] expected = LogicalUtils.hexStringToBooleanArray("1252a4adfab148d536cd04b5b87ff740bfdf6");
        boolean[] actuals = LogicalUtils.modInverseOfTwoPrimeBooleanArrays(n1, n2);
        assertArrayEquals(expected, actuals);
    }

    @Test
    public void addOfTwoBooleanArrays() {

        boolean[] i1 = {true, true, false, true, false};
        boolean[] i2 = {true, true, false, true};
        boolean[] e = {true, false, false, true, true, true};
        assertArrayEquals(e, LogicalUtils.addOfTwoBooleanArrays(i1, i2));

        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        boolean[] n2 = LogicalUtils.hexStringToBooleanArray("4eff8faeeb0e7fa06dbf8faeeb0e7fa01ec0");
        boolean[] expected = LogicalUtils.hexStringToBooleanArray("4eff9ec45cd7c77a56189ec45cd7c77a0719");
        boolean[] actuals = LogicalUtils.addOfTwoBooleanArrays(n1, n2);
        assertArrayEquals(expected, actuals);


    }

    @Test
    public void modPowOfTwoBooleanArrays() {
        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        boolean[] n2 = LogicalUtils.hexStringToBooleanArray("23094028340980328409fbbbbbdd4324");
        boolean[] n3 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859242376482364");
        boolean[] expected = LogicalUtils.hexStringToBooleanArray("9bd4f818bc5d96e23e3a9636c9df50d570bc69cbd1");
        boolean[] actuals = LogicalUtils.modPowOfTwoBooleanArrays(n1, n2, n3);
        assertArrayEquals(expected, actuals);
        n1 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        n2 = LogicalUtils.hexStringToBooleanArray("1");
        n3 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859242376482364");
        expected = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        actuals = LogicalUtils.modPowOfTwoBooleanArrays(n1, n2, n3);
        assertArrayEquals(expected, actuals);

        n1 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        n2 = LogicalUtils.ZERO;
        n3 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859242376482364");
        expected = LogicalUtils.ONE;
        actuals = LogicalUtils.modPowOfTwoBooleanArrays(n1, n2, n3);
        assertArrayEquals(expected, actuals);

    }

    @Test
    public void modOfTwoBooleanArrays() {

        boolean[] i1 = {true, true, false, true, true};
        boolean[] i2 = {true, true, false};
        boolean[] e = {true, true};
        assertArrayEquals(e, LogicalUtils.modOfTwoBooleanArrays(i1, i2));

        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859242376482364");
        boolean[] n2 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        boolean[] expected = LogicalUtils.hexStringToBooleanArray("242376482364");
        boolean[] actuals = LogicalUtils.modOfTwoBooleanArrays(n1, n2);
        assertArrayEquals(expected, actuals);
    }

//    @Test
//    public void modOfTwoIntegerArrays() {
//        int[] n1 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482364f1571c947d9e8590f1571c947d9e859242376482364");
//        int[] n2 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e8592423764823648");
//        System.out.println(  new  BigInteger("f1571c947d9e8590f1571c947d9e859242376482364f1571c947d9e8590f1571c947d9e859242376482364",16).mod(new BigInteger("f1571c947d9e8590f1571c947d9e8592423764823648",16)).toString(16));
//        int[] expected = LogicalUtils.hexStringToIntegerArray("71571c947d9e8590f1571c947d9e859242376482364");
//        int[] actuals = LogicalUtils.modOfTwoIntegerArrays(n1, n2);
//        Assert.assertArrayEquals(expected, actuals);
//    }
//
//    @Test
//    public void divOfTwoIntegerArrays() {
//
//        int[] n1 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482364");
//        int[] n2 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859");
//        int[] expected = LogicalUtils.hexStringToIntegerArray("1000000000000");
//        int[] actuals = LogicalUtils.divOfTwoIntegerArrays(n1, n2);
//        Assert.assertArrayEquals(expected, actuals);
//    }

    @Test
    public void shiftIntArrayOneBitRight() {

        int[] n1 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482364");
        int[] expected = LogicalUtils.hexStringToIntegerArray("78ab8e4a3ecf42c878ab8e4a3ecf42c9211bb2411b2");
        int[] actuals = LogicalUtils.shiftIntegerArrayOneBitRight(n1);
        Assert.assertArrayEquals(expected, actuals);
    }

    @Test
    public void shiftIntArrayOneBitLeft() {

        int[] n1 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482364");
        int[] expected = LogicalUtils.hexStringToIntegerArray("1e2ae3928fb3d0b21e2ae3928fb3d0b24846ec9046c8");


        int[] actuals = LogicalUtils.shiftIntegerArrayOneBitLeft(n1);
        Assert.assertArrayEquals(expected, actuals);
    }

    @Test
    public void compareTwoBooleanArrays() {

        boolean[] i1 = {true, true, false, true, true};
        boolean[] i2 = {true, true, false};
        Assert.assertEquals(1, LogicalUtils.compareTwoBooleanArrays(i1, i2));
        Assert.assertEquals(-1, LogicalUtils.compareTwoBooleanArrays(i2, i1));
        Assert.assertEquals(0, LogicalUtils.compareTwoBooleanArrays(i1, i1));

        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859242376482364");
        boolean[] n2 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859242376482363");
        boolean[] n3 = LogicalUtils.hexStringToBooleanArray("47d9e859242376482363");
        Assert.assertEquals(1, LogicalUtils.compareTwoBooleanArrays(n1, n2));
        Assert.assertEquals(1, LogicalUtils.compareTwoBooleanArrays(n1, n3));
        Assert.assertEquals(1, LogicalUtils.compareTwoBooleanArrays(n2, n3));
        Assert.assertEquals(0, LogicalUtils.compareTwoBooleanArrays(n1, n1));
        Assert.assertEquals(-1, LogicalUtils.compareTwoBooleanArrays(n3, n2));
        Assert.assertEquals(-1, LogicalUtils.compareTwoBooleanArrays(n2, n1));
    }

    @Test
    public void compareTwoIntegerArrays() {


        int[] n1 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482364");
        int[] n2 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482363");
        int[] n3 = LogicalUtils.hexStringToIntegerArray("47d9e859242376482363");
        int[] n4 = LogicalUtils.hexStringToIntegerArray("ff000000");
        int[] n5 = LogicalUtils.hexStringToIntegerArray("00111111");
        int[] n6 = LogicalUtils.hexStringToIntegerArray("ff000000");
        Assert.assertEquals(1, LogicalUtils.compareTwoIntegerArrays(n4, n5));
        Assert.assertEquals(-1, LogicalUtils.compareTwoIntegerArrays(n5, n6));
        Assert.assertEquals(0, LogicalUtils.compareTwoIntegerArrays(n4, n6));
        Assert.assertEquals(1, LogicalUtils.compareTwoIntegerArrays(n1, n2));
        Assert.assertEquals(1, LogicalUtils.compareTwoIntegerArrays(n1, n3));
        Assert.assertEquals(1, LogicalUtils.compareTwoIntegerArrays(n2, n3));
        Assert.assertEquals(0, LogicalUtils.compareTwoIntegerArrays(n1, n1));
        Assert.assertEquals(-1, LogicalUtils.compareTwoIntegerArrays(n3, n2));
        Assert.assertEquals(-1, LogicalUtils.compareTwoIntegerArrays(n2, n1));


    }

    @Test
    public void divideOfTwoBooleanArrays() {

        boolean[] i1 = {true, true, false, true, false, true};
        boolean[] i2 = {true, false, true};
        boolean[] e = {true, false, true, false};
        assertArrayEquals(e, LogicalUtils.divideOfTwoBooleanArrays(i1, i2));

        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859242376482364");
        boolean[] n2 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        boolean[] expected = LogicalUtils.hexStringToBooleanArray("1000000000000");
        boolean[] actuals = LogicalUtils.divideOfTwoBooleanArrays(n1, n2);
        assertArrayEquals(expected, actuals);
    }

    @Test
    public void multiplyOfTwoBooleanArrays() {
        boolean[] i1 = {true, true, false, true, false};
        boolean[] i2 = {true, true, false, true};
        boolean[] e = {false, true, false, true, false, true, false, false, true, false};
        assertArrayEquals(e, LogicalUtils.multiplyOfTwoBooleanArrays(i2, i1));

        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("88BBE");
        boolean[] n2 = LogicalUtils.hexStringToBooleanArray("99BB");
        boolean[] expected = LogicalUtils.hexStringToBooleanArray("521C2A1CA");
        boolean[] actuals = LogicalUtils.multiplyOfTwoBooleanArrays(n1, n2);
        assertArrayEquals(expected, actuals);

        n1 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        n2 = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859242376482364");
        expected = LogicalUtils.hexStringToBooleanArray("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4");
        actuals = LogicalUtils.multiplyOfTwoBooleanArrays(n1, n2);

        assertArrayEquals(expected, actuals);
    }

    @Test
    public void multiplyOfTwoIntegerArrays() {


        int[] n1 = LogicalUtils.hexStringToIntegerArray("88BBE");
        int[] n2 = LogicalUtils.hexStringToIntegerArray("99BB");
        int[] expected = LogicalUtils.hexStringToIntegerArray("521C2A1CA");
        int[] actuals = LogicalUtils.multiplyOfTwoIntegerArrays(n1, n2);

        Assert.assertArrayEquals(expected, actuals);

        n1 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859");
        n2 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482364");
        expected = LogicalUtils.hexStringToIntegerArray("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4");
        actuals = LogicalUtils.multiplyOfTwoIntegerArrays(n1, n2);

        Assert.assertArrayEquals(expected, actuals);
        n1 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859242376482364");
        n2 = LogicalUtils.INTEGER_TWO;

        expected = LogicalUtils.hexStringToIntegerArray("1e2ae3928fb3d0b21e2ae3928fb3d0b24846ec9046c8");


        actuals = LogicalUtils.multiplyOfTwoIntegerArrays(n1, n2);
        Assert.assertArrayEquals(expected, LogicalUtils.removeLeftZerosFromIntArray(actuals));
    }

    @Test
    public void multiplySpeedTest() {


        int[] intArray = LogicalUtils.hexStringToIntegerArray("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4");
        boolean[] booleanArray = LogicalUtils.hexStringToBooleanArray("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4");
        BigInteger bigInt = new BigInteger("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4", 16);

        long l = System.currentTimeMillis();
        bigInt = bigInt.multiply(bigInt);
//        System.out.println("big   " + (System.currentTimeMillis() - l));
        l = System.currentTimeMillis();
        booleanArray = LogicalUtils.multiplyOfTwoBooleanArrays(booleanArray, booleanArray);
//        System.out.println("booleanArray   " + (System.currentTimeMillis() - l));

        l = System.currentTimeMillis();
        intArray = LogicalUtils.multiplyOfTwoIntegerArrays(intArray, intArray);
//        System.out.println("intArray int " + (System.currentTimeMillis() - l));
    }

    @Test
    public void addOfTwoIntegerArrays() {
        int[] n1 = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859");
        int[] n2 = LogicalUtils.hexStringToIntegerArray("4eff8faeeb0e7fa06dbf8faeeb0e7fa01ec0");
        int[] expected = LogicalUtils.hexStringToIntegerArray("4eff9ec45cd7c77a56189ec45cd7c77a0719");

        Assert.assertArrayEquals(expected, LogicalUtils.addOfTwoIntegerArrays(n1, n2));
    }

    @Test

    public void powOfBooleanArray() {

        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("88BBE");
        boolean[] expected = LogicalUtils.hexStringToBooleanArray("84434d6c795cf6e8a2c595181ed111d5bc63712dcbee36882e247d8e988a423fd005f97026c24bd15748fc0c61c0fb42c9504241e47d6831840eac80cc4a51fa070e04ab07187040100000000");
        boolean[] actuals = LogicalUtils.powOfBooleanArray(n1, 32);
        assertArrayEquals(expected, actuals);


    }

    @Test
    public void subtractOfTwoBooleanArrays() {
        boolean[] i1 = {true, false, false, true, true, true};
        boolean[] i2 = {true, true, false, true};
        boolean[] e = {true, true, false, true, false};
        assertArrayEquals(e, LogicalUtils.subtractOfTwoBooleanArrays(i1, i2));

        boolean[] n1 = LogicalUtils.hexStringToBooleanArray("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4");
        boolean[] n2 = LogicalUtils.hexStringToBooleanArray("1");
        boolean[] expected = LogicalUtils.hexStringToBooleanArray("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc3");
        boolean[] actuals = LogicalUtils.subtractOfTwoBooleanArrays(n1, n2);
        assertArrayEquals(expected, actuals);


        n1 = LogicalUtils.hexStringToBooleanArray("4eff9ec45cd7c77a56189ec45cd7c77a0719");
        n2 = LogicalUtils.hexStringToBooleanArray("4eff8faeeb0e7fa06dbf8faeeb0e7fa01ec0");
        expected = LogicalUtils.hexStringToBooleanArray("f1571c947d9e8590f1571c947d9e859");
        actuals = LogicalUtils.subtractOfTwoBooleanArrays(n1, n2);
        assertArrayEquals(expected, actuals);

    }

    @Test
    public void subtractOfTwoIntegerArrays() {

        int[] n1 = LogicalUtils.hexStringToIntegerArray("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc4");
        int[] n2 = LogicalUtils.hexStringToIntegerArray("1");
        int[] expected = LogicalUtils.hexStringToIntegerArray("e385217404a2aad5d91061ffccb446ab28ac46a2bb92adbc335bf104b78111e6003aeaedc3");
        int[] actuals = LogicalUtils.subtractOfTwoIntegerArrays(n1, n2);
        Assert.assertArrayEquals(expected, actuals);


        n1 = LogicalUtils.hexStringToIntegerArray("4eff9ec45cd7c77a56189ec45cd7c77a0719");
        n2 = LogicalUtils.hexStringToIntegerArray("4eff8faeeb0e7fa06dbf8faeeb0e7fa01ec0");
        expected = LogicalUtils.hexStringToIntegerArray("f1571c947d9e8590f1571c947d9e859");
        actuals = LogicalUtils.subtractOfTwoIntegerArrays(n1, n2);
        Assert.assertArrayEquals(expected, LogicalUtils.removeLeftZerosFromIntArray(actuals));

    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual) {
        if (LogicalUtils.compareTwoBooleanArrays(expected, actual) != 0) {
            Assert.fail();
        }
    }

    @Test
    public void longToBooleanArray() {
        long l = 2346782365834658l;
        String s = Long.toHexString(l);
        boolean[] expected = LogicalUtils.hexStringToBooleanArray(s);
        boolean[] actual = LogicalUtils.longToBooleanArray(l);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void intToBooleanArray() {
        int l = 324234;
        String s = Integer.toHexString(l);
        boolean[] expected = LogicalUtils.hexStringToBooleanArray(s);
        boolean[] actual = LogicalUtils.intToBooleanArray(l);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void probablePrime() {
        for (int i = 0; i < 5; i++) {

            long l = System.currentTimeMillis();
            boolean[] prime = LogicalUtils.probablePrime(10, new BooleanArrayLinearRandomGenerator());
//            System.out.println(System.currentTimeMillis() - l);
            java.math.BigInteger bigInteger = new java.math.BigInteger(LogicalUtils.booleanArrayToStringHex(prime), 16);
            Assert.assertTrue(bigInteger.isProbablePrime(2));
        }
    }


    @Test
    public void isPrime() {
        for (int i = 0; i < 61; i++) {
            BigInteger bigInteger = BigInteger.probablePrime(20, new SecureRandom());
            boolean[] n1 = LogicalUtils.hexStringToBooleanArray(bigInteger.toString(16));

            boolean probablePrime = bigInteger.isProbablePrime(10);
            boolean prime = LogicalUtils.isPrime(n1, 10, new BooleanArrayLinearRandomGenerator());
            Assert.assertTrue(!(prime ^ probablePrime));
        }
        for (int i = 0; i < 61; i++) {
            java.math.BigInteger bigInteger = new java.math.BigInteger(20, new SecureRandom());
            boolean[] n1 = LogicalUtils.hexStringToBooleanArray(bigInteger.toString(16));

            boolean probablePrime = bigInteger.isProbablePrime(10);
            boolean prime = LogicalUtils.isPrime(n1, 10, new BooleanArrayLinearRandomGenerator());
            Assert.assertTrue(!(prime ^ probablePrime));
        }


    }

    @Test
    public void convertUnsignedIntegerToLong() {
        int n = 0xFF000000;
        long ln = 0x00FF000000L;
        Assert.assertNotEquals(ln, n);
        Assert.assertEquals(ln, LogicalUtils.convertUnsignedIntegerToLong(n));
    }


}
