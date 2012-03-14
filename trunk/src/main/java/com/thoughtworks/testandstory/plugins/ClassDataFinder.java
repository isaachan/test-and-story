package com.thoughtworks.testandstory.plugins;

import java.io.File;
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
				loadClass(sub, results);
			} else {
				find(rootDirectory, sub.getAbsolutePath(), results);
			}
		}
	}

	private static boolean isJavaClassFile(File file) {
		return file.getName().endsWith(".class");
	}

	private static void loadClass(File classFile, List<ClassData> results) {
		try {
			results.add(new ClassData(classFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}