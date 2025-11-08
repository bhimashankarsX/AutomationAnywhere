package com.bhima.AutomationAnywhere.utils;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;

public class HarUtils {
    public BrowserMobProxy startProxy() {
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(0);
        return proxy;
    }

    public WebDriver createDriverWithProxy(BrowserMobProxy proxy, boolean headless) {
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        ChromeOptions options = new ChromeOptions();
        options.setProxy(seleniumProxy);
        if (headless) options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        return new ChromeDriver(options);
    }

    public String saveHar(BrowserMobProxy proxy, String harName) {
        try {
            Har har = proxy.getHar();
            File dir = new File("reports/har");
            if (!dir.exists()) dir.mkdirs();
            File harFile = new File(dir, harName + ".har");
            har.writeTo(harFile);
            return harFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write HAR file", e);
        }
    }

    public void stopProxy(BrowserMobProxy proxy) {
        if (proxy != null) proxy.stop();
    }
}
