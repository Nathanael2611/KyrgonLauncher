package fr.arinonia.launcherlib.updater.download;


import fr.arinonia.launcherlib.updater.utils.FileUtils;
import fr.arinonia.launcherlib.utils.MinecraftFolder;
import fr.arinonia.launcherlib.utils.Utils;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;

import java.io.File;
import java.security.cert.CRLReason;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DownloadManager
{

    private MinecraftFolder folder;
    public ArrayList<DownloadEntry> filesToDownload;
    public ArrayList<DownloadEntry> downloadedFile;
    private ArrayList<String> dontDelete;
    private int value;
    public DownloadManager(final MinecraftFolder folder) {
        this.filesToDownload = new ArrayList<DownloadEntry>();
        this.downloadedFile = new ArrayList<DownloadEntry>();
        this.dontDelete = new ArrayList<String>();
        this.folder = folder;
    }
    
    public DownloadEntry addDownloadableFile(final DownloadEntry file) {
        if (!this.filesToDownload.contains(file)) {
            this.dontDelete.add(file.getDestination().getAbsolutePath());
            if (file.needDownload()) {
                this.filesToDownload.add(file);
            }
        }
        return file;
    }
    
    public void startDownload() {
        if (this.filesToDownload.size() == 0) {
        	Utils.sendMessageInConsole("Nothing to download!",false);
            KyrgonLauncher.getInstance().getUpdateManager().setUpdateMessage("Nothing to download !");
            return;
        }
         value = this.filesToDownload.size();
        Utils.sendMessageInConsole(String.valueOf(this.filesToDownload.size()) + " files to download.", false);
        for (final DownloadEntry download : this.filesToDownload) {
        	Utils.sendMessageInConsole("Download: " + download.getURL() + " | " + getDownloadPercentage() +"% | " + downloadedFile.size() + "/" + filesToDownload.size(),false);
            download.download();
            this.downloadedFile.add(download);
        }
        Utils.sendMessageInConsole("Finished download !", false);
    }
    
    private int getPercentToDownload() {
        return (value / this.filesToDownload.size())*100;

	}
    public static int crossMult(int value, int maximum, int coefficient){
        return (int) ((double) value / (double) maximum * (double) coefficient);
    }
    
    public int removeSurplus(final File folder) {
        final ArrayList<File> files = FileUtils.listFilesForFolder(folder);
        int count = 0;
        for (final File f : files) {
            if (f.isDirectory()) {
                boolean good = true;
                final ArrayList<File> files2 = FileUtils.listFilesForFolder(f);
                if (files2.isEmpty()) {
                    f.delete();
                }
                else {
                    for (final File f2 : files2) {
                        if (!f2.isDirectory()) {
                            good = true;
                            break;
                        }
                        good = false;
                    }
                    if (!good) {
                        f.delete();
                    }
                }
            }
            if (!this.dontDelete.contains(f.getAbsolutePath()) && !f.isDirectory()) {
                f.delete();
                if (folder == this.folder.getNativesFolder()) {
                    continue;
                }
                ++count;
            }
        }
        return count;
    }
    
    public void clearDownloads() {
        this.filesToDownload.clear();
        this.downloadedFile.clear();
        this.dontDelete.clear();
    }
    
    public float getDownloadPercentage() {
        return ((downloadedFile.size() / this.filesToDownload.size())*100);
    }
    
    public boolean isDownloadFinished() {
        return this.filesToDownload.size() == 0;
    }
    
    public void check() {
        int count = 0;
        if (this.folder.getLibrariesFolder() != null && !this.folder.getLibrariesFolder().exists()) {
            this.folder.getLibrariesFolder().mkdirs();
        }
        if (this.folder.getNativesFolder() != null && !this.folder.getNativesFolder().exists()) {
            this.folder.getNativesFolder().mkdirs();
        }
        if (this.folder.getModsFolder() != null && this.folder.getModsFolder().exists()) {
            count += this.removeSurplus(this.folder.getModsFolder());
        }
        if (this.folder.getLibrariesFolder() != null) {
            count += this.removeSurplus(this.folder.getLibrariesFolder());
        }
        if (count != 0) {
        	Utils.sendMessageInConsole("Deleted " + count + " files.",false);
        }
    }
    
    public ArrayList<String> getDontDownload() {
        return this.dontDelete;
    }

}
