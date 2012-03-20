package com.thoughtworks.testandstory.plugins;

import java.util.ArrayList;
import java.util.List;

public class StoryData {

	private ArrayList<TestData> tests = new ArrayList<TestData>();
	private int number;
	private String link;

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

	public void setNumber(int number) {
		this.number = number;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
