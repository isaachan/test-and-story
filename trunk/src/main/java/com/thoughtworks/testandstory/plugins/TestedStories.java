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
	
	public Collection<StoryData> getStoryDatas(String storyNumber) {
		Map<String, StoryData> storyDatas = new HashMap<String, StoryData>();
		for (ClassData classData : testClasses) {
			collectTestFromClass(storyDatas, classData, storyNumber);
		}
		return storyDatas.values();
	}

	private void collectTestFromClass(Map<String, StoryData> storyDatas, ClassData classData, String storyNumber) {
		for(MethodData m : classData.getMethods()) {
			if (isLabeledByStory(m) && isMeetNumber(storyNumber, m.getAnnotation(Story.class))) {
				String number = m.getAnnotation(Story.class).value();
				if (!storyDatas.containsKey(number)) {
					StoryData storyData = new StoryData(number, String.format(storyUrlTemplate, number), pageReader);
					storyDatas.put(number, storyData);
				}
				storyDatas.get(number).addTest(new TestData(m.getName()));
			}
		}
	}

	private boolean isMeetNumber(String number, Story annotation) {
		if (number.endsWith("-1")) return true; 
		return annotation.value().equals(number);
	}

	private boolean isLabeledByStory(MethodData m) {
		return m.isAnnotationPresent(Story.class) && m.isAnnotationPresent(Test.class);
	}

	public static TestedStories find(String[] directories, String storyUrlTemplate, PageReader pageReader) {
		List<ClassData> classDatas = ClassDataFinder.findClassDatas(directories);
		return new TestedStories(classDatas, storyUrlTemplate, pageReader);
	}

	public Collection<StoryData> getStoryDatas() {
		return getStoryDatas("-1");
	}
	
}
