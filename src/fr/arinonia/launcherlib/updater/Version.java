package fr.arinonia.launcherlib.updater;

import com.google.gson.JsonSyntaxException;
import fr.arinonia.launcherlib.launchlib.Launcher;
import fr.arinonia.launcherlib.updater.utils.HttpUtils;
import fr.arinonia.launcherlib.updater.utils.JsonManager;
import fr.arinonia.launcherlib.updater.versions.CompleteVersion;
import fr.arinonia.launcherlib.updater.versions.VersionsLoader;
import fr.nathanael2611.kyrgon.launcher.Helpers;


import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

public interface Version {

    Game update(Launcher launcher);
    URL getURL();
    CompleteVersion getCompleteVersion();

    class Builder{

        private String name;

        private VersionType versionType;

        private String forgeVersion;

        public Builder(String name){
            this.name = name;

        }

        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setVersionType(VersionType versionType){
            this.versionType = versionType;
            return this;
        }

        public String getForgeVersion() {
            return forgeVersion;
        }

        public Builder setForgeVersion(String forgeVersion) {
            this.forgeVersion = forgeVersion;
            return this;
        }
        protected VersionDownloader downloader;

        public VersionDownloader getDownloader() {
            if(downloader == null){
                Helpers.sendErrorAndClose(
                        "No instance of a VersionDownloader found !\nAre you sure you have launched an update at least ?"
                );
            }
            return downloader;
        }



        @SuppressWarnings({ "unchecked", "rawtypes" })
		public Version build(){
            try {
                VersionsLoader versionsLoader = new VersionsLoader();

                versionsLoader.loadOfficialVersions();

                LightVersion version = (LightVersion)versionsLoader.getVersion(this.getName());

                if(this.versionType.equals(VersionType.FORGE)){

                    versionsLoader.loadAllForgeVersion();

                    if(this.forgeVersion != null && versionsLoader.containsForgeVersion(this.forgeVersion)){
                        version.setForgeVersion( versionsLoader.getLoadedForgeVersion(this.forgeVersion));
                    }
                }
                CompleteVersion complete = null;
		//TODO
                try {
                    complete = (CompleteVersion) JsonManager.getGson().fromJson(HttpUtils.performGet(version.getUrl(), Proxy.NO_PROXY), (Class) CompleteVersion.class);
                } catch (JsonSyntaxException | IOException ex2) {
                    ex2.printStackTrace();
                }
                version.setCompleteVersion(complete);


                CompleteVersion finalComplete = complete;
                return new Version() {
                    @Override
                    public Game update(Launcher launcher) {
                        return version.update(launcher);
                    }

                    @Override
                    public URL getURL() {
                        return version.getUrl();
                    }

                    @Override
                    public CompleteVersion getCompleteVersion() {
                        return finalComplete;
                    }
                };
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    enum VersionType{
        VANILLA,
        FORGE,
        OPTIFINE,
        MCP
    }

}
