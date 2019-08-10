package fr.arinonia.launcherlib.updater;

import fr.arinonia.launcherlib.launchlib.Launcher;
import fr.arinonia.launcherlib.updater.versions.CompleteVersion;
import fr.arinonia.launcherlib.updater.versions.ForgeVersion;
import fr.nathanael2611.kyrgon.launcher.Helpers;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;


import java.net.URL;

public class LightVersion extends fr.arinonia.launcherlib.updater.versions.Version {
    private CompleteVersion completeVersion;
    private ForgeVersion forgeVersion;



    public void setId(String id) {
        super.setID(id);;
    }

    public void setUrl(URL url) {
        super.setUrl(url);
    }

    public void setType(fr.arinonia.launcherlib.updater.Version.VersionType type) {
        super.setType(type);
    }

    public void setForgeVersion(ForgeVersion forgeVersion) {
        this.forgeVersion = forgeVersion;
    }

    public ForgeVersion getForgeVersion() {
        return forgeVersion;
    }

    @Override
    public URL getUrl() {
        return super.getUrl();
    }

    public String getId() {
        return super.getId();
    }

    public fr.arinonia.launcherlib.updater.Version.VersionType getVersionType() {
        return super.getType();
    }

    public CompleteVersion getCompleteVersion() {
        return completeVersion;
    }

    public void setCompleteVersion(CompleteVersion completeVersion) {
        this.completeVersion = completeVersion;
    }


    @Override
    public Game update(Launcher launcher) {
        KyrgonLauncher.getInstance().getUpdateManager().setActualVersionDownloader(new VersionDownloader(launcher));
        Game game  = KyrgonLauncher.getInstance().getUpdateManager().getActualVersionDownloader().downloadVersion(this, this.getCompleteVersion());
        KyrgonLauncher.getInstance().getUpdateManager().getActualVersionDownloader().getDownloadManager().check();
        KyrgonLauncher.getInstance().getUpdateManager().getActualVersionDownloader().getDownloadManager().startDownload();
        KyrgonLauncher.getInstance().getUpdateManager().getActualVersionDownloader().unzipNatives();
        KyrgonLauncher.getInstance().getUpdateManager().getActualVersionDownloader().getDownloadManager().check();

        return game;
    }


}
