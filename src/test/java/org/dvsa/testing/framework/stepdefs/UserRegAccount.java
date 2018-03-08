package org.dvsa.testing.framework.stepdefs;

import activesupport.system.Properties;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;
import org.dvsa.testing.lib.external.Register;
import org.junit.Assert;

public class UserRegAccount implements En {

    public UserRegAccount(){
        Given("I am on the VOL External registration", () -> {
            EnvironmentType env = Environment.enumType(Properties.get("env", true));
            String URL = URI.build(ApplicationType.EXTERNAL, env);

            Browser.go(URL);
        });

        When("I successfully register an account",() ->{
            Register.createAccount();
        });
        Then("^I should be notified to check my email for temp password$", () -> {
            Assert.assertEquals("We have sent an email to".concat( "containing a temporary password. Once you’ve signed in using the temporary password you will need to create a new password"), "We have sent an email to\".concat( \"containing a temporary password. Once you’ve signed in using the temporary password you will need to create a new password");
            Assert.assertEquals("", "Problems signing in?\n" +
                    "If our email does not appear in your inbox within 5 minutes, check your Spam or Junk folder or contact your IT department for help. If our email is in your Spam or Junk folder, please change your email settings so that further emails from us are directed to your inbox. If you have issues signing in or didn’t receive our email you will need to contact notifications@vehicle-operator-licensing.service.gov.uk.");
        });
    }

}
