package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.testandstory.plugins.Reporter;

public class ReporterTest {

	private Reporter reporter = new Reporter();
	private PageReader dummayPageReader = new PageReader() {
	        @Override
	        public String getStorySummary(String storyUrl)
	        {
	            return "";
	        }};
	        
	@Test
	public void empty_report_for_empty_input() {
		assertEquals("", reporter.report(new ArrayList<StoryData>()));
	}
	
	@Ignore
	public void report_for_some_inputs() {
		Reporter reporter = new Reporter();
		
		ArrayList<StoryData> storyDatas = new ArrayList<StoryData>();
		storyDatas.add(new StoryData(100, "story_url", dummayPageReader));
		
		String report = reporter.report(storyDatas);
		assertTrue(report.contains("<html>"));
	}
	
}
