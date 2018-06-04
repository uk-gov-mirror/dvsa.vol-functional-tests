package org.dvsa.testing.framework.stepdefs;

import activesupport.aws.s3.S3;
import activesupport.system.Properties;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.runner.Hooks;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.external.RegisterConfirmationPage;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.dvsa.testing.lib.external.Register;
import org.junit.Assert;

import java.util.regex.Pattern;

public class UserRegAccount extends BasePage implements En {
    private World world;

    public UserRegAccount(World world) {
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
        And("^I should be able to login into my new account$", () -> {
            clickByLinkText("sign");
            String password = S3.getTempPassword(RegisterConfirmationPage.checkEmail());

            if (isTextPresent("Username", 60))
                Login.signIn(world.createLicence.getLoginId(), password);
            if (isTextPresent("Current password", 60)) {
                enterField(nameAttribute("input", "oldPassword"), password);
                enterField(nameAttribute("input", "newPassword"), "Password1");
                enterField(nameAttribute("input", "confirmPassword"), "Password1");
                click(nameAttribute("input", "submit"));
            }


        });
    }

    @After
    public void tearDown(){
        Hooks hooks = new Hooks();
        hooks.attach();
    }
}