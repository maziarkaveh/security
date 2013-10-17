package no.uis.security.rsa.service.impl;

import no.uis.security.des.service.exceptions.IllegalMethodParameterException;
import no.uis.security.rsa.model.BigInt;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;


public class RSABigIntImpl {
    private static final Logger log = LoggerFactory.getLogger(RSABigIntImpl.class);

    private final static BigInt one = new BigInt(BigInteger.ONE);

    private final BigInt privateKey;
    private final BigInt publicKey;
    private final BigInt modulus;
    private int blockSize;

    public RSABigIntImpl(int numBytes) {
        if (numBytes <= 0) {
            throw new IllegalMethodParameterException();
        }
        if (numBytes > 12) {
            log.warn("number of bits exceeding 100 make system  speed low");
        }
        this.blockSize = 2;
        BigInt p = BigInt.probablePrime(numBytes * 4, null);
        BigInt q = BigInt.probablePrime(numBytes * 4, null);
        BigInt phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = BigInt.probablePrimeWithOneGCD(numBytes * 8, phi, null);
        privateKey = publicKey.modInverse(phi);
    }

    public BigInt getPublicKey() {
        return publicKey;
    }

    public BigInt getModulus() {
        return modulus;
    }


    byte[] encrypt(final byte[] message) {
        byte[] bytesValue = message;
        byte[] result = null;
        for (int i = 0; i < Math.ceil(bytesValue.length / (float) blockSize); i++) {
            int from = i * blockSize;
            int to = (i + 1) * blockSize < bytesValue.length ? (i + 1) * blockSize : bytesValue.length;
            BigInt t = new BigInt(ArrayUtils.subarray(bytesValue, from, to));

            byte[] bytes = t.modPow(publicKey, modulus).getBytes();
            if (bytes.length < modulus.getBytes().length) {
                bytes = ArrayUtils.addAll(new byte[modulus.getBytes().length - bytes.length], bytes);
            }

            if (result == null) {
                result = bytes;
            } else {
                result = ArrayUtils.addAll(result, bytes);
            }
        }

        return result;
    }

    byte[] encrypt(String message) {
        return encrypt(message.getBytes());
    }

    byte[] decrypt(final byte[] cipher) {
        int length = modulus.getBytes().length;

        byte[] bytesValue = cipher;
        if (bytesValue.length % modulus.getBytes().length != 0) {
            bytesValue = ArrayUtils.addAll(new byte[modulus.getBytes().length - bytesValue.length % modulus.getBytes().length], bytesValue);
        }
        byte[] result = null;
        for (int i = 0; i < Math.ceil(bytesValue.length / (float) length); i++) {
            int from = i * length;
            int to = (i + 1) * length < bytesValue.length ? (i + 1) * length : bytesValue.length;
            BigInt t = new BigInt(ArrayUtils.subarray(bytesValue, from, to));
            byte[] bytes = t.modPow(privateKey, modulus).getBytes();
            if (result == null) {
                result = bytes;
            } else {
                result = ArrayUtils.addAll(result, bytes);
            }
        }
        return result;
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "phi = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {


            int N = Integer.parseInt("10");
            RSABigIntImpl key = new RSABigIntImpl(N);


            String s = "salamsalamsalsalslals dsjkf gfsdjkg fjhgds fjhgeyriut fsdjhgf jdhsgf yerut fsdjhfg jdhsgf yeru fsvjdgh";

            byte[] encrypt = key.encrypt(s);
            byte[] decrypt = key.decrypt(encrypt);
            System.out.println("message   = " + s);
            System.out.println("decrypted = " + new String(decrypt));
        }
    }
}

