package com.bhima.AutomationAnywhere.utils;

import com.aventstack.extentreports.Status;
import com.bhima.AutomationAnywhere.reporting.ExtentTestManager;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v141.network.Network;

import java.util.Optional;

public class DevToolsNetworkHelper {

    private final DevTools devTools;

    public DevToolsNetworkHelper(DevTools devTools) {
        this.devTools = devTools;
    }

    public void enableNetwork() {
        devTools.send(Network.enable(
                Optional.empty(),  // maxTotalBufferSize
                Optional.empty(),  // maxResourceBufferSize
                Optional.empty(),  // maxPostDataSize
                Optional.of(true), // enablePostData
                Optional.empty()   // disableCache
        ));

        ExtentTestManager.getTest().log(Status.INFO, "🌐 Network tracking enabled via DevTools (v141).");
    }

    public void closeSession() {
        try {
            devTools.clearListeners();
            ExtentTestManager.getTest().log(Status.INFO, "🧹 Network tracking stopped and listeners cleared.");
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "⚠️ Failed to close DevTools session: " + e.getMessage());
        }
    }
}
