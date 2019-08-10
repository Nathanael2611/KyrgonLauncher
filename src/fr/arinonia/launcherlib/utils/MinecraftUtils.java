package fr.arinonia.launcherlib.utils;

public class MinecraftUtils {
    public MinecraftUtils() {
    }

    public static boolean isMinimum1_8(String version) {
        String[] v = version.split("\\.");
        if (v[1].length() >= 1 && isInteger(v[1])) {
            int i = new Integer(v[1]);
            return i >= 8;
        } else {
            return false;
        }
    }

    public static boolean isMaximum1_5_2(String version) {
        String[] v = version.split("\\.");
        if (v[1].length() >= 1 && isInteger(v[1])) {
            int i = new Integer(v[1]);
            return i <= 5;
        } else {
            return false;
        }
    }

    public static boolean is1_7_2_Lower(String version) {
        String[] v = version.split("\\.");
        if (v[1].length() >= 1 && isInteger(v[1])) {
            int i = new Integer(v[1]);
            if (i == 6) {
                return true;
            } else if (i > 7 && (i < 5 || i > 7)) {
                return false;
            } else if (v.length == 3 && isInteger(v[2])) {
                int e = new Integer(v[2]);
                if (i == 5 && e >= 3) {
                    return true;
                } else {
                    return i == 7 && e <= 2;
                }
            } else {
                return v.length != 2 || i != 5;
            }
        } else {
            return false;
        }
    }

    public static boolean isInteger(String s) {
        try {
            new Integer(s);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }
}

