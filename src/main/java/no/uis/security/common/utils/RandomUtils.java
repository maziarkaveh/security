package no.uis.security.common.utils;

public class RandomUtils {
    public static int random(int f, int s) {
        if (s == 0 && f == 0)
            return 0;
        if (f > s)
            return new java.util.Random().nextInt(Math.abs(f - s + 1)) + s;
        else
            return new java.util.Random().nextInt(Math.abs(s - f + 1)) + f;
    }

    public static String randomString() {
        StringBuilder str = new StringBuilder();
        int n = random(6, 15);

        for (int i = 0; i < n; i++)
            str.append((char) random('A', 'z'));
        return str.toString();

    }

    public static String randomStringNumber(int minChar, int maxChar) {
        StringBuilder str = new StringBuilder();
        int n = random(minChar, maxChar);
        for (int i = 0; i < n; i++)
            str.append((char) random('0', '9'));
        return str.toString();

    }

    public static String randomStringNumber() {
        return randomStringNumber(8, 16);

    }

    public static String randomStringNumber(int size) {

        return randomStringNumber(size, size);

    }

    public static String randomString(int minChar, int maxChar) {
        StringBuilder str = new StringBuilder();
        int n = random(minChar, maxChar);

        for (int i = 1; i < n; i++)
            str.append((char) random('A', 'z'));
        return str.toString();

    }

    public static String randomString(int size) {
        return randomString(size, size);

    }

    public static int randomFromParam(int... arr) {
        int random = random(0, arr.length - 1);
        return arr[random];
    }

    public static String randomFromStringParam(String... arr) {
        int random = random(0, arr.length - 1);
        return arr[random];
    }


}




