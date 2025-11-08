package com.bhima.AutomationAnywhere.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.util.Base64;

public class ScreenshotUtils {
    public static String captureBase64(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        byte[] bytes = ts.getScreenshotAs(OutputType.BYTES);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
