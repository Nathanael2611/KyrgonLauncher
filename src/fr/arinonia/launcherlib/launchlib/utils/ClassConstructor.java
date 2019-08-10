package fr.arinonia.launcherlib.launchlib.utils;

import java.io.File;
import java.util.List;

public class ClassConstructor extends FileList {
	/**
	 * Empty Classpath Constructor
	 */
	public ClassConstructor() {
		super();
	}

	/**
	 * Classpath Constructor with pre-defined files
	 *
	 * @param classPath The files to add
	 */
	public ClassConstructor(List<File> classPath) {
		super(classPath);
	}

	/**
	 * Make the classpath
	 *
	 * @return Something like libs.jar;test.jar;libs/myjar.jar (with : instead of ;
	 *         on Mac and Linux)
	 */
	public String make() {
		String classPath = "";

		for (int i = 0; i < files.size(); i++)
			classPath += files.get(i).getAbsolutePath() + (i + 1 == files.size() ? "" : File.pathSeparator);

		return classPath;
	}
}
