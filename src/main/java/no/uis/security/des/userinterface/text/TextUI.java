package no.uis.security.des.userinterface.text;

import no.uis.security.des.service.feistel.FeistelCipher;
import no.uis.security.des.userinterface.UI;
import no.uis.security.des.userinterface.text.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

import static no.uis.security.common.utils.LogicalUtils.*;

@Service
public class TextUI implements UI {
    @Autowired
    private FeistelCipher feistelCipher;


    @Override
    public void feistel() {
        try {
            Action action = getAction();
            byte[] text = getText(action);
            byte[] key = getKey();
            byte[] code = action.equals(Action.DECRYPT_HEX) ? feistelCipher.decrypt(text, key) : feistelCipher.encrypt(text, key);
            System.out.printf("The %s in Hex is:%s\nAnd in Plain text is %s", action, byteArrayToStringHex(code), byteArrayToString(code));
        } catch (Exception e) {
            System.err.println("Some error occurred please try again");
            feistel();
        }

    }


    private static byte[] getKey() {
        Scanner scanner = new Scanner(System.in);
        String key;
        do {
            System.out.print("\nPlease enter 16 digit Hex for key:");
            key = scanner.nextLine();
        } while (!stringMatches16DigitsHex(key));
        return hexStringToByteArray(key);
    }

    private static byte[] getText(Action action) {
        Scanner scanner = new Scanner(System.in);

        String text;
        do {
            System.out.printf("\nPlease message for %s:", action);
            text = scanner.nextLine();
        } while (!action.validateInput(text));
        return action.convertTextToByte(text);
    }

    private static Action getAction() {
        Scanner scanner = new Scanner(System.in);

        Action action = null;
        do {
            System.out.printf("\nPlease enter \n(1) for %s \n(2) for %s \n(3) for %s:", Action.ENCRYPT_WITH_HEX_INPUT, Action.ENCRYPT_WITH_STRING_INPUT, Action.DECRYPT_HEX);
            int input = 0;
            try {
                input = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                continue;
            }
            action = input == 1 ? Action.ENCRYPT_WITH_HEX_INPUT : input == 2 ? Action.ENCRYPT_WITH_STRING_INPUT : input == 3 ? Action.DECRYPT_HEX : null;
        } while (action == null);
        return action;
    }
}
