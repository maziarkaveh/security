package no.uis.security.des.utils;


public class CypherUtils {
    public static byte[] permute(byte[] table, boolean[] code) {

        boolean[] result = new boolean[table.length];
        for (int i = 0; i < table.length; i++) {
            result[i] = code[table[i] - 1];
        }

        return LogicalUtils.booleanArrayToByteArray(result);
    }
}
