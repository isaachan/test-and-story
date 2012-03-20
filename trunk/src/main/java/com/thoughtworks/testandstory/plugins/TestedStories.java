package com.thoughtworks.testandstory.plugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TestedStories {

	private final Collection<ClassData> testClasses;

	public TestedStories(Collection<ClassData> testClasses) {
		this.testClasses = testClasses;
	}
	
	@SuppressWarnings("serial")
	public TestedStories(final ClassData testClass) {
		this.testClasses = new ArrayList<ClassData>() {{
			add(testClass);
		}};
	}
	
	public StoryData get() {
		return new StoryData(get(-1).testInformations);
	}

	public StoryData get(int number) {
		List<TestInformation> results = new ArrayList<TestInformation>();
		
		for (ClassData classData : testClasses) {
			collectFromOneClass(results, classData, number);
		}
		
		Collections.sort(results);
		return new StoryData(new TestInformations(results));
	}

	private TestInformation getStoryInformation(Story annotation, String description, boolean fromMethod) {
		return new TestInformation(annotation.value(), description, fromMethod);
	}

	private void collectFromOneClass(List<TestInformation> results, ClassData classData, int number) {
		for(MethodData m : classData.getMethods()) {
			if (isLabeledByStory(m) && isMeetNumber(number, m.getAnnotation(Story.class))) {;
				results.add(getStoryInformation(m.getAnnotation(Story.class), m.getName(), true));
			}
		}
	}

	private boolean isMeetNumber(int number, Story annotation) {
		if (number == -1) return true; 
		return annotation.value() == number;
	}

	private boolean isLabeledByStory(MethodData m) {
		return m.isAnnotationPresent(Story.class) && m.isAnnotationPresent(Test.class);
	}

	public static TestedStories find(String...directories) {
		return new TestedStories(ClassDataFinder.findClassDatas(directories));
	}
	
}
