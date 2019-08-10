package fr.arinonia.launcherlib.updater.versions;

import fr.arinonia.launcherlib.launchlib.Launcher;
import fr.arinonia.launcherlib.updater.Game;
import fr.arinonia.launcherlib.updater.VersionDownloader;
import fr.nathanael2611.kyrgon.launcher.Helpers;

import java.util.*;
import java.net.*;

public class Version
{
    private String id;
    private Date time;
    private Date releaseTime;
    private URL url;
    private fr.arinonia.launcherlib.updater.Version.VersionType type;
    
    public String getId() {
        return this.id;
    }
    
    public Date getUpdatedTime() {
        return this.time;
    }
    
    public void setUpdatedTime(final Date time) {
        this.time = time;
    }
    
    public Date getReleaseTime() {
        return this.releaseTime;
    }
    
    public void setReleaseTime(final Date time) {
        this.releaseTime = time;
    }
    
    public URL getUrl() {
        return this.url;
    }
    
    public void setUrl(final URL url) {
        this.url = url;
    }
    
    public void setID(final String id) {
        this.id = id;
    }
    
    public fr.arinonia.launcherlib.updater.Version.VersionType getType() {
        return this.type;
    }
    
    public void setType(final fr.arinonia.launcherlib.updater.Version.VersionType type) {
        this.type = type;
    }


    public Game update(Launcher launcher){
        return null;
    }
}
