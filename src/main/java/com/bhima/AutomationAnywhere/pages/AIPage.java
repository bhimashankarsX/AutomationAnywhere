package com.bhima.AutomationAnywhere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.utils.WaitUtils;

public class AIPage {
	
	 private WebDriver driver;
	    public AIPage(WebDriver driver) { this.driver = driver; }

	    private By AIMenu = By.xpath("//button[@name='ai']"); 
	    private By documentAutomation = By.xpath("//a[@aria-label='Document Automation']"); 
	    private By createLearningInstance = By.xpath("//button[@aria-label='Create Learning Instance']");
	    private By instanceName = By.xpath("//input[@name='name']"); 
	    private By instanceNext = By.xpath("//span[@data-text='Next']"); 
	    private By createInstace = By.xpath("//button[@aria-label='Create']");
	    
	    public void navigateToAISection() {
	        WaitUtils.waitForVisibility(driver, AIMenu).click();
	       	ExtentTestManager.getTest().info("Clicked on the AI option from the menu");
	    }
	    
	    public void clickOnDocumentAutomation() {
	        WaitUtils.waitForVisibility(driver, documentAutomation).click();
	       	ExtentTestManager.getTest().info("Clicked on the Document Automation from the menu");
	    }
	    
	    public boolean switchFramesCreateInstance() {
	    	WebElement iframeElement=this.driver.findElement(By.className("modulepage-frame"));
	    	driver.switchTo().frame(iframeElement);
	    	ExtentTestManager.getTest().info("On the right panel, verify every UI element interaction.");
	        return WaitUtils.isElementVisible(driver, createLearningInstance);
	        
	    }
	    
	    public void clickOnCreateLearningInstance() {
	        WaitUtils.waitForVisibility(driver, createLearningInstance).click();
	       	ExtentTestManager.getTest().info("Clicked on the Create learning instance from the right panel");
	    }
	    
	    public void enterName(String name) {
	        WaitUtils.waitForVisibility(driver, instanceName).sendKeys(name);
	        ExtentTestManager.getTest().info("Entered the Instance name");
	        WaitUtils.waitForVisibility(driver, instanceNext).click();
	        ExtentTestManager.getTest().info("Clicked on the Next button");
	    }
	    
	    public void clickOnCreate() {
	        WaitUtils.waitForVisibility(driver, createInstace).click();
	       	ExtentTestManager.getTest().info("Clicked on the Create learning instance from the right panel");
	    }
	    
	    
}
