package org.dvsa.testing.framework.stepdefs;

import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.external.RegisterConfirmationPage;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;
import org.dvsa.testing.lib.external.Register;
import org.junit.Assert;

import java.util.regex.Pattern;

public class UserRegAccount implements En {

    public UserRegAccount() {
        Given("I am on the VOL External registration", () -> {
            EnvironmentType env = Environment.enumType(Properties.get("env", true));
            String URL = URI.build(ApplicationType.EXTERNAL, env, "register") ;

            Browser.go(URL);
        });

        When("I successfully register an account", () -> {
            Register.createAccount();
        });

        Then("^I should be notified to check my email for temp password$", () -> {
            Assert.assertTrue(Pattern.matches(RegisterConfirmationPage.checkEmail().pattern(),
                    RegisterConfirmationPage.getConfirmEmailTemppassSentMessageText()
            ));
        });

        And("^I should see help text for signing in problems$", () -> {
            Assert.assertEquals(RegisterConfirmationPage.signingInProblems(), RegisterConfirmationPage.getEmailNotRecivevedMessage());
        });
    }
}
