package com.thoughtworks.testandstory.plugins;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reporter {

	final ST reportTemplate = new ST(readFileContentAsString("report.st"));
	final ST storyTemplate = new ST(readFileContentAsString("story.st"));
	final ST testTemplate = new ST(readFileContentAsString("test.st"));

	public String report(TestInformations testInformations) {
		if (testInformations.all().isEmpty()) return "";
		
		StringBuffer snappitForStories = new StringBuffer();
		for (int storyNumber : testInformations.storyNumbers()) {
			StringBuffer snappitForTests = new StringBuffer();
			for (TestInformation info : testInformations.getTestInformationsBy(storyNumber)) {
				testTemplate.add("type", info.type().toString().toLowerCase());
				testTemplate.add("description", info.getDescription());
				snappitForTests.append(testTemplate.render());
			}
			storyTemplate.add("number", storyNumber);
			storyTemplate.add("tests", snappitForTests);
			snappitForStories.append(storyTemplate.render());
		}
		
		reportTemplate.add("stories", snappitForStories.toString());
		return reportTemplate.render();
	}

	public void generateReport(TestInformations infos) throws IOException {
		FileWriter writer = new FileWriter("report.html");
		writer.write(report(infos));
		writer.close();
	}
	
	private String readFileContentAsString(String templateName) {
		try {
//			BufferedReader reader = new BufferedReader(new FileReader("./templates/" + templateName));
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("./templates/" + templateName)));
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
