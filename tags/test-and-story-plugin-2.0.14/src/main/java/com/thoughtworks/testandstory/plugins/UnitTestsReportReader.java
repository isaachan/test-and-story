package com.thoughtworks.testandstory.plugins;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class UnitTestsReportReader {

    private String getAllJSONStory(BufferedReader unitTestsReportReader) throws IOException {
        String currentLine = unitTestsReportReader.readLine();
        while (currentLine != null){
            if(currentLine.trim().equals("**/")){
                return parse(unitTestsReportReader.readLine());
            }
            currentLine = unitTestsReportReader.readLine();
        }
        return "";
    }

    private String getAllJSONStory(String storyURL){
        BufferedReader unitTestsReportReader = null;
        try {
            unitTestsReportReader = new BufferedReader(new InputStreamReader(new URL(storyURL).openStream()));
            return getAllJSONStory(unitTestsReportReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (unitTestsReportReader != null) {
                try {
                    unitTestsReportReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "Not Found";
    }

    private String parse(String currentLine)  {
        return currentLine.substring(currentLine.indexOf("["), currentLine.lastIndexOf("]") + 1);
    }

    public List<StoryData> getAllStoryObjects(String storyURL) throws IOException {
        String stories = getAllJSONStory(storyURL);
        return new Gson().fromJson(stories, new TypeToken<List<StoryData>>() {}.getType());
    }

    public List<StoryData> getAllStoryObjects(BufferedReader unitTestsReportReader) throws IOException {
        String stories = getAllJSONStory(unitTestsReportReader);
        return new Gson().fromJson(stories, new TypeToken<List<StoryData>>() {}.getType());
    }
}
