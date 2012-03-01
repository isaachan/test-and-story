package com.thoughtworks.testandstory.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal criteria
 */
public class TestAndStoryMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		System.out.println("=========================================");
		System.out.println("            The System Criteria          ");
		System.out.println("=========================================");
	}

}
