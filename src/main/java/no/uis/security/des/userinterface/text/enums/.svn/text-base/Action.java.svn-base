package no.uis.security.des.userinterface.text.enums;

import static no.uis.security.des.utils.LogicalUtils.hexStringToByteArray;
import static no.uis.security.des.utils.LogicalUtils.stringMatchesHex;

public enum Action {
    ENCRYPT_WITH_HEX_INPUT("encrypt with HEX input") {
        @Override
        public byte[] convertTextToByte(String text) {
            return hexStringToByteArray(text);
        }

        @Override
        public boolean validateInput(String text) {
            return stringMatchesHex(text);
        }
    },
    ENCRYPT_WITH_STRING_INPUT("encrypt with String input") {
        @Override
        public byte[] convertTextToByte(String text) {
            return text.getBytes();
        }

        @Override
        public boolean validateInput(String text) {
            return text.length() > 5;
        }
    },
    DECRYPT_HEX("decrypt Hex value") {
        @Override
        public byte[] convertTextToByte(String text) {
            return hexStringToByteArray(text);
        }

        @Override
        public boolean validateInput(String text) {
            return stringMatchesHex(text);
        }
    };
    private final String name;

    String getName() {
        return name;
    }

    private Action(String name) {
        this.name = name;
    }

    public abstract byte[] convertTextToByte(String text);

    public abstract boolean validateInput(String text);

    @Override
    public String toString() {
        return name;
    }
}
