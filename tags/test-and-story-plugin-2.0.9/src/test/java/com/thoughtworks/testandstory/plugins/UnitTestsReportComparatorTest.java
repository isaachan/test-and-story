package com.thoughtworks.testandstory.plugins;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 4/28/12
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnitTestsReportComparatorTest {
    @Test
    public void should_mark_all_stories() throws Exception {
        BufferedReader latestUnitTestsReportPage = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("new-unit-test-report.html")));
        BufferedReader oldUnitTestsReportPage = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("old-unit-test-report.html")));

        UnitTestsReportReader unitTestsReportReader = new UnitTestsReportReader();

        List<StoryData> latestUnitTests = unitTestsReportReader.getAllStoryObjects(latestUnitTestsReportPage);

        List<StoryData> oldUnitTests = unitTestsReportReader.getAllStoryObjects((oldUnitTestsReportPage));

        List<StoryData> resultUnitTests = new UnitTestsReportComparator().parse(latestUnitTests, oldUnitTests);

        assertThat(resultUnitTests.get(0).getTests().get(0).getName(), is("+should_x"));
        assertThat(resultUnitTests.get(0).getTests().get(1).getName(), is("should_abc"));
        assertThat(resultUnitTests.get(0).getTests().get(2).getName(), is("should_123"));

        assertThat(resultUnitTests.get(1).getTests().get(0).getName(), is("should_xyz"));
        assertThat(resultUnitTests.get(1).getTests().get(1).getName(), is("should_hhh"));

        assertThat(resultUnitTests.get(2).getTests().get(0).getName(), is("+should_xyz"));
        assertThat(resultUnitTests.get(2).getTests().get(1).getName(), is("+should_hhh"));
    }
}
