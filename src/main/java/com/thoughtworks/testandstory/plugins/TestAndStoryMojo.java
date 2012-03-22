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
	private File report = new File("report.html");
	
	/**
	 * @parameter userName;
	 */
	private String userName;
	
	/**
	 * @parameter password;
	 */
	private String password;
	
	/**
	 * @parameter storyUrlTemplate
	 */
	private String storyUrlTemplate = "#%s";
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Collection<StoryData> storyData = TestedStories.find(testDirectries, storyUrlTemplate, new JiraPageReader()).getStoryDatas();
		new Reporter().generateReport(storyData, report);
	}

}
