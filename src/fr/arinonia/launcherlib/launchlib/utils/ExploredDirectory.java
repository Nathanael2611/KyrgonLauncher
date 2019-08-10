package fr.arinonia.launcherlib.launchlib.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import fr.arinonia.launcherlib.updater.utils.FileUtils;

public class ExploredDirectory
{
    protected File directory;

    public ExploredDirectory(File directory)
    {
        this.directory = directory;
    }

    public FileList allRecursive()
    {
        return new FileList(FileUtils.listRecursive(directory));
    }

    public FileList list()
    {
        return new FileList(Arrays.asList(FileUtils.list(this.directory)));
    }

    public ExploredDirectory sub(String directory)
    {
        return new ExploredDirectory(FileUtils.dir(this.directory, directory));
    }

    public File get(String file)
    {
        return FileUtils.get(this.directory, file);
    }

    public FileList subs()
    {
        File[] files = FileUtils.list(this.directory);
        ArrayList<File> dirs = new ArrayList<File>();

        for (File f : files)
            if (f.isDirectory())
                dirs.add(f);

        return new FileList(dirs);
    }

    public FileList files()
    {
        File[] files = FileUtils.list(this.directory);
        ArrayList<File> fs = new ArrayList<File>();

        for (File f : files)
            if (!f.isDirectory())
                fs.add(f);

        return new FileList(fs);
    }

    public File get()
    {
        return directory;
    }

	
}