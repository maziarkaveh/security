package no.uis.security.rsa.model;

import no.uis.security.des.service.exceptions.IllegalMethodParameterException;
import no.uis.security.rsa.service.RandomGenerator;

import java.math.BigInteger;
import java.security.SecureRandom;

public class BigInt implements Cloneable {

    private final BigInteger value;

    public BigInteger getValue() {
        return value;
    }

    public BigInt(byte[] value) {
        if (value == null || !(value.length > 0)) {
            throw new IllegalMethodParameterException();
        }
        this.value = new BigInteger(value);
    }

    public BigInt(BigInteger value) {

        this.value = value;
    }


    public byte[] getBytes() {
        return value.toByteArray();
    }


    public BigInt multiply(BigInt number) {
        return new BigInt(value.multiply(number.value));
    }

    public BigInt subtract(BigInt number) {
        return new BigInt(value.subtract(number.value));
    }

    public BigInt modPow(BigInt ex, BigInt n) {
        return new BigInt(value.modPow(ex.value, n.value));
    }

    public BigInt modInverse(BigInt number) {
        return new BigInt(value.modInverse(number.value));
    }

    public BigInt add(BigInt number) {
        return new BigInt(value.add(number.value));

    }

    public boolean isOdd() {
        return value.getLowestSetBit() % 2 == 1;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BigInt bigInt = (BigInt) o;

        if (value != null ? !value.equals(bigInt.value) : bigInt.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value.toString(16);
    }

    @Override
    public BigInt clone() throws CloneNotSupportedException {
        return new BigInt(value);
    }

    public BigInt mod(BigInt n) {
        return new BigInt(value.mod(n.value));
    }

    public BigInt pow(int ex) {
        return new BigInt(value.pow(ex));
    }

    public static BigInt probablePrime(int nBits, RandomGenerator<BigInt> random) {
        return new BigInt(BigInteger.probablePrime(nBits, new SecureRandom()));
    }

    public static BigInt probablePrimeWithOneGCD(int nBits, BigInt phi, RandomGenerator<BigInt> random) {
        for (; ; ) {
            BigInt bigInt = new BigInt(BigInteger.probablePrime(nBits, new SecureRandom())).mod(phi);
            if (bigInt.value.gcd(phi.value).equals(BigInteger.ONE)) {
                return bigInt;
            }

        }
    }
}

