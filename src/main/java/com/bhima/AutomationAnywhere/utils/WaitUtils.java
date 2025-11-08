package com.bhima.AutomationAnywhere.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public static WebElement waitForVisibility(WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(element));
    }
    
    public static boolean isElementVisible(WebDriver driver, By locator) {
        try {
            return waitForVisibility(driver, locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    public static boolean isElementEnabled(WebDriver driver, By locator) {
        try {
            return waitForVisibility(driver, locator).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
