package com.thoughtworks.testandstory.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassDataFinder {

	public static List<ClassData> findClassDatas(String[] directories) {
	    if (directories == null) return new ArrayList<ClassData>();
	    
		List<ClassData> classes = new ArrayList<ClassData>();
		for(String dir : directories) {
		    if (dir == null) continue;
			classes.addAll(findClasses(dir));
		}
		return classes;
	}

	private static List<ClassData> findClasses(String directory) {
		List<ClassData> results = new ArrayList<ClassData>();
		find(directory, directory, results);
		return results;
	}
	
	private static void find(String rootDirectory, String currentDirectory, List<ClassData> results) {
		File classesDirectory = new File(currentDirectory);
		if (!classesDirectory.exists()) return;
		
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