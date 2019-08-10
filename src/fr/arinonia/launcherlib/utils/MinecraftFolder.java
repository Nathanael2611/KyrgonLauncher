package fr.arinonia.launcherlib.utils;

import java.io.File;
import java.net.URL;
/*
*@Author Arinonia
*LibLauncher-V1
*
*Class for get and set the game folders
*
*/
public class MinecraftFolder {
	
    private File gameFolder;
    private File modsFolder;
    private File librariesFolder;
    private File nativesFolder;
    private File assetsFolder;
    private File clientJarFile;
    private String clientJarSHA1;
    private URL clientJarURL;

    public MinecraftFolder(File gameFolder, File assetsFolder, File librariesFolder, File nativesFolder, File clientJarFile, URL clientJarURL, String clientJarSHA1) {
        this.gameFolder = gameFolder;
        this.librariesFolder = librariesFolder;
        this.nativesFolder = nativesFolder;
        this.assetsFolder = assetsFolder;
        this.clientJarFile = clientJarFile;
        this.clientJarURL = clientJarURL;
        this.clientJarSHA1 = clientJarSHA1;
    }

    public File getGameFolder() {return this.gameFolder;}
    public void setGameFolder(File gameFolder) {this.gameFolder = gameFolder;}
    public File getModsFolder() {return this.modsFolder;}
    public void setModsFolder(File modsFolder) {this.modsFolder = modsFolder;}
    public File getLibrariesFolder() {return this.librariesFolder;}
    public void setLibrariesFolder(File librariesFolder) {this.librariesFolder = librariesFolder;}
    public File getNativesFolder() {return this.nativesFolder;}
    public void setNativesFolder(File nativesFolder) {this.nativesFolder = nativesFolder;}
    public File getAssetsFolder() {return this.assetsFolder;}
    public void setAssetsFolder(File assetsFolder) {this.assetsFolder = assetsFolder;}
    public File getClientJarFile() {return this.clientJarFile;}
    public void setClientJarFile(File clientJarFile) {this.clientJarFile = clientJarFile;}
    public String getClientJarSHA1() {return this.clientJarSHA1;}
    public void setClientJarSHA1(String clientJarSHA1) {this.clientJarSHA1 = clientJarSHA1;}
    public URL getClientJarURL() {return this.clientJarURL;}
    public void setClientJarURL(URL clientJarURL) {this.clientJarURL = clientJarURL;}
}

