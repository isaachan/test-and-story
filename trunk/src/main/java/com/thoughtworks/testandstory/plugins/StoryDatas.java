package com.thoughtworks.testandstory.plugins;

import java.util.ArrayList;
import java.util.List;

public class StoryDatas {

	public final List<StoryData> storyDatas = new ArrayList<StoryData>();

	public void add(StoryData storyData) {
		storyDatas.add(storyData);
	}

	public List<StoryData> all() {
		return storyDatas;
	}
}
