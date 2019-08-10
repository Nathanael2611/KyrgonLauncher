package fr.nathanael2611.kyrgon.launcher;

import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * This class is used for manage the user login information.
 *
 * @author Nathanael2611
 */
public class UserInfos {

    public final File userInfos;

    public UserInfos(File userInfos){
        this.userInfos = userInfos;
    }

    /**
     * Used for setup the user info-file.
     */
    public void setupUserInfoFile(){
        if(!userInfos.exists()){
            try {
                userInfos.createNewFile();
                FileWriter writer = new FileWriter(userInfos);
                writer.write("{}");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the JSONObject stored in the user info-file.
     */
    public JSONObject getUserInfosObject(){
        setupUserInfoFile();
        JSONObject object = null;
        try {
            object = new JSONObject(Helpers.readFileToString(userInfos));
        } catch (IOException e) {
            userInfos.delete();
            setupUserInfoFile();
            e.printStackTrace();
            return getUserInfosObject();
        }
        return object;
    }

    /**
     * Get the username stored in the user-file.
     */
    public String getUsername(){
        JSONObject object = getUserInfosObject();
        String username = null;
        if(object.isNull("username"))return "";
        try {
            username = IOUtils.toString(Base64.getDecoder().decode(object.getString("username")), String.valueOf(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            username = "";
        }
        return username;
    }

    /**
     * Get the password from the user-file.
     */
    public String getPassword(){
        JSONObject object = getUserInfosObject();
        String password = null;
        if(object.isNull("password"))return "";
        try {
            password = IOUtils.toString(Base64.getDecoder().decode(object.getString("password")), String.valueOf(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            password = "";
        }
        return password;
    }

    public boolean useOpenLauncherLib() {
        JSONObject object = getUserInfosObject();
        boolean useOpenLauncherlib = true;
        if(object.isNull("useOpenLauncherLib"))return useOpenLauncherlib;
        return object.getBoolean("useOpenLauncherLib");
    }

    public void setUseOpenLauncherLib(boolean use){
        JSONObject object = getUserInfosObject();
        object.put("useOpenLauncherLib", use);
        try {
            FileWriter writer = new FileWriter(userInfos);
            writer.write(object.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used for set user infos in the info-file.
     */
    public void setInfos(String username, String password){
        JSONObject object = getUserInfosObject();
        try {
            object.put("username", IOUtils.toString(Base64.getEncoder().encode(username.getBytes()), String.valueOf(StandardCharsets.UTF_8)));
            object.put("password", IOUtils.toString(Base64.getEncoder().encode(password.getBytes()), String.valueOf(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(userInfos);
            writer.write(object.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getRam(){
        JSONObject object = getUserInfosObject();
        if(!(object.getDouble("ram")>=1.5))return 1.5;
        return object.getDouble("ram");
    }

    public void setRam(double ram){
        JSONObject object = getUserInfosObject();
        object.put("ram", ram);
        try {
            FileWriter writer = new FileWriter(userInfos);
            writer.write(object.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used for disconnect the player-account.
     */
    public void disconnect(){
        setInfos("", "");
    }

}