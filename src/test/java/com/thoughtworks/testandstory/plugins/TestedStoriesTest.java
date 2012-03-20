package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;

import com.thoughtworks.testandstory.plugins.Story;
import com.thoughtworks.testandstory.plugins.TestedStories;

public class TestedStoriesTest {

	private static final String story_url_template = "http://jira.com/story/%s";
    private PageReader dummayPageReader = new PageReader() {
        @Override
        public String getStorySummary(String storyUrl)
        {
            return "";
        }};

	@Story(100)
	@Test
	@SuppressWarnings("serial")
	public void should_get_storydatas_from_one_class_labeled_by_story() {
		final RefelectionClassData classData = new RefelectionClassData(TestedStoriesTest.class);
		Collection<StoryData> storyDatas = new TestedStories(new ArrayList<ClassData>() {{add(classData);}}, story_url_template, dummayPageReader).getStoryDatas(100);
		StoryData storyData = (StoryData) storyDatas.toArray()[0];
		
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
		Collection<StoryData> infos = new TestedStories(testClasses, story_url_template, dummayPageReader).getStoryDatas();
		assertEquals(2, infos.size());
	}

	@Test
	@SuppressWarnings("serial")
	public void should_get_empty_list_if_no_story_labeled_tests_found() {
		Collection<StoryData> infos = new TestedStories(new ArrayList<ClassData>() {{add(new RefelectionClassData(FunctionalTestCase.class));}}, story_url_template, dummayPageReader).getStoryDatas(12345);
		assertEquals(0, infos.size());
	}
	
	@Test
	public void should_find_classes_under_directory() {
		String directory = "./fixtures";
		Collection<StoryData> infos = TestedStories.find(new String[] {directory}, story_url_template, dummayPageReader).getStoryDatas(731);
		assertEquals(1, infos.size());
		
		infos = TestedStories.find(new String[] {directory}, story_url_template, dummayPageReader).getStoryDatas(8413);
		assertEquals(1, ((StoryData) infos.toArray()[0]).getTests().size());
	}
	
	@Test
	public void should_handler_null_distories() {
        assertEquals(0, TestedStories.find((String[]) null, story_url_template, dummayPageReader).getStoryDatas().size());
        assertEquals(0, TestedStories.find(new String[] {null, null}, story_url_template, dummayPageReader).getStoryDatas().size());
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
