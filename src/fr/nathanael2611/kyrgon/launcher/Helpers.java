package fr.nathanael2611.kyrgon.launcher;

import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import re.alwyn974.openlauncherlib.minecraft.AuthInfos;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contain a lot of useful features used in a lot of mod parts
 *
 * @author Nathanael2611
 */
public class Helpers {
    /**
     * Useful for read the content of a file.
     */
    public static String readFileToString(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();
            return stringBuilder.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "ERROR";
    }

    /**
     * Useful for easily create a List<String> by enter a String Collection in the constructor
     */
    public static List<String> createListFrilStrings(String... str){
        return new ArrayList<>(Arrays.asList(str));
    }

    public static void auth(String username, String password) throws AuthenticationException {
        Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
        AuthResponse response = null;
        response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
        Main.authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(), response.getSelectedProfile().getId());
        if (Main.authInfos.getUuid().equalsIgnoreCase("b19d7c42-38d1-4b03-a325-7f5bdade355c")||Main.authInfos.getUuid().equalsIgnoreCase("58077d6b-2e72-431e-903e-35e29512da85"))isLegendairees();
    }






    public static void isLegendairees(){
        JOptionPane.showMessageDialog(new JPanel(), "35 PAS 25 !!!");
    }

}
