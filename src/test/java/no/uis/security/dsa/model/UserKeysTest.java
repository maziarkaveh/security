package no.uis.security.dsa.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class UserKeysTest{
    @Test
    public void testGenerateNewInstance() throws Exception {
        GlobalPublicKey globalPublicKey = GlobalPublicKey.generateNewInstance();
        User user = new User("Test", "Test", "Test");
        UserKeys userKeys = UserKeys.generateNewInstance(globalPublicKey, user);
        Assert.assertEquals(globalPublicKey,userKeys.getGlobalPublicKey());
        Assert.assertEquals(user,userKeys.getUser());
        Assert.assertTrue(userKeys.getPrivateKey().compareTo(globalPublicKey.getQ()) < 0);
        Assert.assertTrue(userKeys.getPrivateKey().compareTo(BigInteger.ZERO)>0);
        BigInteger expected = globalPublicKey.getG().modPow(userKeys.getPrivateKey(), globalPublicKey.getP());
        Assert.assertEquals(expected,userKeys.getPublicKey());


    }
}
