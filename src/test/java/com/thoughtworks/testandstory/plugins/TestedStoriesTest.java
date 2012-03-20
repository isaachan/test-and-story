package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.testandstory.plugins.Story;
import com.thoughtworks.testandstory.plugins.TestedStories;

public class TestedStoriesTest {

	private static final String story_url_template = "http://jira.com/story/%s";

	@Story(100)
	@Test
	public void should_get_storydatas_from_one_class_labeled_by_story() {
		final RefelectionClassData classData = new RefelectionClassData(TestedStoriesTest.class);
		List<StoryData> storyDatas = new TestedStories(new ArrayList<ClassData>() {{add(classData);}}, story_url_template).getStoryDatas(100);
		StoryData storyData = storyDatas.get(0);
		
		assertEquals(100, storyData.getNumber());
		assertEquals("http://jira.com/story/100", storyData.getLink());
		assertEquals(1, storyData.getTests().size());
		assertEquals("should_get_storydatas_from_one_class_labeled_by_story", storyData.getTests().get(0).getName());
	}
	
	@SuppressWarnings("serial")
	@Test
	@Story(731)
	public void should_get_storydatas_from_multiple_classes_labeled_by_story() {
		Collection<ClassData> testClasses = new ArrayList<ClassData>() {{
			add(new RefelectionClassData(TestedStoriesTest.class));
			add(new RefelectionClassData(AnotherTestCase.class));
		}};
		List<StoryData> infos = new TestedStories(testClasses, story_url_template).getStoryDatas();
		assertEquals(2, infos.size());
	}

	@Test
	public void should_get_empty_list_if_no_story_labeled_tests_found() {
		List<StoryData> infos = new TestedStories(new ArrayList<ClassData>() {{add(new RefelectionClassData(FunctionalTestCase.class));}}, story_url_template).getStoryDatas(12345);
		assertEquals(0, infos.size());
	}
	
	@Test
	public void should_find_classes_under_directory() {
		String directory = "./fixtures";
		List<StoryData> infos = TestedStories.find(new String[] {directory}, story_url_template).getStoryDatas(731);
		assertEquals(2, infos.size());
		
		infos = TestedStories.find(new String[] {directory}, story_url_template).getStoryDatas(8413);
		assertEquals(1, infos.get(0).getTests().size());
	}
	
	@Test
	public void should_handler_null_distories() {
        assertEquals(0, TestedStories.find((String[]) null, story_url_template).getStoryDatas().size());
        
        assertEquals(0, TestedStories.find(new String[] {null, null}, story_url_template).getStoryDatas().size());
	}
	
	@Test
	public void test_that_not_labeled_story() {
	}
	
	@Story(732)
	public void test_that_only_labeled_story() {}
	
	public class FunctionalTestCase {}

	public class IntegrationTestCase {
		@Test
		@Story(value=732)
		public void ussOfHomeIsMandatory() {}
	}
	
}
