package no.uis.security.des.service.feistel;

import no.uis.security.des.model.Block;
import no.uis.security.des.service.RoundFunction;
import no.uis.security.common.utils.CypherUtils;
import no.uis.security.common.utils.LogicalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static no.uis.security.common.utils.LogicalUtils.booleanArrayToByteArray;
import static no.uis.security.common.utils.LogicalUtils.byteArrayToStringHex;


@Service
public class DESRoundFunction implements RoundFunction {
    private static final Logger log = LoggerFactory.getLogger(DESRoundFunction.class);

    private static final byte[][] S = {{
            14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
            0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
            4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
            15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
    }, {
            15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
            3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
            0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
            13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
    }, {
            10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
            13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
            13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
            1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
    }, {
            7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
            13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
            10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
            3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
    }, {
            2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
            14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
            4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
            11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
    }, {
            12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
            10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
            9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
            4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
    }, {
            4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
            13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
            1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
            6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
    }, {
            13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
    }};
    private static final byte[] E = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };
    private static final byte[] P = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };
    private static final byte[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };


    private static final byte[] FP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    public static final int BLOCK_SIZE = 64;

    @Override
    public Block init(Block code) {
        Block block = new Block(CypherUtils.permute(IP, code.getAllBits()));
        log.debug("message after IP = {}", byteArrayToStringHex(block.getAllBytes()));

        return block;
    }


    @Override
    public Block inverseInit(Block code) {
        Block block = new Block(CypherUtils.permute(FP, code.getAllBits()));
        log.debug("message after FP = {}", byteArrayToStringHex(block.getAllBytes()));
        return block;

    }

    @Override
    public Block generateNextLevel(Block code, Block key, int level) {
        byte[] left = code.getLeftInBytes();
        boolean[] right = code.getRight();
        log.debug("cypher level {} left = {}", level, byteArrayToStringHex(left));
        log.debug("cypher level {} right = {}", level, byteArrayToStringHex(code.getRightInBytes()));

        byte[] l1 = CypherUtils.permute(E, right);
        log.debug("cypher level {} after E = {}", level, byteArrayToStringHex(l1));

        byte[] permutedKey = CypherUtils.permute(DesSubKeyGenerator.PC2, key.getAllBits());
        log.debug("subkey {} after PC2 = {}", level, byteArrayToStringHex(permutedKey));

        byte[] l2 = LogicalUtils.exclusiveOr(l1, permutedKey);
        log.debug("cypher level {} after xor = {}", level, byteArrayToStringHex(l2));

        boolean[] l3 = substitution(l2);
        log.debug("cypher level {} after substitution = {}", level, byteArrayToStringHex(booleanArrayToByteArray(l3)));

        byte[] l4 = CypherUtils.permute(P, l3);
        log.debug("cypher level {} after P = {}", level, byteArrayToStringHex(l4));


        byte[] l5 = LogicalUtils.exclusiveOr(l4, left);
        log.debug("cypher level {} after xor by left = {}", level, byteArrayToStringHex(l5));

        Block block = new Block(code.getRightInBytes(), l5);
        log.debug("cypher after level {} = {}", level, byteArrayToStringHex(block.getAllBytes()));

        return block;
    }

    protected boolean[] substitution(byte[] t) {
        boolean[] booleans = LogicalUtils.byteArrayToBooleanArray(t);
        long l = 0;
        for (int i = 0, j = 0; i < 48; i += 6, j++) {
            byte row = LogicalUtils.booleanArrayToByteArray(booleans[i], booleans[i + 5])[0];
            byte col = LogicalUtils.booleanArrayToByteArray(booleans[i + 1], booleans[i + 2], booleans[i + 3], booleans[i + 4])[0];
            l = l << 4;
            l += S[j][row * 16 + col];
        }

        byte[] bytes = LogicalUtils.checkIfByteArrayLengthIsLessThanExpected(LogicalUtils.longToByteArray(l), 4);
        return LogicalUtils.byteArrayToBooleanArray(bytes);

    }


}
