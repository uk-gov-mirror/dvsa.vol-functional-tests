package org.dvsa.testing.framework.util;

import cucumber.api.Scenario;
import org.dvsa.testing.lib.browser.Browser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

public class Util {

    public static void embedScreenShot(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] screenShot = ((TakesScreenshot) Browser.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenShot, "image/png");
            }
        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
        }
    }

}
