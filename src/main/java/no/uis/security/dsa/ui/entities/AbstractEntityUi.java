package no.uis.security.dsa.ui.entities;

import no.uis.security.common.utils.UIUtils;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 05.11.13
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEntityUi implements EntityUi {



    @Override
    public void display() {
        int i = 0;
        do {
            menu();
            i = UIUtils.getOneDigitNumber("Enter number");
            if (i == getAddNewNumber()) {
                newEntity();
            } else if (i == getListNumber()) {
                listOfEntities();
            }

        } while (i != getReturnNumber());
    }

    protected abstract int getReturnNumber();

    protected abstract int getListNumber();

    protected abstract int getAddNewNumber();
}
