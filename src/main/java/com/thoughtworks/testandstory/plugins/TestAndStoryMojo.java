package com.thoughtworks.testandstory.plugins;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal criteria
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
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		TestInformations testInformations = TestedStories.find(testDirectries).get();
		new Reporter().generateReport(testInformations, report);
	}

}
