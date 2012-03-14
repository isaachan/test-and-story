package com.thoughtworks.testandstory.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reporter {

	final ST reportTemplate = new ST(readFileContentAsString("report.st"));
	final ST storyTemplate = new ST(readFileContentAsString("story.st"));
	final ST testTemplate = new ST(readFileContentAsString("test.st"));

	public String report(TestInformations testInformations) {
		if (testInformations.all().isEmpty()) return "";
		return generateReportForTests(testInformations);
	}

	private String generateReportForTests(TestInformations testInformations) {
		StringBuffer reportForStories = new StringBuffer();
		for (int storyNumber : testInformations.storyNumbers()) {
			reportForStories.append(reportForStory(testInformations, storyNumber));
		}
		
		reportTemplate.add("stories", reportForStories.toString());
		return reportTemplate.render();
	}

	private String reportForStory(TestInformations testInformations, int storyNumber) {
		StringBuffer reportForTestsInStory = new StringBuffer();
		for (TestInformation info : testInformations.getTestInformationsBy(storyNumber)) {
			reportForTestsInStory.append(reportForTest(info));
		}
		storyTemplate.add("number", storyNumber);
		storyTemplate.add("tests", reportForTestsInStory);
		return storyTemplate.render();
	}

	private String reportForTest(TestInformation info) {
		testTemplate.add("type", info.type().toString().toLowerCase());
		testTemplate.add("description", info.getMethodOrClassName());
		return testTemplate.render();
	}

	public void generateReport(TestInformations infos, File report) {
		try {
			FileWriter writer = new FileWriter(report);
			writer.write(report(infos));
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String readFileContentAsString(String templateName) {
		try {
			InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream("templates/" + templateName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(templateStream));
			StringBuffer content = new StringBuffer();
			String currentLine = reader.readLine();
			while(currentLine != null) {
				content.append(currentLine + "\n");
				currentLine = reader.readLine();
			}
			return content.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
