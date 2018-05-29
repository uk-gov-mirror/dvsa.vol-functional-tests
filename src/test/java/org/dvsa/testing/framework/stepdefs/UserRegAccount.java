package org.dvsa.testing.framework.stepdefs;

import activesupport.system.Properties;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.runner.Hooks;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.external.RegisterConfirmationPage;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.dvsa.testing.lib.external.Register;
import org.junit.Assert;

import java.util.regex.Pattern;

public class UserRegAccount implements En {

    public UserRegAccount() {
        Given("I am on the VOL external registration", () -> {
            EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));
            String myURL = URL.build(ApplicationType.EXTERNAL, env, "register").toString() ;

            Browser.go(myURL);
        });

        When("I successfully register an account", () -> {
            Register.createAccount();
        });

        Then("^I should be notified to check my email for temp password$", () -> {
              Assert.assertTrue(
                                Pattern.matches(
                                    RegisterConfirmationPage.checkEmail(),
                                    RegisterConfirmationPage.getConfirmEmailTemppassSentMessageText()
                                )
                    );
        });

        And("^I should see help text for signing in problems$", () -> {
            Assert.assertEquals(RegisterConfirmationPage.signingInProblems(),
                                RegisterConfirmationPage.getEmailNotRecivevedMessage());
        });
    }

    @Before
    public static void setup() {
        Hooks hooks = new Hooks();
        hooks.setup();
    }

    @After
    public void tearDown(){
        Hooks hooks = new Hooks();
        hooks.attach();
    }
}