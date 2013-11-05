package no.uis.security.dsa.ui.entities.impl;

import no.uis.security.common.utils.UIUtils;
import no.uis.security.dsa.model.GlobalPublicKey;
import no.uis.security.dsa.model.User;
import no.uis.security.dsa.service.GlobalPublicKeyService;
import no.uis.security.dsa.service.UserService;
import no.uis.security.dsa.ui.entities.AbstractEntityUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 22:33
 * To change this template use File | Settings | File Templates.
 */
@Service
public class GlobalPublicKeyUI extends AbstractEntityUi {
    @Autowired
    private GlobalPublicKeyService globalPublicKeyService;

    @Override
    public void menu() {
        System.out.printf("\n\nGlobal public keys\nPlease enter\n\t%d)list of global public keys \n\t%d)Generate new global public key\n\t%d)return\n", getListNumber(), getAddNewNumber(), getReturnNumber());
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
        System.out.println("Generate new global public key");

        GlobalPublicKey globalPublicKey = globalPublicKeyService.save(GlobalPublicKey.generateNewInstance());
        System.out.println("New globalPublicKey has been saved");
        System.out.println(globalPublicKey);

    }

    @Override
    public void listOfEntities() {
        System.out.println("Global public keys");
        for (GlobalPublicKey globalPublicKey : globalPublicKeyService.getAll()) {
            System.out.println(globalPublicKey);
        }
    }


}
