package no.uis.security.des.utils;

import org.junit.Assert;
import org.junit.Test;


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
        Assert.assertEquals("22 34 12 ", LogicalUtils.convertBytesToStringDecimal(new byte[]{22, 34, 12}));
    }

    @Test
    public void convertBytesToStringBits() {
        Assert.assertEquals("00000110 ", LogicalUtils.convertBytesToStringBits(new byte[]{6}));
        Assert.assertEquals("00000110 00011111 ", LogicalUtils.convertBytesToStringBits(new byte[]{6, 31}));
    }

    @Test
    public void bytesToLong() {
        Assert.assertEquals(6, LogicalUtils.bytesToLong(new byte[]{6}));
        Assert.assertEquals(1567, LogicalUtils.bytesToLong(new byte[]{6, 31}));
        Assert.assertEquals(24657, LogicalUtils.bytesToLong(new byte[]{0x60, 0x51}));

    }

    @Test
    public void longToBytes() {
        Assert.assertArrayEquals(new byte[]{6}, LogicalUtils.longToBytes(6));
        Assert.assertArrayEquals(new byte[]{6, 0x1f}, LogicalUtils.longToBytes(1567));
        Assert.assertArrayEquals(new byte[]{0x60, 0x51}, LogicalUtils.longToBytes(24657));

    }

    @Test
    public void bytesToInt() {
        Assert.assertEquals(6, LogicalUtils.bytesToInt(new byte[]{6}));
        Assert.assertEquals(1567, LogicalUtils.bytesToInt(new byte[]{6, 31}));
        Assert.assertEquals(24657, LogicalUtils.bytesToInt(new byte[]{0x60, 0x51}));

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
        for (int i = 0; i < expected1.length; i++) {
            Assert.assertTrue(expected1[i] == booleans[i]);
        }

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
    public void shiftArrayToLeft() {
        boolean[] in = {false, true, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false};
        boolean[] expected1 = {true, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false, false};
        boolean[] booleans = LogicalUtils.shiftArrayToLeft(in);
        Assert.assertEquals(expected1.length, booleans.length);
        for (int i = 0; i < expected1.length; i++) {
            Assert.assertTrue(expected1[i] == booleans[i]);
        }

    }

    @Test
    public void shiftArrayToRight() {
        boolean[] in = {false, false, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true, false};
        boolean[] expected1 = {false, false, false, true, false, false, false, true, false, true, false, false, false, false, true, false, false, false, true, true, true};
        boolean[] booleans = LogicalUtils.shiftArrayToRight(in);
        Assert.assertEquals(expected1.length, booleans.length);
        for (int i = 0; i < expected1.length; i++) {
            Assert.assertTrue(expected1[i] == booleans[i]);
        }

    }

    @Test
    public void hexStringToByteArray() {
        Assert.assertArrayEquals(new byte[]{0x0f, 0x15, 0x71, (byte) 0xc9, 0x47, (byte) 0xd9, (byte) 0xe8, 0x59}, LogicalUtils.hexStringToByteArray("0f1571c947d9e859"));

    }

}
