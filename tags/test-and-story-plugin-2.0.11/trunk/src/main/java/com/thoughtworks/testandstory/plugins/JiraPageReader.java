package com.thoughtworks.testandstory.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class JiraPageReader implements PageReader {

    private final String userName;
	private final String password;

	public JiraPageReader(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getStorySummary(String storyUrl) {
        BufferedReader jiraPage = null;
        String storyUrlWithAuth = null;
        try {
            storyUrlWithAuth = String.format("%s?os_username=%s&os_password=%s", storyUrl, userName, password);
			jiraPage = new BufferedReader(new InputStreamReader(new URL(storyUrlWithAuth).openStream()));
            return getStorySummary(jiraPage);
        } catch (IOException e) {
            System.out.println("[ERROR]: Failed to load page " + storyUrlWithAuth);
        } finally {
            if (jiraPage == null) return "Not Found";
            try {
                jiraPage.close();
            } catch (IOException ignore) {}     
        }
        return "Not Found";
    }
    
    public String getStorySummary(BufferedReader jiraPage) throws IOException {
        String currentLine = jiraPage.readLine();
        while (currentLine != null) {
            if (currentLine.trim().startsWith("<title>")) {
                return parse(currentLine);
            }
            currentLine = jiraPage.readLine();
        }
        return "Not Found";
    }

    // <title>[#LSP-473] to capture the oldest policy holder's DoB - Suncorp Central Jira </title>
    private String parse(String currentLine)
    {
        return currentLine.replace(" - Suncorp Central Jira </title>", "").substring(currentLine.indexOf(']') + 1);
    }
}
