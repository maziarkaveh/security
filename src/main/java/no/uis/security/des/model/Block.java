package no.uis.security.des.model;

import no.uis.security.des.service.exceptions.ServiceValidationException;
import no.uis.security.des.utils.LogicalUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

public class Block implements Cloneable {
    private final boolean[] left;
    private final boolean[] right;
    private final int sizeInBits;


    public Block(byte[] block, int blockSizeInBits) {
        this(LogicalUtils.byteArrayToBooleanArray(block), blockSizeInBits);
    }

    public Block(byte[] block) {
        this(block, block.length * 8);
    }

    public Block(boolean[] block) {
        this(block, block.length);
    }

    public Block(boolean[] block, int blockSizeInBits) {
        if (block.length != blockSizeInBits) {
            throw new ServiceValidationException("Block length does not equal expected block sizeInBits");
        }
        left = ArrayUtils.subarray(block, 0, blockSizeInBits / 2);
        right = ArrayUtils.subarray(block, blockSizeInBits / 2, blockSizeInBits);
        sizeInBits = blockSizeInBits;
    }

    public Block(boolean[] left, boolean[] right) {
        this(ArrayUtils.addAll(left, right));


    }

    public Block(byte[] left, byte[] right) {
        this(ArrayUtils.addAll(left, right));


    }

    public byte[] getLeftInBytes() {
        return LogicalUtils.booleanArrayToByteArray(left).clone();
    }

    public byte[] getRightInBytes() {
        return LogicalUtils.booleanArrayToByteArray(right).clone();
    }

    public boolean[] getAllBits() {
        return ArrayUtils.addAll(left, right).clone();
    }

    public byte[] getAllBytes() {
        return LogicalUtils.booleanArrayToByteArray(getAllBits());
    }

    public boolean[] getLeft() {
        return left.clone();
    }

    public boolean[] getRight() {
        return right.clone();
    }

    public int getSizeInBits() {
        return sizeInBits;
    }

    public int getSizeInBytes() {
        return sizeInBits / 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;

        Block block = (Block) o;

        if (sizeInBits != block.sizeInBits) return false;
        if (!Arrays.equals(left, block.left)) return false;
        if (!Arrays.equals(right, block.right)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = left != null ? Arrays.hashCode(left) : 0;
        result = 31 * result + (right != null ? Arrays.hashCode(right) : 0);
        result = 31 * result + sizeInBits;
        return result;
    }

    public Block clone() {
        return new Block(Arrays.copyOf(getAllBits(), getSizeInBits()), getSizeInBits());
    }

    @Override
    public String toString() {

        return "Hex :" + LogicalUtils.convertBytesToStringHex(getAllBytes()) + " Bits: " + LogicalUtils.convertBytesToStringBits(getAllBytes()) + " Decimal: " + LogicalUtils.convertBytesToStringDecimal(getAllBytes()) + " ";
    }
}