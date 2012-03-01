package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thoughtworks.testandstory.plugins.StoryType;
import com.thoughtworks.testandstory.plugins.TestInformation;

public class TestInformationTest {

	@Test
	public void order_by_number() {
		TestInformation info1 = new TestInformation(1, StoryType.UNIT, "", true);
		TestInformation info2 = new TestInformation(2, StoryType.UNIT, "", true);
		
		assertTrue(info1.compareTo(info2) > 0);
	}
	
	@Test
	public void order_by_test_type_when_number_is_same() {
		TestInformation info1 = new TestInformation(1, StoryType.UNIT, "", true);
		TestInformation info2 = new TestInformation(1, StoryType.INTEGRATION, "", true);
		TestInformation info3 = new TestInformation(1, StoryType.FUNCTIONAL, "", true);

		assertTrue(info1.compareTo(info2) > 0);
		assertTrue(info2.compareTo(info3) > 0);
	}
	
	@Test
	public void order_by_class_or_test_when_test_type_are_same() {
		TestInformation info1 = new TestInformation(1, StoryType.UNIT, "", true);
		TestInformation info2 = new TestInformation(1, StoryType.UNIT, "", false);
		
		assertTrue(info1.compareTo(info2) < 0);
	}
	
	@Test
	public void order_by_description_when_others_are_same() {
		TestInformation info1 = new TestInformation(1, StoryType.UNIT, "test1", true);
		TestInformation info2 = new TestInformation(1, StoryType.UNIT, "test1", true);
		TestInformation info3 = new TestInformation(1, StoryType.UNIT, "test2", true);

		assertTrue(info1.compareTo(info2) == 0);
		assertTrue(info1.compareTo(info3) < 0);
		assertTrue(info2.compareTo(info3) < 0);		
	}
}
