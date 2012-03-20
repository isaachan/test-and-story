package com.thoughtworks.testandstory.plugins;

import java.util.Collection;

import org.junit.Test;

public class TestedStories {

	private final Collection<ClassData> testClasses;
	private final String storyUrlTemplate;

	public TestedStories(Collection<ClassData> testClasses, String storyUrlTemplate) {
		this.testClasses = testClasses;
		this.storyUrlTemplate = storyUrlTemplate;
	}
	
	public StoryDatas getStoryDatas(int storyNumber) {
		StoryDatas storyDatas = new StoryDatas(null);
		
		for (ClassData classData : testClasses) {
			collectFromOneClass(storyDatas, classData, storyNumber);
		}
		
		return storyDatas;
	}

	private void collectFromOneClass(StoryDatas storyDatas, ClassData classData, int storyNumber) {
		StoryData storyData = new StoryData();
		storyData.setNumber(storyNumber);
		storyData.setLink(String.format(storyUrlTemplate, storyNumber));
		for(MethodData m : classData.getMethods()) {
			if (isLabeledByStory(m) && isMeetNumber(storyNumber, m.getAnnotation(Story.class))) {;
				storyData.addTest(new TestData(m.getName()));
			}
		}
		if (storyData.findTest()) storyDatas.add(storyData);
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

	public StoryDatas getStoryDatas() {
		return getStoryDatas(-1);
	}
	
}
