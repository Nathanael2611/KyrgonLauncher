package fr.arinonia.launcherlib;

import java.io.File;

import fr.arinonia.launcherlib.authlib.Auth;
import fr.arinonia.launcherlib.authlib.exceptions.AuthenticationUnavailableException;
import fr.arinonia.launcherlib.authlib.exceptions.RequestException;
import fr.arinonia.launcherlib.authlib.responses.AuthenticationResponse;
import fr.arinonia.launcherlib.launchlib.Launcher;
import fr.arinonia.launcherlib.launchlib.exceptions.LaunchException;
import fr.arinonia.launcherlib.launchlib.exceptions.MinecraftFolderInvalid;
import fr.arinonia.launcherlib.updater.Game;
import fr.arinonia.launcherlib.updater.Version;
import fr.arinonia.launcherlib.utils.MinecraftFolder;
import fr.arinonia.launcherlib.utils.Utils;

public class MainTest {

	static Game game;
	public static void main(String[] args) {
		Utils.sendMessageInConsole("pas d'erreur ", false);
		Utils.sendMessageInConsole("erreur", true);
		try {
			Utils.sendMessageInConsole(Utils.getJavaPath(), false);
			AuthenticationResponse response = Auth.authenticate("your.email@***.com", "*********");

			Utils.sendMessageInConsole("Acces token: "+response.getAccessToken(),false);
			Utils.sendMessageInConsole("Account name: "+response.getSelectedProfile().getName(),false);
			Utils.sendMessageInConsole("Account id: "+response.getSelectedProfile().getUUID(),false);

			File gameDir = new File(System.getenv("APPDATA"), ".MaLibLauncher");
			File assetsFolder = new File(gameDir,"assets");

			MinecraftFolder folder = new MinecraftFolder(gameDir, assetsFolder, new File(gameDir, "libraries"), new File(gameDir, "natives"), new File(gameDir, "minecraft.jar"), null, null);

			Version version = new Version.Builder("1.7.10").setForgeVersion("10.13.4.1614").setVersionType(Version.VersionType.FORGE).build();
			Game game = version.update(new Launcher(folder));
			Utils.sendMessageInConsole("", false);
			
			Launcher launcher = new Launcher(folder);
			try {
				launcher.launchGame(game);
			} catch (MinecraftFolderInvalid e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LaunchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			} catch (RequestException | AuthenticationUnavailableException e) {
			e.printStackTrace();
		}
	}

}
