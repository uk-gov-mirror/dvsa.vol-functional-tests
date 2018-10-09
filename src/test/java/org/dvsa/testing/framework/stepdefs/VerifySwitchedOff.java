package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VerifySwitchedOff extends BasePage implements En {
    String flag;

    public VerifySwitchedOff(World world) {
        Given("^I have a \"([^\"]*)\" \"([^\"]*)\" application$", (String arg0, String arg1) -> {
            world.genericUtils = new GenericUtils(world);
            world.createLicence.setOperatorType(arg0);
            world.APIJourneySteps.createPartialApplication();
            if (arg1.equals("NI")) {
                world.APIJourneySteps.nIAddressBuilder();
            }
            world.APIJourneySteps.createApplication();
        });
        Then("^Signing options are not displayed on the page$", () -> {
            assertFalse(isElementPresent("//*[@type='radio']", SelectorType.XPATH));
            assertFalse(isTextPresent("How would you like to sign the declaration?", 20));
        });
        And("^submit to operator button is displayed$", () -> {
            assertTrue(isElementPresent("//*[@id='form-actions[submitToOperator]']"));
        });
        And("^submit to operator button is not displayed$", () -> {
            assertTrue(isElementPresent("//*[@id='form-actions[submitToOperator]']"));
        });
        And("^i add a transport manager$", () -> {
            world.UIJourneySteps.navigateToExternalUserLogin();
            clickByLinkText(world.createLicence.getApplicationNumber());
            world.UIJourneySteps.addExistingPersonAsTransportManager();
        });
        When("^the transport manager is the owner$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^the transport manager is not the owner$", () -> {
            world.UIJourneySteps.updateTMDetailsAndNavigateToDeclarationsPage("Yes");
        });
    }
}