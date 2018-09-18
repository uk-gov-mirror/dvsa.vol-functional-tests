package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.IllegalBrowserException;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationVerifyJourney extends BasePage implements En {
    public ApplicationVerifyJourney(World world) {
        Given("^i have an application in progress$", () -> {
            world.createLicence.setIsInterim("N");
            world.APIJourneySteps.createApplication();
        });
        When("^i choose to sign with verify with \"([^\"]*)\"$", (String arg0) -> {
            String password = "Password1";
            world.UIJourneySteps.navigateToExternalReviewAndDeclarationsPage();
            world.UIJourneySteps.signWithVerify(arg0,password);
        });
        Then("^the application should be signed with verify$", () -> {
            waitForTextToBePresent("Review and declarations");
            assertTrue(isTextPresent("Declaration signed through GOV.UK Verify", 10));
        });
    }
}