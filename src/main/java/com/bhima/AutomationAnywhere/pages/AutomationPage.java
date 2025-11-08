package com.bhima.AutomationAnywhere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.utils.WaitUtils;

public class AutomationPage {
    private WebDriver driver;
    private By automationMenu = By.xpath("//a[@name='automations']"); 
    private By createDropdown = By.xpath("//span[@data-text='Create']");
    private By taskBotOption = By.xpath("//span[@data-text='Task Bot…']");
   
    private By formName = By.xpath("//input[@name='name']"); 
    private By createedit = By.xpath("//span[@data-text='Create & edit']");
    private By actionsSearchBar = By.xpath("//input[@placeholder='Search actions']");
    private By messageBoxOption = By.xpath("//div[@data-item-name='messagebox#messagebox']"); 

    private By formOption = By.xpath("//span[@data-text='Form…']"); 

    public AutomationPage(WebDriver driver) { this.driver = driver; }

    public void navigateToAutomationSection() {
        WaitUtils.waitForVisibility(driver, automationMenu).click();
       	ExtentTestManager.getTest().info("Clicked on the Automation option from the menu");
    }

    public void clickCreateAndSelectTaskBot() {
        WaitUtils.waitForVisibility(driver, createDropdown).click();
       	ExtentTestManager.getTest().info("Clicked on the Create dropdown");
        WaitUtils.waitForVisibility(driver, taskBotOption).click();
        ExtentTestManager.getTest().info("Clicked on the task bot option from dropdown");
    }

    public void clickCreateAndSelectForm() {
    	 WaitUtils.waitForVisibility(driver, createDropdown).click();
        	ExtentTestManager.getTest().info("Clicked on the Create dropdown");
            WaitUtils.waitForVisibility(driver, formOption).click();
            ExtentTestManager.getTest().info("Clicked on the form option from dropdown");
    }
}
