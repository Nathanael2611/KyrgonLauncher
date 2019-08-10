package fr.nathanael2611.kyrgon.launcher.ui;

import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceManager {


    public static String resourcePath;

    public static void setResourcePath(String resourcePath)
    {
        ResourceManager.resourcePath = resourcePath.endsWith("/") ? resourcePath.substring(0, resourcePath.length() - 1) : resourcePath;
    }

    public static String getResourcePath(){
        return resourcePath;
    }

    public static BufferedImage getImage(String resource)
    {
        try {
            return ImageIO.read(KyrgonLauncher.class.getResourceAsStream(getResourcePath() + "/" + resource));
        }
        catch (IOException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't load the given image (" + getResourcePath() + "/" + resource + ") : " + e);
        }

    }
    public static File getResource(String resource){
        try
        {
            return new File(getResourcePath()+"/"+ resource);
        } catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Can't load the given image (" + getResourcePath() + "/" + resource + ") : " + e);
        }
    }
}
