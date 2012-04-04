package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class StoryDataTest {

	private Gson gson = new Gson();
	private ArrayList<StoryData> storyDatas;
	private PageReader dummyPageLoader = new DummyPageReader();
	
	@Before
	public void setUp() {
		storyDatas = new ArrayList<StoryData>();
	}
	
	@Test
	public void serilize_empty_data() {
		String json = gson.toJson(storyDatas, Collections.class);
		assertEquals("{}", json);
	}
	
	@Test
	public void should_serilize_one_data() {
		StoryData storyData = new StoryData(100, "http://jire/100", dummyPageLoader);
		storyData.addTest(new TestData("test100-1"));
		storyDatas.add(storyData);
		
		String jsonForOneData = 
				"[{\"tests\":[{\"name\":\"test100-1\"}],\"number\":100,\"link\":\"http://jire/100\",\"summary\":\"\"}]";
		
		String json = gson.toJson(storyDatas);
		assertEquals(jsonForOneData, json);
	}
	
	@Test
	public void should_serilize_multiple_datas() {
		StoryData storyData = new StoryData(100, "http://jire/100", dummyPageLoader);
		storyData.addTest(new TestData("test100-1"));
		storyData.addTest(new TestData("test100-2"));
		storyDatas.add(storyData);
		
		storyData = new StoryData(101, "http://jire/101", dummyPageLoader);
		storyData.addTest(new TestData("test101-1"));
		storyData.addTest(new TestData("test101-2"));
		storyDatas.add(storyData);
		
		String jsonForMultipleStoryDatas = 
				"[{\"tests\":[{\"name\":\"test100-1\"},{\"name\":\"test100-2\"}],\"number\":100,\"link\":\"http://jire/100\",\"summary\":\"\"}," +
		         "{\"tests\":[{\"name\":\"test101-1\"},{\"name\":\"test101-2\"}],\"number\":101,\"link\":\"http://jire/101\",\"summary\":\"\"}]";
		String json = gson.toJson(storyDatas);
		assertEquals(jsonForMultipleStoryDatas, json);
	}
	
}
