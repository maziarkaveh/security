package no.uis.security.rsa.service.impl;

import no.uis.security.common.utils.LogicalUtils;
import no.uis.security.rsa.service.RandomGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static no.uis.security.common.utils.LogicalUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.10.13
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
@Service
@Scope("prototype")
public class BooleanArrayLinearRandomGenerator implements RandomGenerator<boolean[]> {

    private final static boolean[] A = LogicalUtils.hexStringToBooleanArray("41A7");
    private static boolean[] state;
    private static final int NUM_BITS = 512;

    static {
        state = generateSeed();
    }


    @Override
    public boolean[] next() {
        return next(NUM_BITS);
    }

    @Override
    public boolean[] next(int numBits) {

        boolean[] pow = powOfBooleanArray(LogicalUtils.TWO, numBits);
        state = modOfTwoBooleanArrays(multiplyOfTwoBooleanArrays(A, state), (subtractOfTwoBooleanArrays(pow, LogicalUtils.ONE)));
        return state;
    }

    protected static boolean[] generateSeed() {
        final int numberOfLongs = NUM_BITS / 64;
        long[] seed = new long[numberOfLongs];
        seed[0] = System.nanoTime();
        for (int i = 1; i < numberOfLongs; i++) {
            for (int j = 0; j < i; j++) {
                long l = (j) % 2 == 1 ? ~seed[i] : seed[i];
                long l1 = System.nanoTime();
                seed[i] = seed[j] ^ (l1 >>> i) | (l1 << (64 - i)) ^ l;
            }
        }
        return LogicalUtils.longArrayToBooleanArray(seed);
    }
}
