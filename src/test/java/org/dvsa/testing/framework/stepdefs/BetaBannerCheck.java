package org.dvsa.testing.framework.stepdefs;

import activesupport.driver.Browser;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;

public class BetaBannerCheck extends BasePage implements En {

    public BetaBannerCheck() {

        Given("^I am on Selfserve homepage$", () -> {
            String env = System.getProperty("env");
            String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();
            Browser.navigate().get(myURL);
        });

        Then("^banner colour is blue$", () -> {
            String bannerColour = Browser.navigate().findElement(By.xpath("//*[@class='phase__tag']")).getCssValue("background-color");
            String hex = Color.fromString(bannerColour).asHex();
            Assert.assertEquals("#005ea5", hex);
        });
    }
}