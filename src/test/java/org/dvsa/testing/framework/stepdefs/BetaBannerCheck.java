package org.dvsa.testing.framework.stepdefs;

import cucumber.api.java8.En;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.junit.Assert;

public class BetaBannerCheck extends BasePage implements En {

    public BetaBannerCheck() {

        Given("^I am on Selfserve homepage$", () -> {
            String env = System.getProperty("env");
            String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();

            if (Browser.isInitialised()) {
                //Quit Browser and open a new window
                Browser.quit();
            }
            Browser.go(myURL);
        });

        Then("^banner colour is blue$", () -> {
            String colour = colourChecker(".phase__tag", "background");
            String[] hexValue =  colour.replace("rgb(", "").replace(")", "").replace("none repeat scroll 0% 0% / auto padding-box border-box","").split(",");
            int hexValue1=Integer.parseInt(hexValue[0]);
            hexValue[1] = hexValue[1].trim();
            int hexValue2=Integer.parseInt(hexValue[1]);
            hexValue[2] = hexValue[2].trim();
            int hexValue3=Integer.parseInt(hexValue[2]);
            String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
            Assert.assertEquals("#005ea5",actualColor);
        });
    }
}