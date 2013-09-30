package no.uis.security.des.service;


public interface EncryptionService {
    byte[] encrypt(byte[] plain, byte[] key);

    byte[] encrypt(String plain, byte[] key);

    byte[] decrypt(byte[] cypher, byte[] key);
}
