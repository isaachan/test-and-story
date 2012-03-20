package com.thoughtworks.testandstory.plugins;

import java.util.ArrayList;
import java.util.List;

public class StoryDatas {

	public final TestInformations testInformations;
	private List<StoryData> storyDatas = new ArrayList<StoryData>();

	// This constructor will be removed once refactoring done.
	public StoryDatas(TestInformations testInformations) {
		this.testInformations = testInformations;
	}

	public StoryData get(int index) {
		return storyDatas.get(0);
	}

	public void add(StoryData storyData) {
		storyDatas.add(storyData);
	}

	public List<StoryData> all() {
		return storyDatas;
	}
}
