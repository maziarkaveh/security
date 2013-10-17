package no.uis.security.rsa.service.impl;

import no.uis.security.rsa.model.UnsignedBigNumber;
import no.uis.security.rsa.service.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Adapter
 */
@Service
@Scope("prototype")
public class UnsignedBitsBigNumberLinearRandomGenerator implements RandomGenerator<UnsignedBigNumber> {

    @Autowired
    @Qualifier("booleanArrayLinearRandomGenerator")
    RandomGenerator<boolean[]> booleanRandomGenerator;


    @Override
    public UnsignedBigNumber next() {
        return new UnsignedBigNumber(booleanRandomGenerator.next());
    }

    @Override
    public UnsignedBigNumber next(int numBits) {
        return new UnsignedBigNumber(booleanRandomGenerator.next(numBits));
    }


}
