package no.uis.security.des.service.feistel;

import no.uis.security.des.model.Block;
import no.uis.security.des.service.SubKeyGenerator;
import no.uis.security.des.service.exceptions.IllegalMethodParameterException;
import no.uis.security.common.utils.CypherUtils;
import no.uis.security.common.utils.LogicalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DesSubKeyGenerator implements SubKeyGenerator {
    private static final Logger log = LoggerFactory.getLogger(DesSubKeyGenerator.class);

    private static final byte[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };
    public static final byte[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };
    private static final byte[] rotations = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    @Override
    public Block init(byte[] rawPassword) {
        boolean[] rawPasswordBits = LogicalUtils.byteArrayToBooleanArray(rawPassword);

        byte[] permute = CypherUtils.permute(PC1, rawPasswordBits);
        log.debug("key after PC1 = {}", LogicalUtils.byteArrayToStringHex(permute));

        return new Block(permute);

    }


    @Override
    public Block generateNextKey(Block key, int level) {
        if (level < 0 || rotations.length - 1 < level || key.getSizeInBits() != 56) {
            throw new IllegalMethodParameterException();
        }
        boolean[] left = key.getLeft();
        boolean[] right = key.getRight();
        for (int i = 0; i < rotations[level]; i++) {
            left = LogicalUtils.circularShiftArrayToLeft(left);
            right = LogicalUtils.circularShiftArrayToLeft(right);
        }
        Block block = new Block(left, right);
        log.debug("subkey level {} before PC2 = {}", level, LogicalUtils.byteArrayToStringHex(block.getAllBytes()));

        return block;
    }


}
