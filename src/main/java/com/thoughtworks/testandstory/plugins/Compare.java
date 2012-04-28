package com.thoughtworks.testandstory.plugins;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Compare {

    private static boolean checkArguments(String[] args) {
        if (args.length != 2) {
            return false;
        }

        try {
            Integer.parseInt(args[0]);
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static void printUseage() {
        System.out.println("Useage: java -cp test-and-story.jar com.thoughtworks.testandstory.plugin.Compare [BUILD NUMBER] [BUILD NUMBER]");
    }

    private static void execute(String[] args, UnitTestsReportComparator comparator) throws IOException {
        int latestUnitReportBuildNumber = Integer.valueOf(args[0]) > Integer.valueOf(args[1]) ? Integer.valueOf(args[0]) : Integer.valueOf(args[1]);
        int oldUnitReportBuildNumber = Integer.valueOf(args[0]) < Integer.valueOf(args[1]) ? Integer.valueOf(args[0]) : Integer.valueOf(args[1]);
        List<StoryData> resultUnitTests = comparator.process(latestUnitReportBuildNumber, oldUnitReportBuildNumber);
        Reporter reporter = new Reporter();
        File report = new File("compare-report.html");
        reporter.generateReport(resultUnitTests, report);
    }

    public static void main(String[] args) throws IOException {
        UnitTestsReportComparator comparator = new UnitTestsReportComparator();
        if(!checkArguments(args)){
            printUseage();
            return;
        }
        execute(args, comparator);
    }
}
