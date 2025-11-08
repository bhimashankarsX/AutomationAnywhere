package com.bhima.AutomationAnywhere.coreEngine;

import com.aventstack.extentreports.Status;
import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.utils.DevToolsNetworkHelper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class DevToolsTestBase extends BaseTest {

    protected DevTools devTools;
    protected DevToolsNetworkHelper networkHelper;

    @BeforeMethod(alwaysRun = true)
    public void setupDevTools() {
        try {
            WebDriver webDriver = getDriver();
            if (webDriver instanceof ChromeDriver) {
                ChromeDriver chromeDriver = (ChromeDriver) webDriver;
                devTools = chromeDriver.getDevTools();
                devTools.createSession();

                networkHelper = new DevToolsNetworkHelper(devTools);
                networkHelper.enableNetwork();

                ExtentTestManager.getTest().log(Status.INFO, "✅ DevTools session started successfully.");
            } else {
                ExtentTestManager.getTest().log(Status.WARNING, "⚠️ DevTools supported only with ChromeDriver instance.");
            }
        } catch (Exception e) {
            if (ExtentTestManager.getTest() != null)
                ExtentTestManager.getTest().log(Status.WARNING, "⚠️ DevTools setup failed: " + e.getMessage());
            else
                System.out.println("⚠️ DevTools setup failed (Extent not ready): " + e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDevTools() {
        try {
            if (networkHelper != null) {
                networkHelper.closeSession();
                networkHelper = null;
            }
            if (devTools != null) {
                devTools.close();
                ExtentTestManager.getTest().log(Status.INFO, "🧹 DevTools session closed.");
            }
        } catch (Exception e) {
            if (ExtentTestManager.getTest() != null)
                ExtentTestManager.getTest().log(Status.WARNING, "⚠️ Failed to close DevTools session: " + e.getMessage());
            else
                System.out.println("⚠️ Failed to close DevTools session: " + e.getMessage());
        }
    }
}
