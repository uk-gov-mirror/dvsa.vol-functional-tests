package org.dvsa.testing.framework.stepdefs;

import activesupport.IllegalBrowserException;
import activesupport.driver.Browser;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TmVerify extends BasePage implements En {
    public TmVerify(World world) {
        Given("^the self-service user has successfully signed the TM application through Verify$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the 'Awaiting operator review' post signature page is displayed showing the correct information$", () -> {
            String name = world.createLicence.getForeName() + " " + world.createLicence.getFamilyName();
            assertTrue(isTextPresent(name ,30));
            assertTrue(isTextPresent("What happens next" ,30));
            assertTrue(isTextPresent("Awaiting operator review" ,30));
            assertTrue(isTextPresent("Declaration signed through GOV.UK Verify" ,30));
            assertTrue(isTextPresent("You've submitted your details to the operator. We'll let you know once they've been reviewed." ,30));
            assertTrue(isElementPresent("//button[@class='govuk-button']" , SelectorType.XPATH));
        });
        And("^the confirmation panel is displaying the correct assets$", () -> {
            Assert.assertEquals("#fff",  Color.fromString(confirmationPanel("color")).asHex());
            Assert.assertEquals("#28a197",  Color.fromString(confirmationPanel("background-color")).asHex());
        });
    }

    private String confirmationPanel(String cssValue) throws IllegalBrowserException {
        return Browser.navigate().findElement(By.xpath("//div[@class='govuk-panel govuk-panel--confirmation']")).getCssValue(cssValue);
    }
}