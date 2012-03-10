package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.testandstory.plugins.StoryType;
import com.thoughtworks.testandstory.plugins.TestInformation;
import com.thoughtworks.testandstory.plugins.TestInformations;

public class TestInformationsTest {

	private TestInformations infos;
	
	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		infos = new TestInformations(new ArrayList<TestInformation>() {{
						add(new TestInformation(732, StoryType.UNIT, "test 1", true));
						add(new TestInformation(731, StoryType.UNIT, "test 2", true));
						add(new TestInformation(731, StoryType.FUNCTIONAL, "test 3", true));
						add(new TestInformation(731, StoryType.UNIT, "test 4", true));
					}});
	}
	
	@Test
	public void should_get_all_sorted_story_numbers() {
		List<Integer> storyNumbers = infos.storyNumbers();
		assertEquals(2, storyNumbers.size());
		assertEquals(731, storyNumbers.get(0).intValue());
		assertEquals(732, storyNumbers.get(1).intValue());
	}
	
	@Test
	public void should_get_all_sorted_test_informations_by_story_number() {
		List<TestInformation> infosFor732 = infos.getTestInformationsBy(732);
		assertEquals(1, infosFor732.size());
		
		List<TestInformation> infosFor731 = infos.getTestInformationsBy(731);
		assertEquals(3, infosFor731.size());
		assertEquals("test 3", infosFor731.get(0).getMethodOrClassName());
		assertEquals("test 2", infosFor731.get(1).getMethodOrClassName());
		assertEquals("test 4", infosFor731.get(2).getMethodOrClassName());
	}
}
