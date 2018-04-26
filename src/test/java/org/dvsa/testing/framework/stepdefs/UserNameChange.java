package org.dvsa.testing.framework.stepdefs;

import activesupport.string.Str;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.InternalSiteObject;
import org.dvsa.testing.framework.stepdefs.Utils.SelfserveSiteObjects;
import org.dvsa.testing.lib.pages.BasePage;

public class UserNameChange extends BasePage implements En {
    String FIRST_NAME  = Str.randomWord(7);
    String LAST_NAME = Str.randomWord(7);

    public UserNameChange() {
        Given("^I change user name$", () -> {
            SelfserveSiteObjects.loginSS();
            SelfserveSiteObjects.yourAccountLink();
            SelfserveSiteObjects.userNameChange(FIRST_NAME, LAST_NAME);
        });
        When("^I make a change on my application$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^the change should be logged against the new username$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
