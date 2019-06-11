package fr.nathanael2611.kyrgon.launcher;

import fr.litarvan.openauth.AuthenticationException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import re.alwyn974.openlauncherlib.JavaUtil;
import re.alwyn974.openlauncherlib.LaunchException;
import re.alwyn974.openlauncherlib.external.ExternalLaunchProfile;
import re.alwyn974.openlauncherlib.external.ExternalLauncher;
import re.alwyn974.openlauncherlib.minecraft.*;
import re.alwyn974.openlauncherlib.util.explorer.ExploredDirectory;
import re.alwyn974.openlauncherlib.util.explorer.Explorer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main extends Application  {

    public static final GameVersion KG_VERSION = new GameVersion("1.12.2", GameType.V1_8_HIGHER);
    public static final GameInfos KG_INFOS = new GameInfos("Kyrgon", KG_VERSION, new GameTweak[] {GameTweak.FORGE});
    public static final File KG_DIR = KG_INFOS.getGameDir();
    public static final File USER_INFOS = new File(KG_DIR, "infos.json");
    public static AuthInfos authInfos;
    public static Slider ramSlider;
    public static Stage stage;
    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        if(!KG_DIR.exists()){
            KG_DIR.mkdir();
        }
        UserInfos.setupUserInfoFile();
        String nnnaaammmeee = "connexion";
        try {
            Helpers.auth(UserInfos.getUsername(), UserInfos.getPassword());
            nnnaaammmeee = "sample";
        } catch (Exception e) { }
        Parent root = FXMLLoader.load(getClass().getResource(nnnaaammmeee+".fxml"));
        primaryStage.setTitle("Kyrgon Launcher V2");
        Scene scene = new Scene(root, 1200, 700);
        this.scene = scene;
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(1200);
        primaryStage.setMaxHeight(900);
        primaryStage.setMaxWidth(1400);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.getScene().getStylesheets().add((this.getClass().getResource("style.css").toExternalForm()));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Launch Minecraft
     */
    public static void launchMinecraft() throws LaunchException {
        ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(KG_INFOS, GameFolder.BASIC, authInfos);
        int minRam = 512;
        profile.getVmArgs().addAll(Arrays.asList("-Xms" + minRam + "M", "-Xmx" + (int) (UserInfos.getRam()*1000) + "M"));
        ExternalLauncher launcher = new ExternalLauncher(profile);
        JavaUtil.setjavacustom(true);
        ExploredDirectory gameDir = Explorer.dir(Main.KG_DIR);
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            JavaUtil.setJavaCommandLauncher(gameDir.sub("JRE\\bin").get().getPath() + "\\java");
        } else {
            JavaUtil.setJavaCommandLauncher(gameDir.sub("JRE/bin").get().getPath() + "/java");
        }
        Process p = launcher.launch();
        System.exit(0);
    }

    public static void switchScene(String fxmlFile)
    {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Parent root;
        try {
            root = (Parent)loader.load();
            Main.stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
