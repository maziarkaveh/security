package no.uis.security.des.model;

import no.uis.security.des.AbstractBasicTest;
import no.uis.security.des.service.exceptions.ServiceValidationException;
import org.junit.Assert;
import org.junit.Test;

public class BlockTest extends AbstractBasicTest {
    @Test(expected = ServiceValidationException.class)
    public void blockClassShouldThrowExceptionNotProperBlockSize() {
        new Block("this is test with wrong length".getBytes(), 32);

    }

    @Test
    public void blockClassConstructor() {
        new Block("this".getBytes(), 32);
    }

    @Test
    public void blockGetAll() {
        byte[] bytes = "this is ".getBytes();
        Block block = new Block(bytes, 64);
        Assert.assertArrayEquals(bytes, block.getAllBytes());
    }

    @Test
    public void blockClassTestRightAndLeft() {
        Block block = new Block("this".getBytes(), 32);
        Assert.assertArrayEquals("th".getBytes(), block.getLeftInBytes());
        Assert.assertArrayEquals("is".getBytes(), block.getRightInBytes());
    }

}
