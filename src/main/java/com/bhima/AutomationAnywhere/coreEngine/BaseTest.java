package com.bhima.AutomationAnywhere.coreEngine;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.utils.DevToolsNetworkHelper;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;

public class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    // Add DevTools helper reference
    protected DevTools devTools;
    protected DevToolsNetworkHelper networkHelper;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected static ExtentReports extent;




    @BeforeSuite(alwaysRun = true)
    public void initExtent() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Automation Anywhere Test Report");
        spark.config().setReportName("UI + API Automation Execution");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Bhima Shankar");
    }
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        ChromeDriver chromeDriver = new ChromeDriver(options);
        driver.set(chromeDriver);
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ✅ Create ExtentTest first
        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
        ExtentTestManager.startTest(method.getName(), "Test started");

        // ✅ Initialize DevTools after test node exists
        devTools = chromeDriver.getDevTools();
        devTools.createSession();

        ExtentTestManager.getTest().log(Status.INFO, "✅ Chrome and DevTools initialized successfully.");
    }


    public WebDriver getDriver() {
        return driver.get();
    }

    public void captureScreenshot(String testName) {
        try {
            File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            String screenshotDir = System.getProperty("user.dir") + "/reports/screenshots/";
            Files.createDirectories(Paths.get(screenshotDir));
            String destPath = screenshotDir + testName + ".png";
            Files.copy(screenshot.toPath(), Paths.get(destPath));

            String base64 = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
            test.get().addScreenCaptureFromBase64String(base64, "Screenshot on Failure");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        ExtentTest extentTest = test.get();

        if (extentTest == null) {
            System.out.println("[WARN] ExtentTest was null for " + result.getName());
            return;
        }

        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                extentTest.log(Status.FAIL, " Test Failed: " + result.getThrowable());
                captureScreenshot(result.getName());
                break;
            case ITestResult.SUCCESS:
                extentTest.log(Status.PASS, " Test Passed Successfully!");
                break;
            case ITestResult.SKIP:
                extentTest.log(Status.SKIP, " Test Skipped: " + result.getThrowable());
                break;
        }

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }

        test.remove(); //  clear ThreadLocal
    }


    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }
}
