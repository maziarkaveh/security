package no.uis.security.rsa.service;

import no.uis.security.rsa.model.RSAKeys;

public interface EncryptionService {
    byte[] encrypt(byte[] plain, byte[] key, byte[] modulus);

    byte[] encrypt(String plain, byte[] key, byte[] modulus);

    byte[] decrypt(byte[] cypher, byte[] key, byte[] modulus);

    RSAKeys generateRSAKeys(int numberOgBytes);
}
