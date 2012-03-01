package com.thoughtworks.testandstory.plugins;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TestedStories {

	private final Collection<Class<?>> testClasses;

	public TestedStories(Collection<Class<?>> testClasses) {
		this.testClasses = testClasses;
	}
	
	@SuppressWarnings("serial")
	public TestedStories(final Class<?> testClass) {
		this.testClasses = new ArrayList<Class<?>>() {{
			add(testClass);
		}};
	}
	
	public TestInformations get() {
		return get(-1);
	}

	public TestInformations get(int number) {
		
		List<TestInformation> results = new ArrayList<TestInformation>();
		
		for (Class<?> testClass : testClasses) {
			if (testClass.isAnnotationPresent(Story.class) && isMeetNumber(number, testClass.getAnnotation(Story.class))) {
				results.add(getStoryInformation(testClass.getAnnotation(Story.class), testClass.getSimpleName(), false));
			} else {
				collectFromOneClass(results, testClass, number);
			}
		}
		
		Collections.sort(results);
		return new TestInformations(results);
	}

	private TestInformation getStoryInformation(Story annotation, String description, boolean fromMethod) {
		return new TestInformation(annotation.value(), annotation.type(), description, fromMethod);
	}

	private void collectFromOneClass(List<TestInformation> results, Class<?> testClass, int number) {
		for(Method m : testClass.getMethods()) {
			if (isLabeledByStory(m) && isMeetNumber(number, m.getAnnotation(Story.class))) {;
				results.add(getStoryInformation(m.getAnnotation(Story.class), m.getName(), true));
			}
		}
	}

	private boolean isMeetNumber(int number, Story annotation) {
		if (number == -1) return true; 
		return annotation.value() == number;
	}

	private boolean isLabeledByStory(Method m) {
		return m.isAnnotationPresent(Story.class) && m.isAnnotationPresent(Test.class);
	}

	public static TestedStories find(String...directories) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for(String dir : directories) {
			classes.addAll(findClasses(dir));
		}
		return new TestedStories(classes);
	}
	
	public static TestedStories find(String directory) {
		return new TestedStories(findClasses(directory));
	}

	private static ArrayList<Class<?>> findClasses(String directory) {
		ArrayList<Class<?>> results = new ArrayList<Class<?>>();
		find(directory, directory, results);
		return results;
	}
	
	private static void find(String rootDirectory, String currentDirectory, List<Class<?>> results) {
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

	private static void loadClass(File classesDirectory, String classPackageName, List<Class<?>> results) {
		try {
			URL[] urls = new URL[] {classesDirectory.toURI().toURL()};
			URLClassLoader classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
			results.add(classLoader.loadClass(classPackageName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
