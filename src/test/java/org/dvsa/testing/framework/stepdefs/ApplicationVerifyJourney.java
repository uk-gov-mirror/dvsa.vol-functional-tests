package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationVerifyJourney extends BasePage implements En {
    public ApplicationVerifyJourney(World world) {
        Given("^i have an application in progress$", () -> {
            world.APIJourneySteps.createApplication();
            world.UIJourneySteps.navigateToExternalUserLogin(world.createLicence.getLoginId(),world.createLicence.getEmailAddress());
            world.UIJourneySteps.navigateToApplicationReviewDeclarationsPage();
        });
        When("^i choose to sign with verify with \"([^\"]*)\"$", (String arg0) -> {
            String password = "Password1";
            world.UIJourneySteps.signWithVerify(arg0,password);
        });
        Then("^the application should be signed with verify$", () -> {
            waitForTextToBePresent("Review and declarations");
            assertTrue(isTextPresent("Declaration signed through GOV.UK Verify", 10));
        });
    }
}