AutomationAnywhere Framework (Java 17 | Maven)

## Overview

This framework is a **hybrid automation solution** that combines:
- **UI Testing (Selenium WebDriver + TestNG)**
- **API Monitoring (Chrome DevTools Protocol v141)**
- **Reporting (ExtentReports)**
- **Configuration Management (ConfigReader)**

It allows you to:
1. Perform end-to-end UI flows.
2. Capture and validate API calls triggered in the browser via DevTools.
3. Log rich reports with screenshots and network traces.

Run locally:
1. Install Java 17 and Maven.
2. Update src/main/resources/config.properties with credentials.
3. to run from Maven use mvn clean test command for the first time, later we can use mvn test.
4.mvn clean test -DsuiteXmlFile=testng.xml
5. Reports in reports/extent/ExtentReport.html ; HARs in reports/har/

CI:
Push to GitHub (main branch) and workflow will run tests.

 Summary
Feature	Description
Framework Type	Hybrid (UI + DevTools API Validation)
Language	Java
Libraries	Selenium 4.25, TestNG, ExtentReports 5.1.1
Browser Support	Chrome v141+
Reporting	HTML (Extent)
Integration	DevTools Protocol (CDP)

Author: Bhima Shankar S
Purpose: Unified testing framework to validate both frontend and backend API flows via Chrome DevTools.
Status:  Stable for Java 8–17 and Selenium 4.25+.
