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
          isTextPresent("We've sent you an email. Follow the link in the email to reset your password", 30);
        });

        Then("^i will receive an error that username invalid$", () -> {
            isTextPresent("Failed to reset your password", 30);
        });

        And("^i reset my password$", () -> {
            String env = System.getProperty("env");
            world.UIJourneySteps.navigateToExternalUserLogin();
            clickByLinkText("Sign out");
            String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();
            Browser.navigate().get(myURL);
            clickByLinkText("Forgotten your password?");
            enterField(nameAttribute("input", "username"), world.createLicence.getLoginId());
            isTextPresent("Failed", 30);
            click(nameAttribute("input","submit"), SelectorType.CSS);
                while (isTextPresent("Failed", 30)) {
                click(nameAttribute("input","submit"), SelectorType.CSS);
            }

        });
        And("^i try resetting my password$", () -> {
            String env = System.getProperty("env");
            String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();
            Browser.navigate().get(myURL);
            clickByLinkText("Forgotten your password?");
            enterField(nameAttribute("input", "username"), Str.randomWord(14));
            click(nameAttribute("input","submit"), SelectorType.CSS);
        });
        And("^i then try reset my password$", () -> {
            if (Browser.isBrowserOpen()) {
                Browser.navigate().manage().deleteAllCookies();
            }
            String env = System.getProperty("env");
            String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();
            Browser.navigate().get(myURL);
            clickByLinkText("Forgotten your password?");
            enterField(nameAttribute("input", "username"), world.createLicence.getLoginId());
            isTextPresent("Failed", 30);
            click(nameAttribute("input","submit"), SelectorType.CSS);
            isTextPresent("Failed", 30);
            click(nameAttribute("input","submit"), SelectorType.CSS);
        });
        Then("^i will recieve an error for inactive account$", () -> {
            isTextPresent("It looks like your account isn't active", 30);
        });
    }
}
