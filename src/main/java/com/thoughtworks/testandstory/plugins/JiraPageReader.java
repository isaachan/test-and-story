package com.thoughtworks.testandstory.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class JiraPageReader implements PageReader
{

    public String getStorySummary(String storyUrl) {
        BufferedReader jiraPage = null;
        try {
            jiraPage = new BufferedReader(new InputStreamReader(new URL(storyUrl).openStream()));
            return getStorySummary(jiraPage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jiraPage != null) {
                try {
                    jiraPage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    // <title>[#LSP-473] to capture the oldest policy holder&#39;s DoB - Suncorp Central Jira </title>
    private String parse(String currentLine)
    {
        return currentLine.replace(" - Suncorp Central Jira </title>", "").substring(currentLine.indexOf(']') + 1);
    }
}
