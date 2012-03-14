package com.thoughtworks.testandstory.plugins;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.err.println("Please specify class directories.");
			System.exit(-1);
		}
		TestInformations testInformations = TestedStories.find(args).get();
		new Reporter().generateReport(testInformations, new File("report.html"));
	}

}