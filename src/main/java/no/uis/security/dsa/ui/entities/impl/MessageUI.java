package no.uis.security.dsa.ui.entities.impl;

import no.uis.security.common.utils.UIUtils;
import no.uis.security.dsa.model.Message;
import no.uis.security.dsa.model.User;
import no.uis.security.dsa.model.UserKeys;
import no.uis.security.dsa.service.MessageService;
import no.uis.security.dsa.service.UserKeysService;
import no.uis.security.dsa.service.UserService;
import no.uis.security.dsa.ui.entities.AbstractEntityUi;
import no.uis.security.hash.HashService;
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
public class MessageUI extends AbstractEntityUi {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserKeysService userKeysService;
    @Autowired
    private HashService hashService;

    @Override
    public void menu() {
        System.out.printf("\n\nMessages\nPlease enter\n\t%d)list of messages \n\t%d)Add new message\n\t%d)return\n", getListNumber(), getAddNewNumber(), getReturnNumber());
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
        System.out.println("Message user");
        long userKeysID = getDigitNumber("Enter Userkeys Id");
        UserKeys userKeys = null;
        try {
            userKeys = userKeysService.get(userKeysID);
        } catch (Exception e) {
            System.err.printf("userKeys with id %d does not exist\n", userKeysID);
            return;
        }
        String text = UIUtils.getText("Enter a text in one line:", "");

        Message message = Message.generateNewMessage(text, userKeys, hashService);
        message = messageService.save(message);
        System.out.println("New message has been saved");
        System.out.println(message);

    }

    @Override
    public void listOfEntities() {
        System.out.println("Messages");
        for (Message message : messageService.getAll()) {
            System.out.println(message);
        }
    }
     public void listOfMessagesWithVerifyStatus() {
        System.out.println("List Of Messages With Verify Status");
        for (Message message : messageService.getAll()) {
            System.out.println(message);
            System.out.printf("\t Verified by userKey id=%d , verify status is=%s\n\n", message.getFromUserKeys().getId(), message.verify(message.getFromUserKeys(), hashService)?"Verified":"Not verified");
        }
    }

}
