package no.uis.security.rsa.service.impl;


import no.uis.security.des.service.exceptions.IllegalMethodParameterException;
import no.uis.security.rsa.model.UnsignedBigNumber;
import no.uis.security.rsa.service.RandomGenerator;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RSAImpl {
    private static final Logger log = LoggerFactory.getLogger(RSAImpl.class);

    private final static UnsignedBigNumber one = new UnsignedBigNumber("1");
    private final static RandomGenerator<UnsignedBigNumber> random = new UnsignedBitsBigNumberLinearRandomGenerator();

    private final UnsignedBigNumber privateKey;
    private final UnsignedBigNumber publicKey;
    private final UnsignedBigNumber modulus;
    private int blockSize;

    public RSAImpl(int numBytes) {
        if (numBytes <= 0) {
            throw new IllegalMethodParameterException();
        }
        if (numBytes > 12) {
            log.warn("number of bits exceeding 100 make system  speed low");
        }
        UnsignedBigNumber p = UnsignedBigNumber.probablePrime(numBytes * 4, random);
        UnsignedBigNumber q = UnsignedBigNumber.probablePrime(numBytes * 4, random);
        UnsignedBigNumber phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        this.blockSize = modulus.getBytes().length-1;

        publicKey = UnsignedBigNumber.probablePrime(numBytes * 8, random);
        privateKey = publicKey.modInverse(phi);
    }

    public UnsignedBigNumber getPublicKey() {
        return publicKey;
    }

    public UnsignedBigNumber getModulus() {
        return modulus;
    }


    byte[] encrypt(final byte[] message) {
        byte[] bytesValue = message;
        byte[] result = null;
        for (int i = 0; i < Math.ceil(bytesValue.length / (float) blockSize); i++) {
            int from = i * blockSize;
            int to = (i + 1) * blockSize < bytesValue.length ? (i + 1) * blockSize : bytesValue.length;
            UnsignedBigNumber t = new UnsignedBigNumber(ArrayUtils.subarray(bytesValue, from, to));

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

    byte[] decrypt(byte[] cipher) {
        int length = modulus.getBytes().length;

        byte[] bytesValue = cipher;
        if (bytesValue.length % modulus.getBytes().length != 0) {
            bytesValue = ArrayUtils.addAll(new byte[modulus.getBytes().length - bytesValue.length % modulus.getBytes().length], bytesValue);
        }
        byte[] result = null;
        for (int i = 0; i < Math.ceil(bytesValue.length / (float) length); i++) {
            int from = i * length;
            int to = (i + 1) * length < bytesValue.length ? (i + 1) * length : bytesValue.length;
            UnsignedBigNumber t = new UnsignedBigNumber(ArrayUtils.subarray(bytesValue, from, to));

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
            RSAImpl key = new RSAImpl(N);


            String s = "salallalallal sdfg sdfgsdhjg fjhsg fjhsdg fjhgsd jfhgsdjfierw fsdjgfjdirtsdv";

            byte[] encrypt = key.encrypt(s);
            byte[] decrypt = key.decrypt(encrypt);
            System.out.println("message   = " + key);
            System.out.println("decrypted = " + new String(decrypt));
        }
    }
}

