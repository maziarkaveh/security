package no.uis.security.rsa.model;

import no.uis.security.common.utils.LogicalUtils;
import no.uis.security.des.service.exceptions.IllegalMethodParameterException;
import no.uis.security.rsa.service.RandomGenerator;
import no.uis.security.rsa.service.impl.BooleanArrayLinearRandomGenerator;

import java.util.Arrays;

import static no.uis.security.common.utils.LogicalUtils.*;
import static no.uis.security.common.utils.LogicalUtils.ZERO;

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


    private byte[] getByteValue() {
        return booleanArrayToByteArray(value.clone());
    }

    private boolean[] getValue() {
        return value.clone();
    }


    public UnsignedBigNumber multiply(UnsignedBigNumber number) {
        return new UnsignedBigNumber(multiplyOfTwoBooleanArrays(getValue(), number.getValue()));
    }

    public UnsignedBigNumber subtract(UnsignedBigNumber number) {
        return new UnsignedBigNumber(subtractOfTwoBooleanArrays(getValue(), number.getValue()));
    }

    public UnsignedBigNumber modPow(UnsignedBigNumber ex, UnsignedBigNumber n) {
        return new UnsignedBigNumber(modPowOfTwoBooleanArrays(getValue(), ex.getValue(), n.getValue()));
    }

    public UnsignedBigNumber modInverse(UnsignedBigNumber number) {
        return new UnsignedBigNumber(modInverseOfTwoPrimeBooleanArrays(getValue(), number.getValue()));
    }

    public UnsignedBigNumber add(UnsignedBigNumber number) {
        return new UnsignedBigNumber(addOfTwoBooleanArrays(getValue(), number.getValue()));

    }

    public boolean isOdd() {
        return LogicalUtils.isOddArrayBoolean(getValue());

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
        return new UnsignedBigNumber(value.clone());
    }

    public UnsignedBigNumber mod(UnsignedBigNumber n) {
        return new UnsignedBigNumber(LogicalUtils.modOfTwoBooleanArrays(getValue(), n.getValue()));
    }

    public UnsignedBigNumber pow(int ex) {
        return new UnsignedBigNumber(LogicalUtils.powOfBooleanArray(value, ex));
    }

    public static UnsignedBigNumber probablePrime(int nBits, RandomGenerator<UnsignedBigNumber> random) {
        return new UnsignedBigNumber(LogicalUtils.probablePrime(nBits,new BooleanArrayLinearRandomGenerator()));
    }
}

