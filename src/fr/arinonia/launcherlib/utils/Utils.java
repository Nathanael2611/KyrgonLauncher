package fr.arinonia.launcherlib.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

	public static final String AUTH_MOJANG = "https://authserver.mojang.com/";
	
	/**
	 * 
	 * @param id (UUID)
	 * @return to user head skin texture
	 * @throws URISyntaxException (if url is bad)
	 */
	public static URI getHeadUser(String id) throws URISyntaxException {
		URI url = new URI("https://crafatar.com/avatars/"+ id);
		return url;
	}
	
	/**
	 * 
	 * @return to operating system user
	 */
    public static String getJavaPath() {
        return System.getProperty("os.name").toLowerCase().contains("win") ? "\"" + System.getProperty("java.home") + "\\bin\\java" + "\"" : System.getProperty("java.home") + "/bin/java";
    }
    /**
     * 
     * @param message: message in console
     * @param error if true : error else : simple message
     * 
     * 
     */
    public static void sendMessageInConsole(String message, boolean error) {
    	LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String str = dateTime.format(timeFormatter);
    	if(error) {
    		System.err.println("["+str+"]" + "[MinecraftDownloader] " + message);
    	}else {
    		System.out.println("["+str+"]" + "[MinecraftDownloader] " + message);
    	}
    	
    }
}
