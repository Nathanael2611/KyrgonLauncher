package fr.nathanael2611.kyrgon.launcher.update;

import fr.arinonia.launcherlib.launchlib.Launcher;
import fr.arinonia.launcherlib.updater.Game;
import fr.arinonia.launcherlib.updater.LightVersion;
import fr.arinonia.launcherlib.updater.Version;
import fr.arinonia.launcherlib.updater.VersionDownloader;
import fr.arinonia.launcherlib.utils.MinecraftFolder;
import fr.nathanael2611.kyrgon.launcher.Helpers;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;
import fr.nathanael2611.kyrgon.launcher.config.LauncherConfig;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

public class UpdateManager {

    private String updateMessage = "";

    public String getUpdateMessage(){
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    private VersionDownloader actualVersionDownloader;

    public VersionDownloader getActualVersionDownloader() {
        return actualVersionDownloader;
    }

    public void setActualVersionDownloader(VersionDownloader actualVersionDownloader) {
        this.actualVersionDownloader = actualVersionDownloader;
    }

    public void updateMinecraftFiles(){
        updateMessage = "Verifying assets files...";
        KyrgonLauncher kyrgonLauncher = KyrgonLauncher.getInstance();
        MinecraftFolder folder = kyrgonLauncher.getMinecraftFoler();

        Version version = kyrgonLauncher.getConfig().getVersion();
        updateMessage = "Downloading assets files...";

        kyrgonLauncher.setGame(
                version.update(new Launcher(kyrgonLauncher.getMinecraftFoler()))
        );
    }

    public float getDownloadPercents(){
        return getActualVersionDownloader().getDownloadManager().getDownloadPercentage();
    }

    public int downloadedMods = 0;
    public void updateMods(){
        KyrgonLauncher launcher = KyrgonLauncher.getInstance();
        File gameDir = launcher.getGameDir();
        File modDir = new File(gameDir, "/mods/");
        modDir.mkdir();
        LauncherConfig config = launcher.getConfig();
        if(modDir.isFile()){
            Helpers.sendErrorAndClose("Heu, ton dossier mod c'est un fichier ptdr :')");
        }
        for(File file : Objects.requireNonNull(modDir.listFiles())){

                System.out.println(file);
                System.out.println(config.isModContained(file));
                if(!config.isModContained(file)){
                    System.out.println("deleting "+file);
                    try {
                        Files.delete(file.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    file.delete();
                }

        }
        for(LauncherConfig.Mod mod : config.getMods()){
            if(!config.isModAlreadyDownload(mod)){
                try {
                    FileUtils.copyURLToFile(
                            new URL(mod.getDownloadLink()),
                            new File(modDir, mod.getFileName())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                downloadedMods++;
            }else{
                downloadedMods++;
            }
        }
    }

}
