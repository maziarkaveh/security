package no.uis.security.rsa.model;

import no.uis.security.common.utils.LogicalUtils;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 17.10.13
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
public class RSAKeys {
    private final byte[] privateKey;
    private final byte[] publicKey;
    private final byte[] modulus;

    public RSAKeys(byte[] privateKey, byte[] publicKey, byte[] modulus) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.modulus = modulus;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getModulus() {
        return modulus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RSAKeys rsaKeys = (RSAKeys) o;

        if (!Arrays.equals(modulus, rsaKeys.modulus)) return false;
        if (!Arrays.equals(privateKey, rsaKeys.privateKey)) return false;
        if (!Arrays.equals(publicKey, rsaKeys.publicKey)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = privateKey != null ? Arrays.hashCode(privateKey) : 0;
        result = 31 * result + (publicKey != null ? Arrays.hashCode(publicKey) : 0);
        result = 31 * result + (modulus != null ? Arrays.hashCode(modulus) : 0);
        return result;
    }

    public String toString() {
        String s = "";
        s += "public  = " + LogicalUtils.byteArrayToStringHex(publicKey) + "\n";
        s += "private = " + LogicalUtils.byteArrayToStringHex(privateKey) + "\n";
        s += "modulus = " + LogicalUtils.byteArrayToStringHex(modulus);
        return s;
    }
}
