package com.thoughtworks.testandstory.plugins;

public class DummyPageReader implements PageReader {

	@Override
	public String getStorySummary(String storyUrl) {
		return "";
	}

}
