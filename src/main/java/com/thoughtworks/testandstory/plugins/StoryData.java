package com.thoughtworks.testandstory.plugins;

import java.util.ArrayList;
import java.util.List;

public class StoryData {

	private final ArrayList<TestData> tests = new ArrayList<TestData>();
	private final int number;
	private final String link;
    private final String summary;

	public StoryData(int number, String link, PageReader pageReader) {
		this.number = number;
		this.link = link;
        this.summary = pageReader.getStorySummary(link);
	}

	public int getNumber() {
		return number;
	}

	public String getLink() {
		return link;
	}

	public List<TestData> getTests() {
		return tests;
	}

	public void addTest(TestData testData) {
		tests.add(testData);
	}

	public boolean findTest() {
		return !tests.isEmpty();
	}

	public String getSummary() {
	    return summary;
	}
}
