package no.uis.security.dsa.model;

 import no.uis.security.common.AbstractBasicTest;
 import org.junit.Assert;
 import org.junit.Test;
 import org.springframework.test.annotation.Repeat;

 import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class GlobalPublicKeyTest  {
    @Test
    @Repeat(10)
    public void testGenerateNewInstance() throws Exception {
        GlobalPublicKey globalPublicKey = GlobalPublicKey.generateNewInstance();
        BigInteger p = globalPublicKey.getP();
        BigInteger q = globalPublicKey.getQ();
        BigInteger g = globalPublicKey.getG();
        Assert.assertTrue(q.isProbablePrime(20));
        Assert.assertTrue(q.bitLength()==160);

        Assert.assertTrue(p.isProbablePrime(20));
        int l = p.bitLength();
        Assert.assertTrue((l >= 512 && l <= 1024 && l % 64 == 0));
        Assert.assertEquals(p.mod(q),BigInteger.ONE);
        Assert.assertTrue(g.compareTo(BigInteger.ONE)>0);

    }
}
