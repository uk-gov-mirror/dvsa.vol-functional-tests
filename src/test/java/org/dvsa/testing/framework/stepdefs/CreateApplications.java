package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateApplications extends BasePage implements En  {
    public CreateApplications(World world) {
        Given("^i have a \"([^\"]*)\" application$", (String operatorType) -> {
            world.createLicence.setOperatorType(operatorType);
            world.APIJourneySteps.registerAndGetUserDetails();
            world.APIJourneySteps.createApplication();
        });
        When("^i choose to print and sign$", () -> {
            world.UIJourneySteps.navigateToExternalUserLogin(world.createLicence.getLoginId(),world.createLicence.getEmailAddress());
            clickByLinkText(world.createLicence.getApplicationNumber());
            world.UIJourneySteps.navigateThroughApplication();
            click("//*[contains(text(),'Print')]", SelectorType.XPATH);
            click("//*[@name='form-actions[submitAndPay]']", SelectorType.XPATH);
        });
        Then("^the application should be submitted$", () -> {
          assertTrue(isTextPresent(String.format("GV/SN Application Fee for application %s",world.createLicence.getApplicationNumber()),30));
        });
    }
}