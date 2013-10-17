package no.uis.security.rsa.service.impl;

import no.uis.security.common.utils.LogicalUtils;
import no.uis.security.des.AbstractBasicTest;
import no.uis.security.rsa.model.RSAKeys;
import no.uis.security.rsa.model.UnsignedBigNumber;
import no.uis.security.rsa.service.EncryptionService;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 17.10.13
 * Time: 23:28
 * To change this template use File | Settings | File Templates.
 */
public class RSAEncryptionServiceTest extends AbstractBasicTest {
    @Autowired
    private EncryptionService encryptionService;

    @Test
    public void testEncryptDecrypt() throws Exception {
        byte[] publicKey = LogicalUtils.hexStringToByteArray("74657C859ACFFC6B3E5D");
        byte[] privateKey = LogicalUtils.hexStringToByteArray("0C932ECF9969EF1A6855");
        byte[] modulus = LogicalUtils.hexStringToByteArray("2BA238A7FD72FC7A74F7");
        String message = "this is a long message test for encrypt and decrypt the result for operate both should be the same";
        byte[] cypher = encryptionService.encrypt(message, publicKey, modulus);
        byte[] actual = encryptionService.decrypt(cypher, privateKey, modulus);

        Assert.assertEquals(message, new String(actual));

    }

    @Test
    public void testEncrypt() throws Exception {
        byte[] publicKey = LogicalUtils.hexStringToByteArray("74657C859ACFFC6B3E5D");
        byte[] modulus = LogicalUtils.hexStringToByteArray("2BA238A7FD72FC7A74F7");
        String message = "this is a long message test for encrypt and decrypt the result for operate both should be the same";
        byte[] cypher = encryptionService.encrypt(message, publicKey, modulus);
        String expected = "017601BE47B47DF20B552B366BD9022869FAE2661ED97A0873BE3D49D72E048E67DC2B99D72F43B3104C2C48DA022178B0D80981D565C65D3D51DA601FFB0382DCC62F7A6D9019BF06FB22535F32CDF21553CF5C2AD000E70EB60CD8E0FD7F6A993F56371B2FE35F8CA5997AFE02";
        Assert.assertEquals(expected, LogicalUtils.byteArrayToStringHex(cypher));

    }

    @Test
    public void testDecrypt() throws Exception {
        byte[] privateKey = LogicalUtils.hexStringToByteArray("0C932ECF9969EF1A6855");
        byte[] modulus = LogicalUtils.hexStringToByteArray("2BA238A7FD72FC7A74F7");
        byte[] cipher = LogicalUtils.hexStringToByteArray("017601BE47B47DF20B552B366BD9022869FAE2661ED97A0873BE3D49D72E048E67DC2B99D72F43B3104C2C48DA022178B0D80981D565C65D3D51DA601FFB0382DCC62F7A6D9019BF06FB22535F32CDF21553CF5C2AD000E70EB60CD8E0FD7F6A993F56371B2FE35F8CA5997AFE02");
        byte[] actual = encryptionService.decrypt(cipher, privateKey, modulus);
        String message = "this is a long message test for encrypt and decrypt the result for operate both should be the same";

        Assert.assertEquals(message, new String(actual));

    }

    @Test
    public void generateRSAKeys() throws Exception {
        UnsignedBigNumber test = new UnsignedBigNumber("4324234");
        RSAKeys rsaKeys = encryptionService.generateRSAKeys(8);
        UnsignedBigNumber publicKey = new UnsignedBigNumber(rsaKeys.getPublicKey());
        UnsignedBigNumber privateKey = new UnsignedBigNumber(rsaKeys.getPrivateKey());
        UnsignedBigNumber modulus = new UnsignedBigNumber(rsaKeys.getModulus());
        Assert.assertEquals(test.toString(), test.modPow(publicKey, modulus).modPow(privateKey, modulus).toString());

    }
}
