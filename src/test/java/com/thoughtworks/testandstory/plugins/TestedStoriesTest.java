package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.testandstory.plugins.Story;
import com.thoughtworks.testandstory.plugins.TestInformation;
import com.thoughtworks.testandstory.plugins.TestedStories;

public class TestedStoriesTest {

	@Test
	@Story(732)
	public void should_get_information_from_method_labeled_by_story() {
		List<TestInformation> infos = new TestedStories(new RefelectionClassData(TestedStoriesTest.class)).get().testInformations.all();
		TestInformation info = infos.get(0);
		
		assertEquals(732, info.number());
		assertEquals("should_get_information_from_method_labeled_by_story", info.getMethodOrClassName());
		assertTrue(info.fromMethod());
	}
	
	@SuppressWarnings("serial")
	@Test
	@Story(731)
	public void should_get_information_from_methods_labeled_by_story() {
		Collection<ClassData> testClasses = new ArrayList<ClassData>() {{
			add(new RefelectionClassData(TestedStoriesTest.class));
			add(new RefelectionClassData(AnotherTestCase.class));
		}};
		List<TestInformation> infos = new TestedStories(testClasses).get().testInformations.all();
		assertEquals(3, infos.size());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void should_get_information_by_specified_story_number() {
		Collection<ClassData> testClasses = new ArrayList<ClassData>() {{
			add(new RefelectionClassData(TestedStoriesTest.class));
			add(new RefelectionClassData(AnotherTestCase.class));
		}};
		List<TestInformation> infos = new TestedStories(testClasses).get(731).testInformations.all();
		
		assertEquals(2, infos.size());
	}
	
	@Test
	public void should_get_type_of_test() {
		List<TestInformation> infos = new TestedStories(new RefelectionClassData(IntegrationTestCase.class)).get().testInformations.all();
		assertEquals(732, infos.get(0).number());
	}
	
	@Test
	public void should_get_empty_list_if_no_story_labeled_tests_found() {
		List<TestInformation> infos = new TestedStories(new RefelectionClassData(FunctionalTestCase.class)).get(12345).testInformations.all();
		assertEquals(0, infos.size());
	}
	
	@Test
	public void should_find_classes_under_directory() {
		String directory = "./fixtures";
		List<TestInformation> infos = TestedStories.find(directory).get(731).testInformations.all();
		assertEquals(2, infos.size());
		
		infos = TestedStories.find(directory).get(8413).testInformations.all();
		assertEquals(1, infos.size());
	}
	
	@Test
	public void should_handler_null_distories() {
        assertEquals(0, TestedStories.find((String[]) null).get().testInformations.all().size());
        
        assertEquals(0, TestedStories.find(new String[] {null, null}).get().testInformations.all().size());
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
