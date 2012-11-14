package com.thoughtworks.testandstory.plugins;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 4/28/12
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnitTestsReportComparator {
    private static final String NEW_ADDED_TEST = "+";
    private static final String UNIT_TEST_LINK = "http://pbnelspci4001/view/home-build-pipeline/job/home-ci/#BUILD_NUMBER/artifact/trunk/homeonline-tests/target/ut-spec-report.html";

    private boolean isExistUnmodifiedStory(StoryData storyData, List<StoryData> storyDatas) {
        return storyDatas.contains(storyData);
    }

    private boolean isExistModifiedStory(StoryData storyData, List<StoryData> storyDatas) {
        if (isExistUnmodifiedStory(storyData, storyDatas)) {
            return false;
        }
        if (findStory(storyData, storyDatas) != null) {
            return true;
        }
        return false;
    }

    private StoryData markNewAddedStory(StoryData storyData) {
        int testSize = storyData.getTests().size();
        for (int i = 0; i < testSize; i++) {
            storyData.getTests().get(i).setName(NEW_ADDED_TEST + storyData.getTests().get(i).getName());
        }
        return storyData;
    }

    private StoryData markModifiedStory(StoryData modifiedStory, List<StoryData> storyDatas) {
        StoryData storyData = findStory(modifiedStory, storyDatas);
        int testSize = modifiedStory.getTests().size();
        for (int i = 0; i < testSize; i++) {
            if (!storyData.getTests().contains(modifiedStory.getTests().get(i))) {
                modifiedStory.getTests().get(i).setName(NEW_ADDED_TEST + modifiedStory.getTests().get(i).getName());
            }
        }
        return modifiedStory;
    }

    private StoryData findStory(StoryData modifiedStory, List<StoryData> storyDatas) {
        StoryData storyData = null;
        for (StoryData story : storyDatas) {
            if (modifiedStory.getNumber().equals(story.getNumber()) && modifiedStory.getLink().equals(story.getLink()) && modifiedStory.getSummary().equals(story.getSummary())) {
                storyData = story;
            }
        }
        return storyData;
    }

    public List<StoryData> parse(List<StoryData> latestUnitTests, List<StoryData> oldUnitTests) {
        List<StoryData> parsedUnitTests = new ArrayList<StoryData>();
        for (StoryData storyData : latestUnitTests) {
            if (isExistUnmodifiedStory(storyData, oldUnitTests)) {
                parsedUnitTests.add(storyData);
            }
            if (isExistModifiedStory(storyData, oldUnitTests)) {
                parsedUnitTests.add(markModifiedStory(storyData, oldUnitTests));
            }

            if (!isExistUnmodifiedStory(storyData, oldUnitTests) && !isExistModifiedStory(storyData, oldUnitTests)) {
                parsedUnitTests.add(markNewAddedStory(storyData));
            }
        }

        return parsedUnitTests;
    }

    private String getUnitReportURL(int buildNumber) {
        return UNIT_TEST_LINK.replace("#BUILD_NUMBER", String.valueOf(buildNumber));
    }

    public List<StoryData> process(int latestUnitReportBuildNumber, int oldUnitReportBuildNumber) throws IOException {
        String latestUnitReportURL = getUnitReportURL(latestUnitReportBuildNumber);
        String oldUnitReportURL = getUnitReportURL(oldUnitReportBuildNumber);
        UnitTestsReportReader unitTestsReportReader = new UnitTestsReportReader();
        List<StoryData> latestUnitTests = unitTestsReportReader.getAllStoryObjects(latestUnitReportURL);
        List<StoryData> oldUnitTests = unitTestsReportReader.getAllStoryObjects(oldUnitReportURL);
        return parse(latestUnitTests, oldUnitTests);
    }
}
