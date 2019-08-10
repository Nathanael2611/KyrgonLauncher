package fr.nathanael2611.kyrgon.launcher.launch;

import fr.arinonia.launcherlib.authlib.exceptions.AuthenticationUnavailableException;
import fr.arinonia.launcherlib.authlib.exceptions.RequestException;
import fr.arinonia.launcherlib.launchlib.Launcher;
import fr.arinonia.launcherlib.launchlib.exceptions.MinecraftFolderInvalid;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;
import fr.nathanael2611.kyrgon.launcher.UserInfos;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.internal.InternalLaunchProfile;
import fr.theshark34.openlauncherlib.internal.InternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;

import java.io.File;
import java.util.Arrays;

public class LaunchManager {


    private boolean openLauncherLib = true;

    public void setOpenLauncherLib(boolean openLauncherLib) {
        this.openLauncherLib = openLauncherLib;
    }

    public void launch(){
        if(!openLauncherLib) {
            KyrgonLauncher.getInstance().getUserInfos().setUseOpenLauncherLib(false);
            if (KyrgonLauncher.getInstance().getGame() == null) {
                System.out.println("bon, pas fou, le Game est null !!! :C");
            }
            Launcher launcher = new Launcher(
                    KyrgonLauncher.getInstance().getMinecraftFoler()
            );

            try {
                launcher.launchGame(KyrgonLauncher.getInstance().getGame());
            } catch (MinecraftFolderInvalid | NullPointerException e) {
                e.printStackTrace();
                System.out.println("eh bah c'est null :/");
            } catch (fr.arinonia.launcherlib.launchlib.exceptions.LaunchException e) {
                e.printStackTrace();
            }
        }else {
            KyrgonLauncher.getInstance().getUserInfos().setUseOpenLauncherLib(true);

            KyrgonLauncher launcher = KyrgonLauncher.getInstance();
            GameInfos infos = new GameInfos("Kyrgon/minecraft/", new GameVersion("1.12.2", GameType.V1_8_HIGHER), new GameTweak[]{GameTweak.FORGE});
            AuthInfos authInfos = new AuthInfos(launcher.getAccountManager().getAuthResponse().getSelectedProfile().getName(), launcher.getAccountManager().getAuthResponse().getAccessToken(), launcher.getAccountManager().getAuthResponse().getSelectedProfile().getUUID().toString());
            ExternalLaunchProfile profile = null;
            try {
                profile = MinecraftLauncher.createExternalProfile(
                        infos,
                        new GameFolder(
                                "/assets/",
                                "/libraries/",
                                "/natives/",
                                "/minecraft.jar"
                        ),
                        authInfos
                );
            } catch (LaunchException e) {
                e.printStackTrace();
            }
            final int minRam = 512;
            profile.getVmArgs().addAll(Arrays.asList("-Xms" + minRam + "M", "-Xmx" + (int)(Launcher.ram * 1000.0) + "M"));
            ExternalLauncher l = new ExternalLauncher(profile);
            try {
                l.launch();
            } catch (LaunchException e) {
                e.printStackTrace();
            }
        }

    }

}
