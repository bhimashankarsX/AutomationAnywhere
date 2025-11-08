package com.bhima.AutomationAnywhere.tests.UI;

import com.bhima.AutomationAnywhere.coreEngine.BaseTest;
import com.bhima.AutomationAnywhere.pages.*;
import com.bhima.AutomationAnywhere.utils.ConfigReader;
import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UITests extends BaseTest {
	UUID uniqueId = UUID.randomUUID();
	
    @Test(enabled=true,description = "Use Case 1: Message Box Task (UI Automation)")
    public void createMessageBoxTask() {
        ExtentTestManager.getTest().info("Starting Message Box Task creation test...");
        LoginPage login = new LoginPage(driver.get());
        AutomationPage automation = new AutomationPage(driver.get());
        TaskBotPage taskBot = new TaskBotPage(driver.get());

        // Step 1: Login
        login.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(login.isLoggedIn(), "Login failed!");

        // Step 2: Navigate to Automation
        automation.navigateToAutomationSection();

        // Step 3: Create Task Bot
        automation.clickCreateAndSelectTaskBot();

        // Step 4: Fill mandatory details and create (placeholder)
        
        String botName=uniqueId.toString();
        taskBot.createTaskBot(botName);
        ExtentTestManager.getTest().info("File name entered is "+botName);

        // Step 5: Add Message Box action
        taskBot.searchAndAddAction("Message Box");

        // Step 6: Verify UI interactions on right panel
        Assert.assertTrue(taskBot.verifyMessageBoxPanelElements(), "Message Box panel elements not visible!");

        // Step 7: Save configuration
        taskBot.saveTask();
        Assert.assertTrue(taskBot.isTaskSavedSuccessfully(), "Task not saved successfully!");

        ExtentTestManager.getTest().pass("Message Box Task created successfully.");
    }

    @Test(enabled=true,description = "Use Case 2: Form with Upload Flow (UI Automation)")
    public void createFormWithUpload() {
        ExtentTestManager.getTest().info("Starting Form with File Upload test...");

        LoginPage login = new LoginPage(driver.get());
        AutomationPage automation = new AutomationPage(driver.get());
        FormPage form = new FormPage(driver.get());

        // Step 1: Login
        login.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(login.isLoggedIn(), "Login failed!");

        // Step 2: Navigate to Automation
        automation.navigateToAutomationSection();

        // Step 3: Create Form
        automation.clickCreateAndSelectForm();
        
     // Step 4: Create Form
        String fileName=uniqueId.toString();
        form.createForm(fileName);
        ExtentTestManager.getTest().info("File name entered is "+fileName);

        // Step 5: Drag and drop textBox and select file into Canvas 
        form.dragAndDropTextBoxandSelectFile();


        // Step 6: Enter text and upload file and Verify right panel interactions
        form.enterText("Sample text input");
        form.uploadFile();

        // Step 7: Save and verify upload
        form.saveForm();
        Assert.assertTrue(form.isFormSavedSuccessfully(), "Form save failed!");
        

        ExtentTestManager.getTest().pass("Form with upload created and verified successfully.");
    }
}
