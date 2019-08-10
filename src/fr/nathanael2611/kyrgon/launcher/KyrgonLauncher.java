package fr.nathanael2611.kyrgon.launcher;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import fr.arinonia.launcherlib.authlib.exceptions.AuthenticationUnavailableException;
import fr.arinonia.launcherlib.authlib.exceptions.RequestException;
import fr.arinonia.launcherlib.launchlib.Launcher;
import fr.arinonia.launcherlib.updater.Game;
import fr.arinonia.launcherlib.utils.MinecraftFolder;
import fr.nathanael2611.kyrgon.launcher.auth.AccountManager;
import fr.nathanael2611.kyrgon.launcher.config.LauncherConfig;
import fr.nathanael2611.kyrgon.launcher.launch.LaunchManager;
import fr.nathanael2611.kyrgon.launcher.ui.GraphicsManager;
import fr.nathanael2611.kyrgon.launcher.update.UpdateManager;

import javax.swing.*;
import java.io.File;

public class KyrgonLauncher {

    private static KyrgonLauncher instance;

    /* The launcher config manager */
    private LauncherConfig config;


    /* The launcher dir */
    private final File launcherDir = new File(System.getenv("APPDATA"), ".Kyrgon");;
    /* The game dir */
    private final File gameDir = new File(launcherDir, "/minecraft/");
    /* The assets dir */
    private final File assetsFolder = new File(gameDir, "/assets/");
    /* The user-infos file */
    private UserInfos userInfos = new UserInfos(new File(launcherDir, "userinfos.json"));

    private final MinecraftFolder minecraftFolder = new MinecraftFolder(
            gameDir, assetsFolder,
            new File(gameDir, "libraries"),
            new File(gameDir, "natives"),
            new File(gameDir, "minecraft.jar"),
            null, null
    );

    private Game game;

    /* The update manager */
    private UpdateManager updateManager;
    /* The launch manager */
    private LaunchManager launchManager;
    /* The account manager */
    private AccountManager accountManager;
    /* The UI Manager */
    private GraphicsManager graphicsManager;


    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
        );
        new KyrgonLauncher();

    }

    public KyrgonLauncher(){
        launcherDir.mkdir();
        gameDir.mkdir();
        instance = this;
        Helpers.sendMessageInConsole("Starting the launcher...", false);
        getUserInfos().setupUserInfoFile();
        /* Define the config manager */
        config = new LauncherConfig();
        getGraphicsManager().start();




    }

    public LauncherConfig getConfig() {
        return config;
    }


    public static KyrgonLauncher getInstance() {
        return instance;
    }

    public File getLauncherDir(){
        return launcherDir;
    }
    public File getGameDir() {
        return gameDir;
    }
    public File getAssetsFolder() {
        return assetsFolder;
    }
    public MinecraftFolder getMinecraftFoler(){
        return minecraftFolder;
    }

    public Game getGame() {
        if(game == null){
            Helpers.sendErrorAndClose(
                    "No Game instance was found !\nPLEASE LAUNCH THE UPDATE BEFORE LAUNCHING GAME !"
            );
        }
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public UpdateManager getUpdateManager() {
        if(updateManager == null){
            updateManager = new UpdateManager();
        }
        return updateManager;
    }

    public LaunchManager getLaunchManager(){
        if(launchManager == null){
            launchManager = new LaunchManager();
        }
        return launchManager;
    }

    public AccountManager getAccountManager() {
        if(accountManager == null){
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    public GraphicsManager getGraphicsManager() {
        if(graphicsManager == null){
            graphicsManager = new GraphicsManager();
        }
        return graphicsManager;
    }

    public UserInfos getUserInfos() {
        return userInfos;
    }
}
