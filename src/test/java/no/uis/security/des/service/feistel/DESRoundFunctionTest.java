package no.uis.security.des.service.feistel;

import no.uis.security.common.utils.LogicalUtils;
import no.uis.security.des.AbstractBasicTest;
import no.uis.security.des.model.Block;
import no.uis.security.des.service.RoundFunction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class DESRoundFunctionTest extends AbstractBasicTest {

    @Autowired
    private RoundFunction roundFunction;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFunction() throws Exception {

    }

//    @Test(expected = IllegalMethodParameterException.class)
//    public void testInitShouldThrowExceptionInappropriateSize() throws Exception {
//        roundFunction.init(new Block("123456789".getBytes(), 9 * 8));
//    }


    @Test
    public void testInit() throws Exception {
        Block block = new Block("someTest".getBytes(), 64);
        Block permutedBlock = roundFunction.init(block);
        Assert.assertEquals("someTest", new String(roundFunction.inverseInit(permutedBlock).getAllBytes()));
        Assert.assertArrayEquals(new byte[]{-1, -47, -66, 111, 0, -17, 6, 67}, permutedBlock.getAllBytes());
    }

    @Test
    public void generateNextLevel() throws Exception {
        byte[] m = LogicalUtils.hexStringToByteArray("887fbc6cc11bfc09");
        byte[] key = LogicalUtils.hexStringToByteArray("225347ef4b0889");
        byte[] expected = LogicalUtils.hexStringToByteArray("c11bfc0967117cf2");
        Block block = roundFunction.generateNextLevel(new Block(m), new Block(key), 8);
        Assert.assertArrayEquals(expected, block.getAllBytes());
    }

    @Test
    public void testSubstitution() {
        byte[] m = LogicalUtils.hexStringToByteArray("e7a4633f5a2e");
        byte[] expected = LogicalUtils.hexStringToByteArray("a32f11c2");
        boolean[] substitution = new DESRoundFunction().substitution(m);
        Assert.assertArrayEquals(expected, LogicalUtils.booleanArrayToByteArray(substitution));
    }

    @Test
    public void inverseInit() throws Exception {
        Block block = new Block(new byte[]{-1, -47, -66, 111, 0, -17, 6, 67}, 64);
        Block inverse = roundFunction.inverseInit(block);

        Assert.assertArrayEquals("someTest".getBytes(), inverse.getAllBytes());
        byte[] m = LogicalUtils.hexStringToByteArray("2589649075e8fd8f");
        Block block1 = roundFunction.inverseInit(new Block(m));
        Assert.assertEquals("DA02CE3A89ECAC3B", LogicalUtils.byteArrayToStringHex(block1.getAllBytes()));
    }
}
