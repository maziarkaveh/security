package no.uis.security.dsa.service.impl;

import no.uis.security.common.repository.BasicRepository;
import no.uis.security.common.service.BasicService;
import no.uis.security.common.service.BasicServiceImpl;
import no.uis.security.dsa.model.GlobalPublicKey;
import no.uis.security.dsa.model.User;
import no.uis.security.dsa.model.UserKeys;
import no.uis.security.dsa.repository.UserKeysRepository;
import no.uis.security.dsa.service.GlobalPublicKeyService;
import no.uis.security.dsa.service.UserKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserKeysServiceImpl extends BasicServiceImpl<UserKeys, Long> implements UserKeysService {

    private UserKeysRepository userKeysRepository;
    @Autowired
    private GlobalPublicKeyService globalPublicKeyService;

    @Autowired
    public UserKeysServiceImpl(UserKeysRepository userKeysRepository) {
        super(userKeysRepository);
        this.userKeysRepository = userKeysRepository;
    }

    @Transactional
    public UserKeys retrieveOrGenerateAndAssignUserKeys(User user, GlobalPublicKey globalPublicKey) {
        for (UserKeys userKeys : user.getUserKeys()) {
            if (userKeys.getGlobalPublicKey().equals(globalPublicKey)) {
                return userKeys;
            }
        }
        UserKeys userKeys = UserKeys.generateNewInstance(globalPublicKey, user);
        user.addUserKeys(userKeys);
        return userKeysRepository.store(userKeys);
    }
}
