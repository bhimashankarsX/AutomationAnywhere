package com.bhima.AutomationAnywhere.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized ExtentTest startTest(String name, String description) {
        ExtentTest test = extent.createTest(name, description);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() { return extentTest.get(); }
}
