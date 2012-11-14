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

	private final Reporter reporter = new Reporter();

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
	 * @parameter displayStorySummary
	 */
	private boolean displayStorySummary = true;
	
	/**
	 * @parameter daemon
	 */
	private boolean daemon = false;
	
	/**
	 * @parameter storyUrlTemplate
	 */
	private String storyUrlTemplate = "#%s";

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (daemon) {
			Thread thread = new Thread() {
				@Override
				public void run() { doExecute(); }
			};
			thread.setDaemon(true);
			thread.start();
		} else {
			doExecute();
		}
	}
	
	private void doExecute() {
		PageReader pageReader = displayStorySummary ? new JiraPageReader(userName, password) : new DummyPageReader();
		Collection<StoryData> storyData = TestedStories.find(testDirectries, storyUrlTemplate, pageReader).getStoryDatas();
		reporter.generateReport(storyData, report);
	};
}
