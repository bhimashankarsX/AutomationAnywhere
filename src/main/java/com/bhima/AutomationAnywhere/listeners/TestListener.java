package com.bhima.AutomationAnywhere.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.reporting.ExtentManager;
import com.bhima.AutomationAnywhere.utils.ScreenshotUtils;
import com.bhima.AutomationAnywhere.coreEngine.DriverFactory;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {
    @Override
    public void onStart(ITestContext context) { }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.startTest(result.getMethod().getMethodName(), "");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                String base64 = ScreenshotUtils.captureBase64(driver);
                ExtentTestManager.getTest().addScreenCaptureFromBase64String(base64, "Failure Screenshot");
            } else {
                ExtentTestManager.getTest().warning("Driver instance is null, cannot capture screenshot.");
            }
        } catch (Exception e) {
            ExtentTestManager.getTest().warning("Could not capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }
}
