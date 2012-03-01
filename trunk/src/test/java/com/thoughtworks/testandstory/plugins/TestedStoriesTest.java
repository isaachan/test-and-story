package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.testandstory.plugins.Story;
import com.thoughtworks.testandstory.plugins.StoryType;
import com.thoughtworks.testandstory.plugins.TestInformation;
import com.thoughtworks.testandstory.plugins.TestedStories;

public class TestedStoriesTest {

	@Test
	@Story(732)
	public void should_get_information_from_method_labeled_by_story() {
		List<TestInformation> infos = new TestedStories(TestedStoriesTest.class).get().all();
		TestInformation info = infos.get(0);
		
		assertEquals(732, info.number());
		assertEquals("should_get_information_from_method_labeled_by_story", info.getDescription());
		assertTrue(info.fromMethod());
	}
	
	@SuppressWarnings("serial")
	@Test
	@Story(731)
	public void should_get_information_from_methods_labeled_by_story() {
		Collection<Class<?>> testClasses = new ArrayList<Class<?>>() {{
			add(TestedStoriesTest.class);
			add(AnotherTestCase.class);
		}};
		List<TestInformation> infos = new TestedStories(testClasses).get().all();
		assertEquals(3, infos.size());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void should_get_information_by_specified_story_number() {
		Collection<Class<?>> testClasses = new ArrayList<Class<?>>() {{
			add(TestedStoriesTest.class);
			add(AnotherTestCase.class);
		}};
		List<TestInformation> infos = new TestedStories(testClasses).get(731).all();
		
		assertEquals(2, infos.size());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void should_get_test_class_information_by_specified_story_number() {
		Collection<Class<?>> testClasses = new ArrayList<Class<?>>() {{
			add(TestedStoriesTest.class);
			add(AnotherTestCase.class);
			add(FunctionalTestCase.class);
		}};
		
		List<TestInformation> infos = new TestedStories(testClasses).get(731).all();
		
		assertEquals(3, infos.size());
	}
	
	@Test
	public void should_get_type_of_test() {
		List<TestInformation> infos = new TestedStories(IntegrationTestCase.class).get().all();
		assertEquals(732, infos.get(0).number());
		assertEquals(StoryType.INTEGRATION, infos.get(0).type());
	}
	
	@Test
	public void should_get_labeled_testcase_class() {
		List<TestInformation> infos = new TestedStories(FunctionalTestCase.class).get().all();
		TestInformation info = infos.get(0);
		assertEquals(731, info.number());
		assertEquals(StoryType.FUNCTIONAL, info.type());
		assertEquals("FunctionalTestCase", info.getDescription());
		assertFalse(info.fromMethod());
	}
	
	@Test
	public void should_get_empty_list_if_no_story_labeled_tests_found() {
		List<TestInformation> infos = new TestedStories(FunctionalTestCase.class).get(12345).all();
		assertEquals(0, infos.size());
	}
	
	@Test
	public void should_find_classes_under_directory() {
		String directory = "./fixtures";
		List<TestInformation> infos = TestedStories.find(directory).get(731).all();
		assertEquals(3, infos.size());
	}
	
	@Test
	public void test_that_not_labeled_story() {
	}
	
	@Story(732)
	public void test_that_only_labeled_story() {}
	
	@Story(value=731, type=StoryType.FUNCTIONAL)
	public class FunctionalTestCase {}
	
	public class IntegrationTestCase {
		@Test
		@Story(value=732, type=StoryType.INTEGRATION)
		public void ussOfHomeIsMandatory() {}
	}
	
}