package com.bhima.AutomationAnywhere.tests.api;

import com.aventstack.extentreports.Status;
import com.bhima.AutomationAnywhere.coreEngine.DevToolsTestBase;
import com.bhima.AutomationAnywhere.pages.AIPage;
import com.bhima.AutomationAnywhere.pages.LoginPage;
import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.utils.ConfigReader;

import org.openqa.selenium.devtools.v141.network.Network;
import org.openqa.selenium.devtools.v141.network.model.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Use Case 3: Learning Instance API Flow (UI + DevTools API validation)
 */
public class LearningInstanceTest extends DevToolsTestBase {

    @Test(description = "Validate Learning Instance creation API using Chrome DevTools Network Interception")
    public void validateLearningInstanceCreationAPI() throws InterruptedException {

        UUID uniqueId = UUID.randomUUID();
        String name = uniqueId.toString();
        ExtentTestManager.getTest().info("Starting Learning Instance API Flow via DevTools...");

        LoginPage login = new LoginPage(driver.get());
        AIPage aiPage = new AIPage(driver.get());

        // Step 1: Login to the Application
        login.open();
        login.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(login.isLoggedIn(), "Login failed!");

        // Step 2: Start listening for API responses
        AtomicReference<Response> createInstanceResponse = new AtomicReference<>();

        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            String url = response.getUrl();

            if (url.contains("learninginstances") || url.contains("learning-instance")) {
                createInstanceResponse.set(response);
                ExtentTestManager.getTest().log(Status.INFO, " Captured API call: " + url);
            }
        });

        // Step 3: Navigate to AI → Learning Instance page and trigger instance creation
        aiPage.navigateToAISection();
        aiPage.clickOnDocumentAutomation();
        ExtentTestManager.getTest().info("Navigating to AI → Learning Instance");

        aiPage.switchFramesCreateInstance();
        Thread.sleep(3000);

        ExtentTestManager.getTest().info("Triggering Learning Instance creation via UI...");
        aiPage.clickOnCreateLearningInstance();
        aiPage.enterName(name);
        ExtentTestManager.getTest().info("Entered Instance name: " + name);
        aiPage.clickOnCreate();
        ExtentTestManager.getTest().info("Learning instance creation triggered...");

        Thread.sleep(4000); // Wait for network capture

        // Step 4: Validate captured API response
        Response response = createInstanceResponse.get();
        Assert.assertNotNull(response, " No API response captured for Learning Instance creation!");

        int statusCode = response.getStatus();
        String apiUrl = response.getUrl();
        //Optional<String> mimeType = response.getMimeType();

        ExtentTestManager.getTest().info(" API URL: " + apiUrl);
        ExtentTestManager.getTest().info(" Status Code: " + statusCode);
        //ExtentTestManager.getTest().info("✅ Mime Type: " + mimeType.orElse("unknown"));

        Assert.assertTrue(statusCode == 200 || statusCode == 201, "Unexpected status code!");

        ExtentTestManager.getTest().pass(" Learning Instance created successfully (Status: " + statusCode + ")");
    }
}
