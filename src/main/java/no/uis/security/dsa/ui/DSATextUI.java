package no.uis.security.dsa.ui;

import no.uis.security.common.utils.UIUtils;
import no.uis.security.dsa.ui.entities.EntityUi;
import no.uis.security.dsa.ui.entities.impl.MessageUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service

public class DSATextUI implements UI {
    @Autowired
    @Qualifier("userUI")
    private EntityUi userUI;
    @Autowired
    @Qualifier("userKeysUI")
    private EntityUi userKeysUI;
    @Autowired
    @Qualifier("globalPublicKeyUI")
    private EntityUi globalPublicKeyUI;
    @Autowired
    @Qualifier("messageUI")
    private EntityUi messageUI;

    @Override
    public void dsa() {

        int i = 0;

        do {
            EntityUi current;
            System.out.printf("\n\nUsers\nPlease enter\n\t%d)Users \n\t%d)Global public key\n\t%d)User keys \n\t%d)Messages\n\t%d)List o messages with verify status\n\t%d)return\n", 1, 2, 3, 4, 5, 6);
            i = UIUtils.getOneDigitNumber("Please enter a number");
            switch (i) {
                case 1:
                    current = userUI;
                    break;
                case 2:
                    current = globalPublicKeyUI;
                    break;
                case 3:
                    current = userKeysUI;
                    break;
                case 4:
                    current = messageUI;
                    break;
                default:
                    current = null;
            }
            if (current != null) {
                current.display();
            } else if (i == 5) {
                ((MessageUI) messageUI).listOfMessagesWithVerifyStatus();
            }

        } while (i != 6);
    }


}
