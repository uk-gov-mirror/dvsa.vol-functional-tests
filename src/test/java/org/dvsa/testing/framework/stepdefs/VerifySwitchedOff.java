package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;

import static org.junit.Assert.assertFalse;

public class VerifySwitchedOff extends BasePage implements En {
    String flag;
    public VerifySwitchedOff(World world) {
        Given("^I have a \"([^\"]*)\" \"([^\"]*)\" application$", (String arg0, String arg1) -> {
            this.flag = arg1;
            world.genericUtils = new GenericUtils(world);
            world.createLicence.setIsInterim("Y");
            world.createLicence.setOperatorType(arg0);
            if (arg1.equals("NI")) {
                world.APIJourneySteps.nIAddressBuilder();
            }
            world.APIJourneySteps.createApplication();
        });
        When("^I navigate to the declaration page$", () -> {
          world.UIJourneySteps.navigateToExternalReviewAndDeclarationsPage();
        });
        Then("^Signing options are not displayed on the page$", () -> {
         assertFalse(isElementPresent("//*[@type='radio']", SelectorType.XPATH));
         assertFalse(isTextPresent("How would you like to sign the declaration?", 20));
        });
    }
}