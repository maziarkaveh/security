package no.uis.security.des.service;

import no.uis.security.des.model.Block;


public interface SubKeyGenerator {
    Block init(byte[] rawPassword);

    Block generateNextKey(Block key, int level);
}
