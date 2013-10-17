package no.uis.security.rsa.service.impl;

import no.uis.security.des.service.exceptions.IllegalMethodParameterException;
import no.uis.security.rsa.model.RSAKeys;
import no.uis.security.rsa.model.UnsignedBigNumber;
import no.uis.security.rsa.service.EncryptionService;
import no.uis.security.rsa.service.RandomGenerator;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 17.10.13
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RSAEncryptionService implements EncryptionService {
    @Autowired
    @Qualifier("unsignedBitsBigNumberLinearRandomGenerator")
    private RandomGenerator<UnsignedBigNumber> randomGenerator;
    private static final Logger log = LoggerFactory.getLogger(RSAEncryptionService.class);

    @Override
    public byte[] encrypt(byte[] message, byte[] key, byte[] modulus) {
        return getBytes(message, key, modulus, modulus.length - 1);
    }

    @Override
    public byte[] encrypt(String plain, byte[] key, byte[] modulus) {
        return encrypt(plain.getBytes(), key, modulus);
    }

    @Override
    public byte[] decrypt(byte[] cipher, byte[] key, byte[] modulus) {
        byte[] bytesValue = cipher;
        if (bytesValue.length % modulus.length != 0) {
            bytesValue = ArrayUtils.addAll(new byte[modulus.length - bytesValue.length % modulus.length], bytesValue);
        }
        return getBytes(bytesValue, key, modulus, modulus.length);
    }

    private byte[] getBytes(byte[] bytesValue, byte[] key, byte[] modulus, int blockLength) {
        byte[] result = null;
        for (int i = 0; i < Math.ceil(bytesValue.length / (float) blockLength); i++) {
            int from = i * blockLength;
            int to = (i + 1) * blockLength < bytesValue.length ? (i + 1) * blockLength : bytesValue.length;
            UnsignedBigNumber t = new UnsignedBigNumber(ArrayUtils.subarray(bytesValue, from, to));
            byte[] bytes = t.modPow(new UnsignedBigNumber(key), new UnsignedBigNumber(modulus)).getBytes();
            result = result == null ? bytes : ArrayUtils.addAll(result, bytes);
        }

        return result;
    }

    @Override
    public RSAKeys generateRSAKeys(int numberOgBytes) {
        if (numberOgBytes <= 0) {
            throw new IllegalMethodParameterException();
        }
        if (numberOgBytes > 12) {
            log.warn("number of bits exceeding 12 bytes make systems  speed low");
        }
        UnsignedBigNumber p = UnsignedBigNumber.probablePrime(numberOgBytes * 4, randomGenerator);
        UnsignedBigNumber q = UnsignedBigNumber.probablePrime(numberOgBytes * 4, randomGenerator);
        UnsignedBigNumber phi = (p.subtract(UnsignedBigNumber.ONE)).multiply(q.subtract(UnsignedBigNumber.ONE));

        UnsignedBigNumber modulus = p.multiply(q);

        UnsignedBigNumber publicKey = UnsignedBigNumber.probablePrimeWithOneGCD(numberOgBytes * 8, phi, randomGenerator);
        UnsignedBigNumber privateKey = publicKey.modInverse(phi);
        return new RSAKeys(privateKey.getBytes(), publicKey.getBytes(), modulus.getBytes());
    }
}
