package fr.nathanael2611.kyrgon.launcher.config;

import fr.arinonia.launcherlib.updater.Version;
import fr.nathanael2611.kyrgon.launcher.Helpers;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.nio.ch.IOUtil;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LauncherConfig {

    private JSONObject configObj;

    private Version version;

    private String url = "http://kyrgon.fr/launcher/launcher.json";

    public LauncherConfig() {
        Helpers.sendMessageInConsole("Getting the launcher config from: " + url + "...", false);
        try {
            this.configObj = new JSONObject(
                    IOUtils.toString(new URL(url), "UTF-8")
            );
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossile de lire le fichier de configuration web.");
            System.exit(0);
        }
        getMods();

    }


    public String getForgeVersion() {
        return getStringOrException("forge-version");
    }
    public String getMinecraftVersion(){
        return getStringOrException("minecraft-version");
    }

    public void sendPropertyNotFoundMessageAndClose(String property){
        Helpers.sendErrorAndClose(
                "The property \"" + property + "\" was not found in config... Stopping the launcher..."
        );
    }

    public Version getVersion(){
        if(version == null){
            Helpers.sendMessageInConsole(
                    "Getting the version...", false
            );
            version = new Version.Builder(
                    getMinecraftVersion()
            ).setForgeVersion(
                    getForgeVersion()
            ).setVersionType(
                    Version.VersionType.FORGE
            ).build();
        }
        return version;
    }

    public String getStringOrException(String string){
        if(configObj.isNull(string)){
            sendPropertyNotFoundMessageAndClose(string);
        }
        return configObj.getString(string);
    }

    private List<Mod> mods = new ArrayList<>();

    public List<Mod> getMods(){
        mods.clear();
        JSONArray modsArray = configObj.getJSONArray("mods");
        for(int i = 0; i < modsArray.length(); i++){
            JSONObject modObj = modsArray.getJSONObject(i);
            mods.add(
                    new Mod(
                            modObj.getString("name"),
                            modObj.getString("url"),
                            modObj.getString("sha1")
                    )
            );
        }
        return mods;
    }

    public boolean isModContained(File file){
        for(Mod mod : mods){
            if(toSha1(file).equals(mod.getSha1())){
                return true;
            }
        }
        return false;
    }

    public boolean isModAlreadyDownload(Mod mod){
        for(File file : new File(KyrgonLauncher.getInstance().getGameDir(), "/mods/").listFiles()){
            if(toSha1(file).equals(mod.getSha1())){
                return true;
            }
        }
        return false;
    }

    public static class Mod{
        private String fileName;
        private String downloadLink;
        private String sha1;

        public Mod(String fileName, String downloadLink, String sha1) {
            this.fileName = fileName;
            this.downloadLink = downloadLink;
            this.sha1 = sha1;
        }

        public String getFileName() {
            return fileName;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public String getSha1() {
            return sha1;
        }
    }

    public static String toSha1(File file){
        try {
            FileInputStream stream = new FileInputStream(file);
            String sha1 = DigestUtils.sha1Hex(stream);
            stream.close();
            return sha1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
