package com.thoughtworks.testandstory.plugins;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestedStories {

	private final Collection<ClassData> testClasses;
	private final String storyUrlTemplate;
    private final PageReader pageReader;

	public TestedStories(Collection<ClassData> testClasses, String storyUrlTemplate, PageReader pageReader) {
		this.testClasses = testClasses;
		this.storyUrlTemplate = storyUrlTemplate;
        this.pageReader = pageReader;
	}
	
	public Collection<StoryData> getStoryDatas(int storyNumber) {
		Map<Integer, StoryData> storyDatas = new HashMap<Integer, StoryData>();
		for (ClassData classData : testClasses) {
			collectTestFromClass(storyDatas, classData, storyNumber);
		}
		return storyDatas.values();
	}

	private void collectTestFromClass(Map<Integer, StoryData> storyDatas, ClassData classData, int storyNumber) {
		for(MethodData m : classData.getMethods()) {
			if (isLabeledByStory(m) && isMeetNumber(storyNumber, m.getAnnotation(Story.class))) {
				int number = m.getAnnotation(Story.class).value();
				if (!storyDatas.containsKey(number)) {
					StoryData storyData = new StoryData(number, String.format(storyUrlTemplate, number), pageReader);
					storyDatas.put(number, storyData);
				}
				storyDatas.get(number).addTest(new TestData(m.getName()));
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

	public static TestedStories find(String[] directories, String storyUrlTemplate, PageReader pageReader) {
		List<ClassData> classDatas = ClassDataFinder.findClassDatas(directories);
		return new TestedStories(classDatas, storyUrlTemplate, pageReader);
	}

	public Collection<StoryData> getStoryDatas() {
		return getStoryDatas(-1);
	}
	
}
