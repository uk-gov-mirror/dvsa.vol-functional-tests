package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateApplications extends BasePage implements En {
    public CreateApplications(World world) {
        Given("^i have a \"([^\"]*)\" \"([^\"]*)\" application$", (String operatorType, String licenceType) -> {
            world.createLicence.setOperatorType(operatorType);
            world.createLicence.setLicenceType(licenceType);
            world.APIJourneySteps.registerAndGetUserDetails();
            world.APIJourneySteps.createApplication();
        });
        When("^i choose to print and sign$", () -> {
            world.UIJourneySteps.navigateToExternalUserLogin(world.createLicence.getLoginId(), world.createLicence.getEmailAddress());
            clickByLinkText(world.createLicence.getApplicationNumber());
            world.UIJourneySteps.navigateThroughApplication();
            click("//*[contains(text(),'Print')]", SelectorType.XPATH);
            click("//*[@name='form-actions[submitAndPay]']", SelectorType.XPATH);
        });
        Then("^the application should be submitted$", () -> {
            assertTrue(isTextPresent("Thank you, your application has been submitted.", 20));
        });
        When("^i pay for my application$", () -> {
            click("//*[@name='form-actions[pay]']", SelectorType.XPATH);
            world.UIJourneySteps.payFee(null, "card", "4006000000000600", "10", "20");
        });
    }
}