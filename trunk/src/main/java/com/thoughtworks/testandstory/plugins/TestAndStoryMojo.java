package com.thoughtworks.testandstory.plugins;

import java.io.File;
import java.util.Collection;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal criteria
 * @phase integration
 */
public class TestAndStoryMojo extends AbstractMojo {

	/**
	 * @parameter testDirectries
	 */
	private String[] testDirectries;
	
	/**
	 * @parameter report
	 */
	private File report;
	
	/**
	 * @parameter storyUrlTemplate
	 */
	private String storyUrlTemplate;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (report == null) { report = new File("report.html"); }
		
		Collection<StoryData> storyData = TestedStories.find(testDirectries, storyUrlTemplate).getStoryDatas();
		new Reporter().generateReport(storyData, report);
	}

}
