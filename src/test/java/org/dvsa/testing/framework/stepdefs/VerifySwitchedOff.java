package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VerifySwitchedOff extends BasePage implements En {
    String flag;
    private World world;

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
            String buttonName = findElement("form-actions[submit]", SelectorType.ID, 10).getText();
            assertEquals("Submit to operator", buttonName);
        });
        And("^submit to operator button is not displayed$", () -> {
            String buttonName = findElement("form-actions[submit]", SelectorType.ID, 10).getText();
            assertEquals("Submit", buttonName);
        });
        And("^i add a transport manager$", () -> {
            world.UIJourneySteps.navigateToExternalUserLogin();
            clickByLinkText(world.createLicence.getApplicationNumber());
            world.UIJourneySteps.addExistingPersonAsTransportManager();
        });
        When("^the transport manager is the owner$", () -> {
            world.UIJourneySteps.updateTMDetailsAndNavigateToDeclarationsPage("Yes", "No", "No", "No", "No");
        });
        And("^the transport manager is not the owner$", () -> {
            world.UIJourneySteps.updateTMDetailsAndNavigateToDeclarationsPage("No", "No", "No", "No", "No");
        });
        Given("^verify has been switched off$", () -> {
            world.APIJourneySteps.enableDisableVerify("1");
        });
        When("^i submit the application$", () -> {
            click("form-actions[submit]", SelectorType.ID);
        });
        Then("^the print and sign page is displayed$", () -> {

            Assert.assertTrue(isTextPresent("Transport Manager details approved", 10));
            Assert.assertTrue(isTextPresent(world.createLicence.getForeName() + " " + world.createLicence.getFamilyName(), 10));
            Assert.assertTrue(isTextPresent("Print, sign and return", 10));
        });
        And("^the application status is \"([^\"]*)\"$", (String arg0) -> {
            clickByLinkText("Back to Transport");
            waitForTextToBePresent("Transport Managers");
            Assert.assertTrue(isTextPresent(arg0, 10));
        });
        Then("^the 'Awaiting operator review' post signature page is displayed$", () -> {
            Assert.assertTrue(isTextPresent(world.createLicence.getForeName() + " " + world.createLicence.getFamilyName(), 10));
            Assertions.assertTrue(isTextPresent("Awaiting operator review", 10));
            Assert.assertTrue(isElementPresent("//a[contains(text(),'change your details')]", SelectorType.XPATH));
        });
        When("^i am on the the TM landing page$", () -> {
            world.UIJourneySteps.submitTMApplicationAndNavigateToTMLandingPage();
        });
        Then("^a success message banner should be displayed$", () -> {
            Assert.assertTrue(isTextPresent("The user account has been created and form has been emailed to the transport manager", 10));
        });
        After(new String[]{"@SS-Verify-Off"}, (Scenario scenario) -> {
            world.APIJourneySteps.enableDisableVerify("0");
        });
    }
}