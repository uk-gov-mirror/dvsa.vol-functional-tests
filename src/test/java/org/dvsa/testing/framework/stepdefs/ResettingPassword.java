package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import activesupport.string.Str;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.junit.Assert;

import javax.xml.xpath.XPath;

public class ResettingPassword extends BasePage implements En {
    public ResettingPassword(World world) {
        Then("^i will receive a message to say my password has changed$", () -> {

        });

        Then("^i will receive an error that username invalid$", () -> {
            Assert.assertEquals("Failed to reset your password",getText(nameAttribute("",""), SelectorType.CSS));
        });

        And("^i reset my password$", () -> {
            String env = System.getProperty("env");
            String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();
            Browser.navigate().get(myURL);
            clickByLinkText("Forgotten your password?");
            enterField(nameAttribute("", ""), world.createLicence.getUsername());
            click("", SelectorType.XPATH);
        });
        And("^i try resetting my password$", () -> {
            String env = System.getProperty("env");
            String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();
            Browser.navigate().get(myURL);
            clickByLinkText("Forgotten your password?");
            enterField(nameAttribute("", ""), Str.randomWord(14));
            click("", SelectorType.XPATH);
        });
    }
}
