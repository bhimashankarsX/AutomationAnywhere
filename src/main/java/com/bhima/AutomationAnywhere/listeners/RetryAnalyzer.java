package com.bhima.AutomationAnywhere.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.bhima.AutomationAnywhere.utils.ConfigReader;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private final int maxRetry = Integer.parseInt(ConfigReader.get("retry.count", "2"));

    @Override
    public boolean retry(ITestResult result) {
        boolean enabled = Boolean.parseBoolean(ConfigReader.get("retry.enabled", "true"));
        if (!enabled) return false;
        if (retryCount < maxRetry) {
            retryCount++;
            return true;
        }
        return false;
    }
}
