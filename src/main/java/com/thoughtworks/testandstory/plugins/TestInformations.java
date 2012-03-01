package com.thoughtworks.testandstory.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestInformations {

	private final List<TestInformation> testInformations;

	public TestInformations(List<TestInformation> results) {
		this.testInformations = results;
	}
	
	public List<TestInformation> all() {
		return testInformations;
	}

	public List<Integer> storyNumbers() {
		Set<Integer> storyNumbers = new HashSet<Integer>();
		for(TestInformation info : testInformations) {
			storyNumbers.add(info.number());
		}

		List<Integer> toSorted = new ArrayList<Integer>(storyNumbers);
		Collections.sort(toSorted);
		return toSorted;
	}

	public List<TestInformation> getTestInformationsBy(int storyNumber) {
		List<TestInformation> results = new ArrayList<TestInformation>();
		for (TestInformation info : testInformations) {
			if (info.number() == storyNumber) results.add(info);
		}

		Collections.sort(results);
		return results;
	}
	
}
