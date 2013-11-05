package no.uis.security.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Scanner;

public abstract class UIUtils {


    public static String getText(String displayMessage, String validationRegex) {
        Scanner scanner = new Scanner(System.in);

        String text;
        do {
            System.out.printf("\n%s", displayMessage);
            text = scanner.nextLine();
        } while (!StringUtils.isEmpty(validationRegex) && !text.matches(validationRegex));
        return text;
    }

    public static int getOneDigitNumber(String displayMessage) {
        Scanner scanner = new Scanner(System.in);

        String text;
        do {
            System.out.printf("\n%s:", displayMessage);
            text = scanner.nextLine();
        } while (!text.matches("^\\d$"));
        return Integer.parseInt(text);
    }

    public static long getDigitNumber(String displayMessage) {
        Scanner scanner = new Scanner(System.in);

        String text;
        do {
            System.out.printf("\n%s:", displayMessage);
            text = scanner.nextLine();
        } while (!text.matches("^\\d+$"));
        return Long.parseLong(text);
    }
}