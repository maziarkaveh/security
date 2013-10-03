package no.uis.security.rsa.model;

import no.uis.security.des.service.exceptions.IllegalMethodParameterException;

import java.util.Arrays;

import static no.uis.security.common.utils.LogicalUtils.*;

public class UnsignedBigNumber implements Cloneable {
    private final boolean[] value;

    private UnsignedBigNumber(boolean[] value) {
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

    public UnsignedBigNumber modPow(UnsignedBigNumber number) {
        return new UnsignedBigNumber(modPowOfTwoBooleanArrays(getValue(), number.getValue()));
    }

    public UnsignedBigNumber modInverse(UnsignedBigNumber number) {
        return new UnsignedBigNumber(modInverseOfTwoBooleanArrays(getValue(), number.getValue()));
    }

    public UnsignedBigNumber add(UnsignedBigNumber number) {
        return new UnsignedBigNumber(addOfTwoBooleanArrays(getValue(), number.getValue()));

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
}

