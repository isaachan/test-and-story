package com.thoughtworks.testandstory.plugins;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UnitTestsReportReaderTest {

    private UnitTestsReportReader unitTestsReportReader;
    private BufferedReader unitTestsReportPage;

    @Before
    public void setUp() throws Exception {
        unitTestsReportReader = new UnitTestsReportReader();
        unitTestsReportPage = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("new-unit-test-report.html")));
    }

    @Test
    public void should_get_all_story_objects() throws Exception {
        List<StoryData> storyDatas = unitTestsReportReader.getAllStoryObjects(unitTestsReportPage);
        assertThat(storyDatas.get(0).getNumber(), is("731"));
        assertThat(storyDatas.get(0).getTests().get(0).getName(), is("should_x"));
        assertThat(storyDatas.get(1).getNumber(), is("732"));
        assertThat(storyDatas.get(1).getTests().get(1).getName(), is("should_hhh"));
    }
}
