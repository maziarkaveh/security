package no.uis.security.des.service.feistel;

import no.uis.security.des.AbstractBasicTest;
import no.uis.security.des.model.Block;
import no.uis.security.des.utils.LogicalUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DesSubKeyGeneratorTest extends AbstractBasicTest {
    @Autowired
    private DesSubKeyGenerator desSubKeyGenerator;

    @Test
    public void testInitKey() throws Exception {
        byte[] password = LogicalUtils.hexStringToByteArray("0f1571c947d9e859");
        byte[] expected = LogicalUtils.hexStringToByteArray("68fc44a1113e96");
        Block init = desSubKeyGenerator.init(password);
        Assert.assertArrayEquals(expected, init.getAllBytes());

    }


    @Test
    public void testGenerateNextKey() throws Exception {
        byte[] key = LogicalUtils.hexStringToByteArray("68fc44a1113e96");
        byte[] expected = LogicalUtils.hexStringToByteArray("d1f88942227d2c");
        Block next = desSubKeyGenerator.generateNextKey(new Block(key), 0);
        Assert.assertArrayEquals(expected, next.getAllBytes());
        byte[] key2 = LogicalUtils.hexStringToByteArray("fc44a6813e9611");
        byte[] expected2 = LogicalUtils.hexStringToByteArray("f1129a34fa5844");
        Block next2 = desSubKeyGenerator.generateNextKey(new Block(key2), 5);
        Assert.assertArrayEquals(expected2, next2.getAllBytes());
    }
}
