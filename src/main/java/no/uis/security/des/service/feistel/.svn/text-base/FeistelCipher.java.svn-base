package no.uis.security.des.service.feistel;

import no.uis.security.des.model.Block;
import no.uis.security.des.service.EncryptionService;
import no.uis.security.des.service.RoundFunction;
import no.uis.security.des.service.SubKeyGenerator;
import no.uis.security.des.service.exceptions.ServiceValidationException;
import no.uis.security.des.utils.LogicalUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class FeistelCipher implements EncryptionService {


    private static final Logger log = LoggerFactory.getLogger(FeistelCipher.class);
    @Value("64")
    private final int blockSize;
    @Value("56")
    private final int keySize;
    @Value("16")
    private final int numberOfRounds;
    @Autowired
    private SubKeyGenerator subKeyGenerator;
    @Autowired
    private RoundFunction roundFunction;

    //    @Autowired
    public FeistelCipher() {
        this.blockSize = 64;
        this.keySize = 56;
        this.numberOfRounds = 16;
        validateProperties();
    }

    //    @PostConstruct
    protected void validateProperties() throws ServiceValidationException {
        if (blockSize < 0 || blockSize % 32 != 0) {
            throw new ServiceValidationException("Block size is not valid,is should be positive multiple by 32");
        }
        if (keySize < 10) {
            throw new ServiceValidationException("key size is not valid,is should be positive less than 10 integer value");
        }
        if (numberOfRounds < 4) {
            throw new ServiceValidationException("number of round  is not valid,is should be positive more than 3 integer value");
        }
    }

    @Override
    public byte[] encrypt(byte[] plain, byte[] key) {
        log.debug("plain text ={}", LogicalUtils.convertBytesToStringHex(plain));
        log.debug("plain key ={}", LogicalUtils.convertBytesToStringHex(key));
        Block[] keys = generateKeys(key);
        List<Byte> wholeCypher = new ArrayList<Byte>();
        for (int i = 0; i < plain.length; i += 8) {
            Block block = feistel64Bits(ArrayUtils.subarray(plain, i, i + 8), keys);
            wholeCypher.addAll(Arrays.asList(ArrayUtils.toObject(block.getAllBytes())));
        }
        byte[] all = ArrayUtils.toPrimitive(wholeCypher.toArray(new Byte[0]));
        log.debug("cypher={}", LogicalUtils.convertBytesToStringHex(all));
        return all;
    }

    @Override
    public byte[] encrypt(String plain, byte[] key) {
        return encrypt(plain.getBytes(), key);
    }

    @Override
    public byte[] decrypt(byte[] cypher, byte[] key) {
        log.debug("cypher text ={}", LogicalUtils.convertBytesToStringHex(cypher));
        log.debug("cypher key ={}", LogicalUtils.convertBytesToStringHex(key));

        Block[] keys = generateKeys(key);
        ArrayUtils.reverse(keys);
        List<Byte> wholeCypher = new ArrayList<Byte>();
        for (int i = 0; i < cypher.length; i += 8) {
            Block block = feistel64Bits(ArrayUtils.subarray(cypher, i, i + 8), keys);
            wholeCypher.addAll(Arrays.asList(ArrayUtils.toObject(block.getAllBytes())));
        }
        byte[] all = ArrayUtils.toPrimitive(wholeCypher.toArray(new Byte[0]));
        log.debug("plain text={}", LogicalUtils.convertBytesToStringHex(all));
        return all;
    }

    private Block[] generateKeys(byte[] key) {
        Block keyBlock = subKeyGenerator.init(key);
        Block[] keys = new Block[16];
        for (int i = 0; i < numberOfRounds; i++) {
            keyBlock = subKeyGenerator.generateNextKey(keyBlock, i);
            keys[i] = keyBlock;
        }
        return keys;
    }

    private Block feistel64Bits(byte[] text, Block[] keys) {
        byte[] message = LogicalUtils.checkIfByteArrayLengthIsLessThanExpected(text, 8);
        Block block = roundFunction.init(new Block(message));
        for (int i = 0; i < numberOfRounds; i++) {
            block = roundFunction.generateNextLevel(block, keys[i], i);
        }
        Block code = new Block(block.getRight(), block.getLeft());
        return roundFunction.inverseInit(code);
    }


}
