package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

public class JiraPageReaderTest
{

    private BufferedReader jiraPage;

    @Before
    public void load_jira_page() {
        jiraPage = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("LSP-473.html")));
    }
    
    @Test
    public void should_get_story_summary_from_jira_page() throws IOException {
        assertEquals(" to capture the oldest policy holder&#39;s DoB", new JiraPageReader("", "").getStorySummary(jiraPage));
    }

  
}
