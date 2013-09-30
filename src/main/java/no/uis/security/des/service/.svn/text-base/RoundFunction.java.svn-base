package no.uis.security.des.service;

import no.uis.security.des.model.Block;


public interface RoundFunction {

    Block init(Block code);

    Block generateNextLevel(Block code, Block key, int level);

    Block inverseInit(Block code);

}
