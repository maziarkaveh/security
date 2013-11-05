package no.uis.security.dsa.ui.entities.impl;

import no.uis.security.common.utils.UIUtils;
import no.uis.security.dsa.model.GlobalPublicKey;
import no.uis.security.dsa.model.User;
import no.uis.security.dsa.model.UserKeys;
import no.uis.security.dsa.service.GlobalPublicKeyService;
import no.uis.security.dsa.service.UserKeysService;
import no.uis.security.dsa.service.UserService;
import no.uis.security.dsa.ui.entities.AbstractEntityUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static no.uis.security.common.utils.UIUtils.getDigitNumber;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 22:33
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserKeysUI extends AbstractEntityUi {
    @Autowired
    private UserKeysService userkeysService;
    @Autowired
    private UserService userService;
    @Autowired
    private GlobalPublicKeyService globalPublicKeyService;

    @Override
    public void menu() {
        System.out.printf("\n\nUser keys\nPlease enter\n\t%d)list of User keys \n\t%d)Generate new user keys\n\t%d)return\n", getListNumber(), getAddNewNumber(), getReturnNumber());
    }

    @Override
    protected int getReturnNumber() {
        return 3;
    }

    @Override
    protected int getListNumber() {
        return 1;
    }

    @Override
    protected int getAddNewNumber() {
        return 2;
    }

    @Override
    public void newEntity() {
        System.out.println("New user");
        long userID = getDigitNumber("Enter User Id");
        User user = null;
        try {
            user = userService.get(userID);
        } catch (Exception e) {
            System.err.printf("User with id %d does not exist\n", userID);
            return;
        }
        GlobalPublicKey lastGlobalPublicKey = globalPublicKeyService.getLastGlobalPublicKey();
        UserKeys userKeys = UserKeys.generateNewInstance(lastGlobalPublicKey, user);
        userKeys = userkeysService.save(userKeys);
        System.out.println("New user keys has been saved");
        System.out.println(userKeys);

    }

    @Override
    public void listOfEntities() {
        System.out.println("User Keys");
        for (UserKeys userKeys : userkeysService.getAll()) {
            System.out.println(userKeys);
        }
    }


}
