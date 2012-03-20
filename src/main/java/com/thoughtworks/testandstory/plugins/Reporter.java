package com.thoughtworks.testandstory.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Reporter {

	final ST reportTemplate = new ST(readFileContentAsString("report.st"));

	public String report(List<StoryData> testInformations) {
		if (testInformations.isEmpty()) return "";
		return generateReportForTests(testInformations);
	}

	private String generateReportForTests(List<StoryData> testInformations) {
		StringBuffer reportForStories = new StringBuffer();
//		for (int storyNumber : testInformations.storyNumbers()) {
//			reportForStories.append(reportForStory(testInformations, storyNumber));
//		}
//		
//		reportTemplate.add("stories", reportForStories.toString());
		return reportTemplate.render();
	}

	public void generateReport(List<StoryData> infos, File report) {
		try {
			if (!report.exists()) report.createNewFile();
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
