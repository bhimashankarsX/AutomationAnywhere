package com.bhima.AutomationAnywhere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.utils.WaitUtils;

public class FormPage {
    private WebDriver driver;
    public FormPage(WebDriver driver) { this.driver = driver; }

    private By fileControl = By.xpath("//div[contains(.,'Select File')]"); 
    
    private By nameField = By.xpath("//input[@name='name']"); 
    private By createButton = By.xpath("//span[@data-text='Create & edit']");
    private By formPage = By.xpath("//span[@data-text='Properties - Form']");
    private By textBoxOption = By.xpath("//span[@data-text='Text Box']");
    private By selectFileOption = By.xpath("//span[@data-text='Select File']");
    private By formCanvas = By.xpath("//div[@class='formcanvas__leftpane']");
    private By textBoxCanvas = By.xpath("//input[@aria-label ='TextBox']");
    private By textBoxRightPanel = By.xpath("//span[@data-text='Properties - Text Box']");
    private By selectFileCanvas = By.xpath("//ul[@data-path='1']");
    private By selectFileRightPanel = By.xpath("//span[@data-text='Properties - Select File']");
    private By editorPallete = By.xpath("//div[@class='editor-palette-section__list']");
    private By defaultValueTextBox = By.id("TextBox0-defaultValue");
    private By saveForm = By.xpath("//span[@data-text='Save']");

    
    
   
    
    public void createForm(String name) {
        WaitUtils.waitForVisibility(driver, nameField).sendKeys(name);
        ExtentTestManager.getTest().info("Entered the Form name");
        WaitUtils.waitForVisibility(driver, createButton).click();
        ExtentTestManager.getTest().info("Clicked on the create button");
    }
    
    public boolean verifyRightPanelElements() {
    	WebElement iframeElement=this.driver.findElement(By.className("modulepage-frame"));
    	driver.switchTo().frame(iframeElement);
    	ExtentTestManager.getTest().info("On the right panel, verify every UI element interaction.");
        return WaitUtils.isElementVisible(driver, formPage);
        
    }
    
    public void dragAndDropTextBoxandSelectFile() {
    	Actions actions = new Actions(driver);
    	WebElement iframeElement=this.driver.findElement(By.className("modulepage-frame"));
    	driver.switchTo().frame(iframeElement);
        WebElement button=this.driver.findElement(editorPallete);
    	WebElement textBox=this.driver.findElement(textBoxOption);
    	WebElement selectFile=this.driver.findElement(selectFileOption);
        WebElement canvas=this.driver.findElement(formCanvas);
    	WaitUtils.isElementVisible(driver, editorPallete);
    	ExtentTestManager.getTest().info("EditorPallete is visible");
    	
        WaitUtils.isElementVisible(driver, textBoxOption);
        WaitUtils.isElementVisible(driver, formCanvas);
        actions.moveToElement(button);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", textBox);
        actions.dragAndDrop(textBox, canvas).perform();
        actions.dragAndDrop(selectFile, canvas).perform();
        ExtentTestManager.getTest().info("Performed Drag&Drop operation on textbox and select File into canvas");
    }




    public void enterText(String text) {
    	WaitUtils.waitForVisibility(driver, textBoxCanvas).click();
    	WaitUtils.isElementVisible(driver, textBoxRightPanel);
    	WaitUtils.waitForVisibility(driver, textBoxCanvas).sendKeys(text);
    	WaitUtils.isElementVisible(driver, defaultValueTextBox);
    	String defaultValue = WaitUtils.waitForVisibility(driver, defaultValueTextBox).getAttribute("data-value");
    	ExtentTestManager.getTest().info("DefaultValue Entered in textBox is "+defaultValue);
    }
    

    public void uploadFile() {
    	ExtentTestManager.getTest().info("Could not replicat file upload step manually , file upload typically involves providing the link in the edit box or using AutoIT/Sikuli");
    }

    public void saveForm() {
    	WaitUtils.waitForVisibility(driver, saveForm).click();
    	ExtentTestManager.getTest().info("Clicked on SAVE");
    }

    public boolean isFormSavedSuccessfully() {
    	 if(!WaitUtils.isElementEnabled(driver, saveForm));
         ExtentTestManager.getTest().info("Form saved successfully");
         return true;
    }

}
