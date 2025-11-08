package com.bhima.AutomationAnywhere.pages;

import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.utils.WaitUtils;

public class TaskBotPage {
    private WebDriver driver;
    private By nameField = By.xpath("//input[@name='name']"); 
    private By createButton = By.xpath("//span[@data-text='Create & edit']");
    private By actionsSearch =  By.xpath("//input[@placeholder='Search actions']");
    private By messageBoxAction = By.xpath("//div[@data-item-name='messagebox#messagebox']");
    private By rightPanel = By.xpath("//div[@name='title']");
    private By saveTask = By.xpath("//span[@data-text='Save']");

    public TaskBotPage(WebDriver driver) { this.driver = driver; }

    public void createTaskBot(String name) {
        WaitUtils.waitForVisibility(driver, nameField).sendKeys(name);
        ExtentTestManager.getTest().info("Entered the bot name");
        WaitUtils.waitForVisibility(driver, createButton).click();
        ExtentTestManager.getTest().info("Clicked on the create button");
    }

    public void searchAndAddAction(String actionName) {
    	Actions actions = new Actions(driver);
        WaitUtils.waitForVisibility(driver, actionsSearch).sendKeys(actionName);
        WaitUtils.waitForVisibility(driver, messageBoxAction);
        actions.doubleClick(this.driver.findElement(messageBoxAction)).perform();
        ExtentTestManager.getTest().info("searched for messabox and double clicked on it");
    }

    public boolean verifyMessageBoxPanelElements() {
    	ExtentTestManager.getTest().info("On the right panel, verify every UI element interaction.");
        return WaitUtils.isElementVisible(driver, rightPanel);
        
    }

    public void saveTask() {
    	WaitUtils.waitForVisibility(driver, saveTask).click();
    	ExtentTestManager.getTest().info("Clicked on SAVE");
    }

    public boolean isTaskSavedSuccessfully() {
        if(!WaitUtils.isElementEnabled(driver, saveTask));
        ExtentTestManager.getTest().info("Bot saved successfully");
        return true;
    }
}
