package com.thoughtworks.testandstory.plugins;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ClassDataFinder {
	
	public static ArrayList<ClassData> findClassDatas(String... directories) {
		ArrayList<ClassData> classes = new ArrayList<ClassData>();
		for(String dir : directories) {
			classes.addAll(findClasses(dir));
		}
		return classes;
	}
	
	public static TestedStories find(String...directories) {
		ArrayList<ClassData> classes = new ArrayList<ClassData>();
		for(String dir : directories) {
			classes.addAll(findClasses(dir));
		}
		return new TestedStories(classes);
	}

	public static ArrayList<ClassData> findClasses(String directory) {
		ArrayList<ClassData> results = new ArrayList<ClassData>();
		find(directory, directory, results);
		return results;
	}
	
	private static void find(String rootDirectory, String currentDirectory, List<ClassData> results) {
		File classesDirectory = new File(currentDirectory);
		for (File sub : classesDirectory.listFiles()) {
			if (sub.isFile()) {
				if (!isJavaClassFile(sub)) continue;
				loadClass(new File(rootDirectory), classCanonicalName(rootDirectory, sub), results);
			} else {
				find(rootDirectory, sub.getAbsolutePath(), results);
			}
		}
	}

	private static String classCanonicalName(String rootDirectory, File classFile) {
		return classFile.getAbsolutePath()
		          .substring(new File(rootDirectory).getAbsolutePath().length() + 1)
		          .replace("\\", ".")
		          .replace("/", ".")
		          .replace(".class", "");
	}

	private static boolean isJavaClassFile(File file) {
		return file.getName().endsWith(".class");
	}

	private static void loadClass(File classesDirectory, String classPackageName, List<ClassData> results) {
		try {
			URL[] urls = new URL[] {classesDirectory.toURI().toURL()};
			URLClassLoader classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
			results.add(new ClassData(classLoader.loadClass(classPackageName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}