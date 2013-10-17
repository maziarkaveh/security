package no.uis.security.rsa.model;

import no.uis.security.common.utils.LogicalUtils;
import no.uis.security.des.service.exceptions.IllegalMethodParameterException;
import no.uis.security.rsa.service.RandomGenerator;
import no.uis.security.rsa.service.impl.BooleanArrayLinearRandomGenerator;

import java.util.Arrays;

import static no.uis.security.common.utils.LogicalUtils.*;

public class UnsignedBigNumber implements Cloneable {
    public static final UnsignedBigNumber ZERO = new UnsignedBigNumber(LogicalUtils.ZERO);
    public static final UnsignedBigNumber ONE = new UnsignedBigNumber(LogicalUtils.ONE);
    public static final UnsignedBigNumber TWO = new UnsignedBigNumber(LogicalUtils.TWO);
    private final boolean[] value;

    public UnsignedBigNumber(boolean[] value) {
        if (value == null || !(value.length > 0)) {
            throw new IllegalMethodParameterException();
        }
        this.value = value;
    }

    public UnsignedBigNumber(String value) {
        if (value == null || !(value.length() > 0)) {
            throw new IllegalMethodParameterException();
        }
        this.value = hexStringToBooleanArray(value);

    }

    public UnsignedBigNumber(byte[] value) {
        if (value == null || !(value.length > 0)) {
            throw new IllegalMethodParameterException();
        }
        this.value = byteArrayToBooleanArray(value);
    }

    public UnsignedBigNumber(long[] value) {
        if (value == null || !(value.length > 0)) {
            throw new IllegalMethodParameterException();
        }
        this.value = LogicalUtils.longArrayToBooleanArray(value);
    }


    public UnsignedBigNumber(int value) {

        this.value = longToBooleanArray(value);
    }

    public UnsignedBigNumber(long value) {

        this.value = longToBooleanArray(value);
    }


    public byte[] getBytes() {
        return booleanArrayToByteArray(value.clone());
    }

    public boolean[] getBits() {
        return value;
    }


    public UnsignedBigNumber multiply(UnsignedBigNumber number) {
        return new UnsignedBigNumber(multiplyOfTwoBooleanArrays(getBits(), number.getBits()));
    }

    public UnsignedBigNumber subtract(UnsignedBigNumber number) {
        return new UnsignedBigNumber(subtractOfTwoBooleanArrays(getBits(), number.getBits()));
    }

    public UnsignedBigNumber modPow(UnsignedBigNumber ex, UnsignedBigNumber n) {
        return new UnsignedBigNumber(modPowOfTwoBooleanArrays(getBits(), ex.getBits(), n.getBits()));
    }

    public UnsignedBigNumber modInverse(UnsignedBigNumber number) {
        return new UnsignedBigNumber(modInverseOfTwoBooleanArrays(getBits(), number.getBits()));
    }

    public UnsignedBigNumber add(UnsignedBigNumber number) {
        return new UnsignedBigNumber(addOfTwoBooleanArrays(getBits(), number.getBits()));

    }

    public boolean isOdd() {
        return LogicalUtils.isOddArrayBoolean(getBits());

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnsignedBigNumber)) return false;

        UnsignedBigNumber unsignedBigNumber = (UnsignedBigNumber) o;

        if (!Arrays.equals(value, unsignedBigNumber.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? Arrays.hashCode(value) : 0;
    }

    @Override
    public String toString() {
        return booleanArrayToStringHex(value);
    }

    @Override
    public UnsignedBigNumber clone() throws CloneNotSupportedException {
        return new UnsignedBigNumber(value);
    }

    public UnsignedBigNumber mod(UnsignedBigNumber n) {
        return new UnsignedBigNumber(LogicalUtils.modOfTwoBooleanArrays(getBits(), n.getBits()));
    }

    public UnsignedBigNumber pow(int ex) {
        return new UnsignedBigNumber(LogicalUtils.powOfBooleanArray(value, ex));
    }

    public static UnsignedBigNumber probablePrime(int nBits, RandomGenerator<UnsignedBigNumber> random) {
        return new UnsignedBigNumber(LogicalUtils.probablePrime(nBits, new BooleanArrayLinearRandomGenerator()));
    }

    public static UnsignedBigNumber probablePrimeWithOneGCD(int nBits, UnsignedBigNumber phi, RandomGenerator<UnsignedBigNumber> random) {
        return new UnsignedBigNumber(LogicalUtils.probablePrimeWithOneGCD(nBits, phi.getBits(), new BooleanArrayLinearRandomGenerator()));
    }
}

