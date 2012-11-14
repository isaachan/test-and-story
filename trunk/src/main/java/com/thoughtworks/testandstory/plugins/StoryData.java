package com.thoughtworks.testandstory.plugins;

import java.util.ArrayList;
import java.util.List;

public class StoryData {

    private final ArrayList<TestData> tests = new ArrayList<TestData>();
    private final String number;
    private final String link;
    private final String summary;

    public StoryData(String number, String link, PageReader pageReader) {
        this.number = number;
        this.link = link;
        this.summary = pageReader.getStorySummary(link);
    }

    public StoryData(String number, String link, String summary) {
        this.number = number;
        this.link = link;
        this.summary = summary;
    }

    public String getNumber() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoryData storyData = (StoryData) o;

        if (number != storyData.number) return false;
        if (!link.equals(storyData.link)) return false;
        if (!summary.equals(storyData.summary)) return false;
        if (!tests.containsAll(storyData.getTests())) return false;
        if (tests.containsAll(storyData.getTests()) && tests.size() != storyData.getTests().size()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tests.hashCode();
        result += number.hashCode();
        result += link.hashCode();
        result += summary.hashCode();
        return result;
    }
}
