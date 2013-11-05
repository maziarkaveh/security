package no.uis.security.dsa.ui.entities.impl;

import no.uis.security.common.utils.UIUtils;
import no.uis.security.dsa.model.User;
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
public class UserUI extends AbstractEntityUi {
    @Autowired
    private UserService userService;

    @Override
    public void menu() {
        System.out.printf("\n\nUsers\nPlease enter\n\t%d)list of users \n\t%d)Add new user\n\t%d)return\n", getListNumber(), getAddNewNumber(), getReturnNumber());
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
        String username = no.uis.security.common.utils.UIUtils.getText("Username:", "");
        String firstName = no.uis.security.common.utils.UIUtils.getText("First name:", "");
        String lastName = no.uis.security.common.utils.UIUtils.getText("Last name:", "");
        User user = new User(username, firstName, lastName);
        user= userService.save(user);
        System.out.println("New user has been saved");
        System.out.println(user);

    }

    @Override
    public void listOfEntities() {
        System.out.println("Users");
        for (User user : userService.getAll()) {
            System.out.println(user);
        }
    }


}
