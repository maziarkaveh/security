package no.uis.security.rsa.userinterface.text;

import no.uis.security.rsa.userinterface.text.enums.Action;
import no.uis.security.rsa.service.impl.RSAEncryptionService;
import no.uis.security.rsa.userinterface.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

import static no.uis.security.common.utils.LogicalUtils.*;

@Service
public class RsaTextUI implements UI {


    @Autowired
    private RSAEncryptionService rsaEncryptionService;


    @Override
    public void rsa() {
        Action action = null;
        while (true) {
            try {
                action = getAction();
                if (action.equals(Action.GENERATE_RSA)) {
                    System.out.println();
                    System.out.println(rsaEncryptionService.generateRSAKeys(getNumberOfBytes()));
                    System.out.println();
                    continue;
                }
                byte[] text = getText(action);
                byte[] key = getKey();
                byte[] modulus = getModulus();
                byte[] code = action.equals(Action.DECRYPT_HEX) ? rsaEncryptionService.decrypt(text, key, modulus) : rsaEncryptionService.encrypt(text, key, modulus);
                System.out.printf("The %s in Hex is:%s\nAnd in Plain text is %s", action, byteArrayToStringHex(code), byteArrayToString(code));
            } catch (Exception e) {
                if (action == null) {
                    break;
                }
                System.err.println("Some error occurred please try again");
                rsa();
            }
        }

    }


    private static byte[] getKey() {
        Scanner scanner = new Scanner(System.in);
        String key;
        do {
            System.out.print("\nPlease enter     Hex for key:");
            key = scanner.nextLine();
        } while (!stringMatchesHex(key));
        return hexStringToByteArray(key);
    }

    private static byte[] getModulus() {
        Scanner scanner = new Scanner(System.in);
        String key;
        do {
            System.out.print("\nPlease enter     Hex for Modulus:");
            key = scanner.nextLine();
        } while (!stringMatchesHex(key));
        return hexStringToByteArray(key);
    }

    private static int getNumberOfBytes() {
        Scanner scanner = new Scanner(System.in);
        String key;
        do {
            System.out.print("\nPlease enter number of bytes for generate RSA keys (note more than 10 might takes a lot of time):");
            key = scanner.nextLine();
        } while (!Action.GENERATE_RSA.validateInput(key));
        return Integer.parseInt(key);
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

        System.out.printf("\nPlease enter\n(1) for %s \n(2) for %s \n(3) for %s  \n" +
                "(4) for %s  \netc for exit:", Action.ENCRYPT_WITH_HEX_INPUT, Action.ENCRYPT_WITH_STRING_INPUT, Action.DECRYPT_HEX, Action.GENERATE_RSA);
        int input = 0;
        input = scanner.nextInt();
        action = input == 1 ? Action.ENCRYPT_WITH_HEX_INPUT : input == 2 ? Action.ENCRYPT_WITH_STRING_INPUT : input == 3 ? Action.DECRYPT_HEX : input == 4 ? Action.GENERATE_RSA : null;
        return action;
    }


}
