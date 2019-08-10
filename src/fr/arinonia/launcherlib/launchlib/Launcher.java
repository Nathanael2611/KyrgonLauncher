package fr.arinonia.launcherlib.launchlib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.arinonia.launcherlib.authlib.Auth;
import fr.arinonia.launcherlib.launchlib.exceptions.LaunchException;
import fr.arinonia.launcherlib.launchlib.exceptions.MinecraftFolderInvalid;
import fr.arinonia.launcherlib.launchlib.utils.ClassConstructor;
import fr.arinonia.launcherlib.updater.Game;
import fr.arinonia.launcherlib.updater.utils.FileUtils;
import fr.arinonia.launcherlib.utils.MinecraftFolder;
import fr.arinonia.launcherlib.utils.OSUtil;

public class Launcher {

    private MinecraftFolder minecraftFolder;
   
    public Launcher(MinecraftFolder minecraftFolder){
        this.minecraftFolder = minecraftFolder;
    }

    public MinecraftFolder getMinecraftFolder() {
        return minecraftFolder;
    }

    public static int ram = 1000;

    public Process launchGame(Game game) throws MinecraftFolderInvalid, LaunchException
    {
    	if(minecraftFolder == null)throw new MinecraftFolderInvalid(minecraftFolder);
    	
    	ClassConstructor constructor = new ClassConstructor();
    	System.out.println(FileUtils.Explorateur.dir(minecraftFolder.getGameFolder()).sub("libraries").allRecursive().files().match("^(.*\\.((jar)$))*$").get());
    	constructor.add(FileUtils.Explorateur.dir(minecraftFolder.getGameFolder()).sub("libraries").allRecursive().files().match("^(.*\\.((jar)$))*$").get());
    	constructor.add(FileUtils.Explorateur.dir(minecraftFolder.getGameFolder()).get("minecraft.jar"));
    
        String mainClass = game.getMainClass();
    	String classpath = constructor.make();
    	
    	
        List<String> args = getArgs(game);
        List<String> vmArgs = new ArrayList<String>();
        vmArgs.add("-XX:-UseAdaptiveSizePolicy");
        vmArgs.add("-XX:+UseConcMarkSweepGC");
        vmArgs.add("-Djava.library.path=" + FileUtils.Explorateur.dir(minecraftFolder.getGameFolder()).sub("natives/").get().getAbsolutePath());
        vmArgs.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
        vmArgs.add("-Dfml.ignorePatchDiscrepancies=true");
        vmArgs.add("-Xmx" + ram +"G");
        ProcessBuilder builder = new ProcessBuilder();
        ArrayList<String> commands = new ArrayList<String>();
        args.add("--tweakClass");
        args.add(game.getTweaker());
        args.add("--accessToken");
        args.add(Auth.getToken());
        
        commands.add(getJavaCommand());
        commands.addAll(vmArgs);

        commands.add("-cp");
        commands.add(classpath);
        commands.add(mainClass);
        commands.addAll(getArgs(game));
        builder.directory(minecraftFolder.getGameFolder());
        builder.command(commands);
        String entireCommand = "";
        for (String command : commands) entireCommand += command + " ";
        System.out.println(entireCommand);

        try {
            return builder.start();
		} catch (IOException e) {
e.printStackTrace();            throw new LaunchException
("Cannot launch !", e);
		}
        
    }
    
    public ArrayList<String> getArgs(Game game)
    {
        ArrayList<String> arguments = new ArrayList<String>();
        arguments.add("--username=" + Auth.getCurrentProfile().getName());
        arguments.add("--accessToken");
        arguments.add(Auth.getToken());
        arguments.add("--version");
        arguments.add("1.12.2");
        arguments.add("--gameDir");
        arguments.add(minecraftFolder.getGameFolder().getAbsolutePath());
        arguments.add("--assetsDir");
        arguments.add("assets");
        arguments.add("--assetIndex");
        arguments.add("1.12");
        arguments.add("--userProperties");
        arguments.add("{}");
        arguments.add("--uuid");
        arguments.add(Auth.getCurrentProfile().getUUID().toString());
        arguments.add("--userType");
        arguments.add("legacy");
        arguments.add("--tweakClass");
        arguments.add(game.getTweaker());
        arguments.add("--accessToken");
        arguments.add(Auth.getToken());

        return arguments;	
    }
    
    public String getJavaCommand()
    {
    	 if (System.getProperty("os.name").toLowerCase().contains("win"))
             return "\"" + System.getProperty("java.home") + "\\bin\\java" + "\"";

         return System.getProperty("java.home") + "/bin/java";
    }
}
