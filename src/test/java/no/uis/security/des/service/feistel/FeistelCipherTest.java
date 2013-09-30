package no.uis.security.des.service.feistel;

import no.uis.security.des.AbstractBasicTest;
import no.uis.security.des.service.EncryptionService;
import no.uis.security.des.utils.LogicalUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class FeistelCipherTest extends AbstractBasicTest {
    @Autowired
    private EncryptionService encryptionService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testEncryptDecrypt() throws Exception {
        byte[] key = LogicalUtils.hexStringToByteArray("2c345fab19712cdf");
        String message = "this is a long message test for encrypt and decrypt the result for operate both should be the same";
        byte[] cypher = encryptionService.encrypt(message, key);
        byte[] actual = encryptionService.decrypt(cypher, key);
        byte[] plainText = message.getBytes();
        byte[] expected = ArrayUtils.addAll(plainText, new byte[6]);
        Assert.assertArrayEquals(expected, actual);

    }

    @Test
    public void testEncrypt() throws Exception {
        byte[] key = LogicalUtils.hexStringToByteArray("0f1571c947d9e859");
        byte[] plainText = LogicalUtils.hexStringToByteArray("02468aceeca86420");
        byte[] expected = LogicalUtils.hexStringToByteArray("da02ce3a89ecac3b");
        byte[] actual = encryptionService.encrypt(plainText, key);
        Assert.assertArrayEquals(expected, actual);

    }

    @Test
    public void testDecrypt() throws Exception {
        byte[] key = LogicalUtils.hexStringToByteArray("0f1571c947d9e859");
        byte[] plainText = LogicalUtils.hexStringToByteArray("02468aceeca86420");
        byte[] cypher = LogicalUtils.hexStringToByteArray("da02ce3a89ecac3b");
        byte[] actual = encryptionService.decrypt(cypher, key);
        Assert.assertArrayEquals(plainText, actual);

    }
}
