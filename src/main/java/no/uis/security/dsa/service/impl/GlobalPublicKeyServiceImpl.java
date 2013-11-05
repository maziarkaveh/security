package no.uis.security.dsa.service.impl;

import no.uis.security.common.service.BasicServiceImpl;
import no.uis.security.dsa.model.GlobalPublicKey;
import no.uis.security.dsa.repository.GlobalPublicKeyRepository;
import no.uis.security.dsa.service.GlobalPublicKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class GlobalPublicKeyServiceImpl extends BasicServiceImpl<GlobalPublicKey, Long> implements GlobalPublicKeyService {

    private GlobalPublicKeyRepository globalPublicKeyRepository;

    @Autowired
    public GlobalPublicKeyServiceImpl(GlobalPublicKeyRepository globalPublicKeyRepository) {
        super(globalPublicKeyRepository);
        this.globalPublicKeyRepository = globalPublicKeyRepository;
    }

    @Transactional
    public GlobalPublicKey getLastGlobalPublicKey() {
        GlobalPublicKey lastGlobalPublicKey = globalPublicKeyRepository.getLastGlobalPublicKey();
        if (lastGlobalPublicKey == null) {
            lastGlobalPublicKey = globalPublicKeyRepository.store(GlobalPublicKey.generateNewInstance());
        }
        return lastGlobalPublicKey;
    }


    @Transactional
    public GlobalPublicKey generateAndPersistNewGlobalPublicKey() {
        GlobalPublicKey globalPublicKey = GlobalPublicKey.generateNewInstance();
        globalPublicKeyRepository.store(globalPublicKey);
        return globalPublicKey;
    }
}
