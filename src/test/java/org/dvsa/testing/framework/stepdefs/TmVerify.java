package org.dvsa.testing.framework.stepdefs;

import activesupport.IllegalBrowserException;
import activesupport.driver.Browser;
import activesupport.system.Properties;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TmVerify extends BasePage implements En {
    public TmVerify(World world) {
        Given("^the self-service user has successfully signed the TM application through Verify$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the 'Awaiting operator review' post signature page is displayed showing the correct information$", () -> {
            //need to split out
            String name = world.createLicence.getForeName() + " " + world.createLicence.getFamilyName();
            assertTrue(isTextPresent(name, 30));
            assertTrue(isTextPresent("What happens next", 30));
            assertTrue(isTextPresent("Awaiting operator review", 30));
            assertTrue(isTextPresent("Declaration signed through GOV.UK Verify", 30));
            assertTrue(isTextPresent("You've submitted your details to the operator. We'll let you know once they've been reviewed.", 30));
            assertTrue(isElementPresent("//button[@class='govuk-button']", SelectorType.XPATH));
        });
        And("^the confirmation panel is displaying the correct assets$", () -> {
            Assert.assertEquals("#fff", Color.fromString(confirmationPanel("color")).asHex());
            Assert.assertEquals("#28a197", Color.fromString(confirmationPanel("background-color")).asHex());
        });
        When("^the user has been redirected to the awaiting confirmation page$", () -> {
            EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));
            String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();
            String url = Browser.navigate().getCurrentUrl();
            assertEquals(myURL, url);
        });
        Given("^the operator has chosen to counter sign the application by print$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^the user is on the print sign page$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^print details like will open in a new tab$", () -> {
            switchTab(0);
        });
        And("^the following \"([^\"]*)\" text will be displayed on the page$", (String arg0) -> {
            assertTrue(isElementPresent(arg0));
        });
        Given("^the operator is on check your answers page$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the correct headings, data and links should be displayed for Your details section$", () -> {
            //TODO: This is written for the prototype please amend
            assertTrue(isTextPresent("Your details", 30));
            assertTrue(isTextPresent("Phil Jowett", 30));
            assertTrue(isTextPresent("11/11/2000", 30));
            assertTrue(isTextPresent("Nottingham", 30));
            assertTrue(isTextPresent("Nottingham", 30));
            assertTrue(isTextPresent("certificate attached", 30));
            //TODO: Add link
            assertTrue(isElementPresent(""));
        });
        Then("^the correct headings, data and links should be displayed for Responsibilities section$", () -> {
            assertTrue(isTextPresent("Responsibilities", 30));
            assertTrue(isTextPresent("test", 30));
            assertTrue(isTextPresent("test", 30));
            //TODO: Add link
            assertTrue(isElementPresent(""));
        });
        Then("^the correct headings, data and links should be displayed for Hours per week section$", () -> {
            assertTrue(isTextPresent("Hours per week", 30));
            assertTrue(isTextPresent("8", 30));
            assertTrue(isTextPresent("8", 30));
            assertTrue(isTextPresent("8", 30));
            assertTrue(isTextPresent("8", 30));
            assertTrue(isTextPresent("8", 30));
            assertTrue(isTextPresent("8", 30));
            assertTrue(isTextPresent("8", 30));
            //TODO: Add link
            assertTrue(isElementPresent(""));
        });


        Then("^the correct headings, data and links should be displayed for Other licences section$", () -> {
            assertTrue(isTextPresent("Other licences", 30));
            assertTrue(isTextPresent("non added", 30));
            //TODO: Add link
            assertTrue(isElementPresent(""));
        });
        Then("^the correct headings, data and links should be displayed for Additional information section$", () -> {
            assertTrue(isTextPresent("Other licences", 30));
            assertTrue(isTextPresent("non added", 30));
            //TODO: Add link
            assertTrue(isElementPresent(""));
        });
        Then("^the correct headings, data and links should be displayed for Other employment section$", () -> {
            assertTrue(isTextPresent("Other Employment", 30));
            assertTrue(isTextPresent("non added", 30));
            //TODO: Add link
            assertTrue(isElementPresent(""));
        });
        Then("^the correct headings, data and links should be displayed for Convictions & Penalties section$", () -> {
            assertTrue(isTextPresent("Convictions & Penalties", 30));
            assertTrue(isTextPresent("information added", 30));
            //TODO: Add link
            assertTrue(isElementPresent(""));
        });
        Then("^the correct headings, data and links should be displayed for Revoked, curtailed or suspended licences section$", () -> {
            assertTrue(isTextPresent("Revoked Curtailed or Suspended Licences", 30));
            assertTrue(isTextPresent("information added", 30));
            //TODO: Add link
            assertTrue(isElementPresent(""));
        });
    }

    private String confirmationPanel(String cssValue) throws IllegalBrowserException {
        return Browser.navigate().findElement(By.xpath("//div[@class='govuk-panel govuk-panel--confirmation']")).getCssValue(cssValue);
    }

    private void switchTab(int tab) throws IllegalBrowserException {
        ArrayList<String> tabs = new ArrayList<>(Browser.navigate().getWindowHandles());
        Browser.navigate().switchTo().window(tabs.get(tab));
    }
}