package no.uis.security.hash.impl;

import no.uis.security.common.AbstractBasicTest;
import no.uis.security.common.utils.LogicalUtils;
import no.uis.security.common.utils.RandomUtils;
import no.uis.security.hash.HashService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 03.11.13
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class SHA1HashImplTest extends AbstractBasicTest {
    @Autowired
    private HashService hashService;
    private MessageDigest sha1MessageDigest;

    @Before
    public void init() throws NoSuchAlgorithmException {
        sha1MessageDigest = MessageDigest.getInstance("sha1");
    }

    @Test
    @Repeat(10)
    public void testHash() throws Exception {
        String str = RandomUtils.randomString(900, 1000);
        sha1MessageDigest.update(str.getBytes());
        String expected = LogicalUtils.byteArrayToStringHex(sha1MessageDigest.digest());
        Assert.assertEquals(expected, hashService.hash(str));
    }
}
