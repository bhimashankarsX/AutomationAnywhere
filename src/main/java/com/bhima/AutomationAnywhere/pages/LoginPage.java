package com.bhima.AutomationAnywhere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import com.bhima.AutomationAnywhere.utils.WaitUtils;

public class LoginPage {
    private WebDriver driver;
    private By usernameField = By.name("username"); 
    private By passwordField = By.name("password"); 
    private By loginButton = By.name("submitLogin");
    private By automationMenu = By.xpath("//a[@name='automations']"); 

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(com.bhima.AutomationAnywhere.utils.ConfigReader.get("baseUrl"));
    }

    public void login(String username, String password) {
        open();
        WaitUtils.waitForVisibility(driver, usernameField).sendKeys(username);
        ExtentTestManager.getTest().info("Entered username: " + username);
        driver.findElement(passwordField).sendKeys(password);
        ExtentTestManager.getTest().info("Entered password: " + "****");
        driver.findElement(loginButton).isEnabled();
        driver.findElement(loginButton).click();
        ExtentTestManager.getTest().info("clicked on login button");
    }

    public boolean isLoggedIn() {
    	 ExtentTestManager.getTest().info("Verified if the homepage is visible");
        return WaitUtils.isElementVisible(driver, automationMenu);
       
    }
}
