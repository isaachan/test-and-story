package com.thoughtworks.testandstory.plugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class TestedStories {

	private final Collection<ClassData> testClasses;
	private final String storyUrlTemplate;

	public TestedStories(Collection<ClassData> testClasses, String storyUrlTemplate) {
		this.testClasses = testClasses;
		this.storyUrlTemplate = storyUrlTemplate;
	}
	
	public List<StoryData> getStoryDatas(int storyNumber) {
		List<StoryData> storyDatas = new ArrayList<StoryData>();
		for (ClassData classData : testClasses) {
			StoryData storyData = collectFromOneClass(classData, storyNumber);
			if (storyData.findTest()) storyDatas.add(storyData);
		}
		return storyDatas;
	}

	private StoryData collectFromOneClass(ClassData classData, int storyNumber) {
		StoryData storyData = new StoryData();
		storyData.setNumber(storyNumber);
		storyData.setLink(String.format(storyUrlTemplate, storyNumber));
		for(MethodData m : classData.getMethods()) {
			if (isLabeledByStory(m) && isMeetNumber(storyNumber, m.getAnnotation(Story.class))) {;
				storyData.addTest(new TestData(m.getName()));
			}
		}
		return storyData;
	}

	private boolean isMeetNumber(int number, Story annotation) {
		if (number == -1) return true; 
		return annotation.value() == number;
	}

	private boolean isLabeledByStory(MethodData m) {
		return m.isAnnotationPresent(Story.class) && m.isAnnotationPresent(Test.class);
	}

	public static TestedStories find(String[] directories, String storyUrlTemplate) {
		return new TestedStories(ClassDataFinder.findClassDatas(directories), storyUrlTemplate);
	}

	public List<StoryData> getStoryDatas() {
		return getStoryDatas(-1);
	}
	
}
