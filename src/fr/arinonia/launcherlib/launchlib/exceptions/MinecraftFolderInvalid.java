package fr.arinonia.launcherlib.launchlib.exceptions;

import fr.arinonia.launcherlib.utils.MinecraftFolder;

@SuppressWarnings("serial")
public class MinecraftFolderInvalid extends Exception {

	private MinecraftFolder folder;
	
	public MinecraftFolderInvalid(MinecraftFolder folder) {
		this.folder = folder;
	}

	public String getError() {
		return "MinecraftFolder == null";
	}

	public String getErrorMessage() {
		return "Error, the Minecraft Folder is null";
	}

	public String getErrorCause() {
		return folder.getGameFolder().getAbsolutePath() + " doesn't exists ! Aborting launch !";
	}
	

}
