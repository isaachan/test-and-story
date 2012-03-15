package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.thoughtworks.testandstory.plugins.Reporter;
import com.thoughtworks.testandstory.plugins.TestInformation;
import com.thoughtworks.testandstory.plugins.TestInformations;

public class ReporterTest {

	private Reporter reporter = new Reporter();

	@Test
	public void empty_report_for_empty_input() {
		assertEquals("", reporter.report(new TestInformations(new ArrayList<TestInformation>())));
	}
	
	@SuppressWarnings("serial")
	@Test
	public void report_for_some_inputs() {
		Reporter reporter = new Reporter();
		ArrayList<TestInformation> results = new ArrayList<TestInformation>() {{
			add(new TestInformation(731, "the test", true));
		}};
		
		String report = reporter.report(new TestInformations(results));
		assertTrue(report.contains("<html>"));
	}
	
}
